package com.self.highperformance.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.self.highperformance.goods.model.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {

    @Select("SELECT brand_id FROM category_brand WHERE category_id = #{id}")
    List<Integer> queryByCategoryId(Integer id);

}
