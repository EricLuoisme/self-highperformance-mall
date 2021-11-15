package com.self.highperformance.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.self.highperformance.search.mapper.SkuSearchMapper;
import com.self.highperformance.search.model.SkuEs;
import com.self.highperformance.search.service.SkuSearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkuSearchServiceImpl implements SkuSearchService {

    @Autowired
    private SkuSearchMapper skuSearchMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Override
    public void addIndex(SkuEs skuEs) {
        // 将Json格式属性转换为map, 方便es子类查询
        String skuAttribute = skuEs.getSkuAttribute();
        if (StringUtils.isNotEmpty(skuAttribute)) {
            skuEs.setAttrMap(JSON.parseObject(skuAttribute, Map.class));
        }
        skuSearchMapper.save(skuEs);
    }

    @Override
    public void delIndex(String id) {
        skuSearchMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> search(Map<String, Object> searchMap) {

        // 构建搜索对象
        NativeSearchQueryBuilder queryBuilder = queryBuilder(searchMap);
        // 分组搜索
        group(queryBuilder, searchMap);

        // Mapper进行搜索
//        Page<SkuEs> search = skuSearchMapper.search(queryBuilder.build());
        AggregatedPage<SkuEs> page = (AggregatedPage<SkuEs>) skuSearchMapper.search(queryBuilder.build());
//        AggregatedPage<SkuEs> page = elasticsearchRestTemplate.queryForPage(queryBuilder.build(), SkuEs.class, new HighlightResultMapper());
//        SearchHits<SkuEs> search = elasticsearchRestTemplate.search(queryBuilder.build(), SkuEs.class);

        // 转换获取结果集
        Map<String, Object> resultMap = new HashMap<>();

        // 分组数据解析
        parseGroup(page.getAggregations(), resultMap);

        // 动态属性解析
        attrParse(resultMap);

        // 复制内容
        List<SkuEs> content = page.getContent();
        resultMap.put("list", content);
        resultMap.put("totalElements", page.getTotalElements());
        return resultMap;
    }

    /**
     * 搜索条件构建
     */
    private NativeSearchQueryBuilder queryBuilder(Map<String, Object> searchMap) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        //组合查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //判断关键词是否为空，不为空，则设置条件
        if (searchMap != null && searchMap.size() > 0) {
            //关键词条件
            Object keywords = searchMap.get("keywords");
            if (!org.springframework.util.StringUtils.isEmpty(keywords)) {
                //builder.withQuery(QueryBuilders.termQuery("name",keywords.toString()));
                boolQueryBuilder.must(QueryBuilders.termQuery("name", keywords.toString()));
            }

            //分类查询
            Object category = searchMap.get("category");
            if (!org.springframework.util.StringUtils.isEmpty(category)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryName", category.toString()));
            }

            //品牌查询
            Object brand = searchMap.get("brand");
            if (!org.springframework.util.StringUtils.isEmpty(brand)) {
                boolQueryBuilder.must(QueryBuilders.termQuery("brandName", brand.toString()));
            }

            //价格区间查询  price=0-500元  500-1000元  1000元以上
            Object price = searchMap.get("price");
            if (!org.springframework.util.StringUtils.isEmpty(price)) {
                //价格区间
                String[] prices = price.toString().replace("元", "").replace("以上", "").split("-");
                //price>x
                boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.valueOf(prices[0])));
                //price<=y
                if (prices.length == 2) {
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.valueOf(prices[1])));
                }
            }

            //动态属性查询
            for (Map.Entry<String, Object> entry : searchMap.entrySet()) {
                // 以attr_开始，动态属性  attr_网络：移动5G
                if (entry.getKey().startsWith("attr_")) {
                    String key = "attrMap." + entry.getKey().replaceFirst("attr_", "") + ".keyword";
                    boolQueryBuilder.must(QueryBuilders.termQuery(key, entry.getValue().toString()));
                }
            }

            // 排序
            Object sfield = searchMap.get("sfield");
            Object sm = searchMap.get("sm");
            if (!org.springframework.util.StringUtils.isEmpty(sfield) && !org.springframework.util.StringUtils.isEmpty(sm)) {
                builder.withSort(
                        SortBuilders.fieldSort(sfield.toString())   // 指定排序域
                                .order(SortOrder.valueOf(sm.toString()))    // 排序方式
                );
            }
        }

        // 分页查询
        builder.withPageable(PageRequest.of(currentPage(searchMap), 5));
        return builder.withQuery(boolQueryBuilder);
    }

    /**
     * 分组查询
     */
    private void group(NativeSearchQueryBuilder queryBuilder, Map<String, Object> searchMap) {
        //用户如果没有输入分类条件，则需要将分类搜索出来，作为条件提供给用户
        if (org.springframework.util.StringUtils.isEmpty(searchMap.get("category"))) {
            queryBuilder.addAggregation(
                    AggregationBuilders
                            .terms("categoryList")//别名，类似Map的key
                            .field("categoryName")//根据categoryName域进行分组
                            .size(100)      //分组结果显示100个
            );
        }
        //用户如果没有输入品牌条件，则需要将品牌搜索出来，作为条件提供给用户
        if (org.springframework.util.StringUtils.isEmpty(searchMap.get("brand"))) {
            queryBuilder.addAggregation(
                    AggregationBuilders
                            .terms("brandList")//别名，类似Map的key
                            .field("brandName")//根据brandName域进行分组
                            .size(100)      //分组结果显示100个
            );
        }
        //属性分组查询
        queryBuilder.addAggregation(
                AggregationBuilders
                        .terms("attrmaps")//别名，类似Map的key
                        .field("skuAttribute")//根据skuAttribute域进行分组
                        .size(100000)      //分组结果显示100000个
        );
    }

    /**
     * 分页参数
     */
    public int currentPage(Map<String, Object> searchMap) {
        try {
            Object page = searchMap.get("page");
            return Integer.valueOf(page.toString()) - 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 分组结果解析
     */
    public void parseGroup(Aggregations aggregations, Map<String, Object> resultMap) {
        if (aggregations != null) {
            for (Aggregation aggregation : aggregations) {
                // 强转ParsedStringTerms
                ParsedStringTerms terms = (ParsedStringTerms) aggregation;

                // 循环结果集对象
                List<String> values = new ArrayList<String>();
                for (Terms.Bucket bucket : terms.getBuckets()) {
                    values.add(bucket.getKeyAsString());
                }
                // 名字
                String key = aggregation.getName();
                resultMap.put(key, values);
            }
        }
    }

    /**
     * 将属性信息合并成Map对象
     */
    public void attrParse(Map<String, Object> searchMap) {
        // 先获取attrmaps
        Object attrmaps = searchMap.get("attrmaps");
        if (attrmaps != null) {
            // 集合数据
            List<String> groupList = (List<String>) attrmaps;

            // 定义一个集合Map<String,Set<String>>,存储所有汇总数据
            Map<String, Set<String>> allMaps = new HashMap<String, Set<String>>();

            // 循环集合
            for (String attr : groupList) {
                Map<String, String> attrMap = JSON.parseObject(attr, Map.class);

                for (Map.Entry<String, String> entry : attrMap.entrySet()) {
                    // 获取每条记录,将记录转成Map
                    String key = entry.getKey();
                    Set<String> values = allMaps.get(key);
                    if (values == null) {
                        values = new HashSet<String>();
                    }
                    values.add(entry.getValue());
                    // 覆盖之前的数据
                    allMaps.put(key, values);
                }
            }
            // 覆盖之前的attrmaps
            searchMap.put("attrmaps", allMaps);
        }
    }

}
