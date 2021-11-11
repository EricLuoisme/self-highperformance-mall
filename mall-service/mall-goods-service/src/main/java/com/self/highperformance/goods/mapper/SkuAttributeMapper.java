package com.self.highperformance.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.self.highperformance.goods.model.SkuAttribute;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SkuAttributeMapper extends BaseMapper<SkuAttribute> {


    @Select("SELECT s.id, s.name, s.options, s.sort " +
            "FROM sku_attribute s " +
            "WHERE s.id IN (" +
            "SELECT attr_id " +
            "FROM category_attr " +
            "WHERE category_id = #{id} )")
    List<SkuAttribute> queryByCategoryId(Integer id);

}
