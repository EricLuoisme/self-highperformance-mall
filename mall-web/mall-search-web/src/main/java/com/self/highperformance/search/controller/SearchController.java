package com.self.highperformance.search.controller;

import com.self.highperformance.util.RespResult;
import com.self.highperformance.search.feign.SkuSearchFeign;
import com.self.highperformance.util.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/web/search")
public class SearchController {

    @Autowired
    private SkuSearchFeign skuSearchFeign;

    @GetMapping
    public String search(@RequestParam(required = false) Map<String, Object> searchMap, Model model) {
        RespResult<Map<String, Object>> search = skuSearchFeign.search(searchMap);
        model.addAttribute("result", search.getData());
        model.addAttribute("searchMap", searchMap);
        // 组装访问的Url
        model.addAttribute("url", UrlUtils.map2url("/web/search",searchMap,"page"));
        model.addAttribute("urlsort", UrlUtils.map2url("/web/search",searchMap,"sm","sfield","page"));
        return "search";
    }


}
