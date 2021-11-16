package com.self.highperformance.cart.controller;

import com.self.highperformance.cart.service.CartService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// http://localhost:8087/cart/1319051488298889217/10
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/{id}/{num}")
    public RespResult addCart(@PathVariable("id") String id, @PathVariable("num") Integer num) {
        String userName = "testUser";
        cartService.addCart(id, userName, num);
        return RespResult.ok();
    }


}
