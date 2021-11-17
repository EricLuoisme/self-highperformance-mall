package com.self.highperformance.order.controller;

import com.self.highperformance.order.model.Order;
import com.self.highperformance.order.service.OrderService;
import com.self.highperformance.util.RespCode;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping
    public RespResult addOrder(@RequestBody Order order) {

        String userName = "testUser";
        order.setUsername(userName);

        Boolean result = orderService.addOrder(order);
        return result ? RespResult.ok() : RespResult.error(RespCode.ERROR);
    }


}
