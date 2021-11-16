package com.self.highperformance.cart.service;

import com.self.highperformance.cart.model.Cart;

import java.util.List;

public interface CartService {

    /**
     * 查询购物车
     */
    List<Cart> list(String userName);

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
