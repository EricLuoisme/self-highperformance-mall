package com.self.highperformance.goods.controller;

import com.self.highperformance.RespResult;
import com.self.highperformance.goods.model.BrandModel;
import com.self.highperformance.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    @PostMapping
    public RespResult add(@RequestBody BrandModel brandModel) {
        boolean save = brandService.save(brandModel);
        return save ? RespResult.ok() : RespResult.error("Insert error");
    }

    @PutMapping
    public RespResult update(@RequestBody BrandModel brandModel) {
        boolean updateById = brandService.updateById(brandModel);
        return updateById ? RespResult.ok() : RespResult.error("Update error");
    }

    @DeleteMapping("/{id}")
    public RespResult delete(@PathVariable(value = "id") String id) {
        boolean removeById = brandService.removeById(id);
        return removeById ? RespResult.ok() : RespResult.error("Remove error");
    }

    @PostMapping("/search")
    public RespResult<List<BrandModel>> queryList(@RequestBody BrandModel brandModel) {
        List<BrandModel> brandModels = brandService.queryList(brandModel);
        return RespResult.ok(brandModels);
    }
}
