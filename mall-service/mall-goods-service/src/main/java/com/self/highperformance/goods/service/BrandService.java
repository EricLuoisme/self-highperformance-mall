package com.self.highperformance.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.goods.model.BrandModel;

import java.util.List;

public interface BrandService extends IService<BrandModel> {

    /**
     * 条件查询
     */
    List<BrandModel> queryList(BrandModel model);

    /**
     * 分页条件查询
     */
    Page<BrandModel> queryPageList(BrandModel model, Long currentPage, Long size);

}
