package com.self.highperformance.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.highperformance.goods.mapper.BrandMapper;
import com.self.highperformance.goods.mapper.CategoryMapper;
import com.self.highperformance.goods.model.Brand;
import com.self.highperformance.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Override
    public List<Brand> queryList(Brand model) {
        // 组装QueryWrapper作为查询条件
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", model.getName())
                .eq("initial", model.getInitial());
        // 将查询条件传入调用MyBatisPlus的接口
        return brandMapper.selectList(queryWrapper);
    }

    @Override
    public Page<Brand> queryPageList(Brand model, Long currentPage, Long size) {
        // 组装QueryWrapper作为查询条件
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", model.getName());
        // 将查询条件传入调用MyBatisPlus的接口
        return brandMapper.selectPage(new Page<>(currentPage, size), queryWrapper);
    }

    @Override
    public List<Brand> queryByCategoryId(Integer id) {
        // 分解Sql
        List<Integer> brands = brandMapper.queryByCategoryId(id);
        if (null != brands && brands.size() > 0) {
            return brandMapper.selectList(new QueryWrapper<Brand>().in("id", brands));
        }
        return null;
    }
}
