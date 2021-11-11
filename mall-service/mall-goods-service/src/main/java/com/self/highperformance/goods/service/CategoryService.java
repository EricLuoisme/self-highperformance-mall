package com.self.highperformance.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.goods.model.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    /**
     * 根据父id查询所有子类
     *
     * @param pid 父id
     * @return 所有子类
     */
    List<Category> findByParentId(Integer pid);
}
