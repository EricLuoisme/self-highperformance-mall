package com.self.highperformance.cart.service;

public interface CartService {

    /**
     * 加入购物车
     *
     * @param id       商品id
     * @param userName 用户名称(唯一键)
     * @param num      商品总数量
     * @return 是否成功加入
     */
    void addCart(String id, String userName, Integer num);

}
