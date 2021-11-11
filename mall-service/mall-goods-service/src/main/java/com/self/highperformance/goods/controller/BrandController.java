package com.self.highperformance.goods.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.self.highperformance.goods.model.Brand;
import com.self.highperformance.resp.RespResult;
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
    public RespResult add(@RequestBody Brand brand) {
        boolean save = brandService.save(brand);
        return save ? RespResult.ok() : RespResult.error("Insert error");
    }

    @PutMapping
    public RespResult update(@RequestBody Brand brand) {
        boolean updateById = brandService.updateById(brand);
        return updateById ? RespResult.ok() : RespResult.error("Update error");
    }

    @DeleteMapping("/{id}")
    public RespResult delete(@PathVariable(value = "id") String id) {
        boolean removeById = brandService.removeById(id);
        return removeById ? RespResult.ok() : RespResult.error("Remove error");
    }

    @PostMapping("/search")
    public RespResult<List<Brand>> queryList(@RequestBody Brand brand) {
        List<Brand> brands = brandService.queryList(brand);
        return RespResult.ok(brands);
    }

    @PostMapping("/search/{page}/{size}")
    public RespResult<Page<Brand>> queryPageList(@PathVariable("page") Long current,
                                                 @PathVariable("size") Long size,
                                                 @RequestBody Brand brand) {
        // 分页查询
        Page<Brand> brandModelPage = brandService.queryPageList(brand, current, size);
        return RespResult.ok(brandModelPage);
    }
}
