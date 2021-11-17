package com.self.highperformance.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.order.model.Order;

public interface OrderService extends IService<Order> {

    /**
     * 添加订单
     */
    Boolean addOrder(Order order);
}
