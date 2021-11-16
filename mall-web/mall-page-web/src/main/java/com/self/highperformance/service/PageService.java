package com.self.highperformance.service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface PageService {

    /**
     * 生成详情页
     */
    void html(String spuId) throws FileNotFoundException, UnsupportedEncodingException;
}
