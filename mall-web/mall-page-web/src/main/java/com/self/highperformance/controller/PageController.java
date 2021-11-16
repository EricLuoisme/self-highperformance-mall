package com.self.highperformance.controller;

import com.self.highperformance.service.PageService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

// http://localhost:8086/page/1319051488177254401
@RestController
@RequestMapping("/page")
public class PageController {

    @Autowired
    private PageService pageService;


    @GetMapping("/{id}")
    public RespResult html(@PathVariable("id") String spuId) throws FileNotFoundException, UnsupportedEncodingException {
        pageService.html(spuId);
        return RespResult.ok();
    }

}
