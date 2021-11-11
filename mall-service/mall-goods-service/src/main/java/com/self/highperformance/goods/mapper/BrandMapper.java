package com.self.highperformance.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.self.highperformance.goods.model.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper extends BaseMapper <Brand>{

    @Select("SELECT b.id, b.name, b.image, b.initial, b.sort " +
            "FROM category_brand cb LEFT JOIN brand b " +
            "ON cb.brand_id = b.id " +
            "WHERE cb.category_id = #{id}")
    List<Brand> queryByCategoryId(Integer id);

}
