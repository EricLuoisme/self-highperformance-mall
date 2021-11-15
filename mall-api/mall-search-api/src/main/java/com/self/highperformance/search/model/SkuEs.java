package com.self.highperformance.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;

@Data
// indexName 对应索引名称
// skues 对应当前表名称
@Document(indexName = "shopsearch", type = "skues")
public class SkuEs {

    @Id
    private String id;

    // 因为对商品名会有模糊查找, 所以该属性需要进行分词
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String name;
    private Integer price;
    private Integer num;
    private String image;
    private String images;
    private Date createTime;
    private Date updateTime;
    private String spuId;
    private Integer categoryId;

    // 通过分类查询不应该进行拆分
    @Field(type = FieldType.Keyword)
    private String categoryName;

    private Integer brandId;

    // 通过品牌查询不应该进行拆分
    @Field(type = FieldType.Keyword)
    private String brandName;

    private String skuAttribute;

    private Integer status;

    // 属性映射
    private Map<String, String> attrMap;
}
