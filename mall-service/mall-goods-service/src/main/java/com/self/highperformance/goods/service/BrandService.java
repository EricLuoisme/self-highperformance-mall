package com.self.highperformance.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.goods.model.Brand;

import java.util.List;

public interface BrandService extends IService<Brand> {

    /**
     * 条件查询
     */
    List<Brand> queryList(Brand model);

    /**
     * 分页条件查询
     */
    Page<Brand> queryPageList(Brand model, Long currentPage, Long size);

    /***
     * 根据分类ID查询品牌集合
     */
    List<Brand> queryByCategoryId(Integer id);

}
