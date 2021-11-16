package com.self.highperformance.goods.controller;

import com.self.highperformance.goods.model.Category;
import com.self.highperformance.goods.service.CategoryService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/parent/{pid}")
    public RespResult<List<Category>> list(@PathVariable("pid") Integer pid) {
        List<Category> byParentId = categoryService.findByParentId(pid);
        return RespResult.ok(byParentId);
    }

    @GetMapping("/{id}")
    public RespResult<Category> searchOne(@PathVariable("id") Integer id) {
        Category byId = categoryService.getById(id);
        return RespResult.ok(byId);
    }

}
