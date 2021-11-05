package com.self.highperformance.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.goods.model.BrandModel;

import java.util.List;

public interface BrandService extends IService<BrandModel> {

    List<BrandModel> queryList(BrandModel model);

}
