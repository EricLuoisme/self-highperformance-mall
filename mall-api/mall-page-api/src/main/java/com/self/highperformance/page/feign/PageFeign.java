package com.self.highperformance.page.feign;

import com.self.highperformance.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@FeignClient("mall-web-page")
public interface PageFeign {

    @GetMapping("/{id}")
    RespResult html(@PathVariable("id") String spuId) throws FileNotFoundException, UnsupportedEncodingException;
}
