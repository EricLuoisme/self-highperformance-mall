package com.self.highperformance.cart.controller;

import com.self.highperformance.cart.model.Cart;
import com.self.highperformance.cart.service.CartService;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// http://localhost:8087/cart/1319051488298889217/10
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;


    @GetMapping("/list")
    public RespResult<List<Cart>> getCartByUserName() {
        String userName = "testUser";
        List<Cart> list = cartService.list(userName);
        return RespResult.ok(list);
    }

    @PostMapping("/list")
    public RespResult<List<Cart>> getCartByCartIds(@RequestBody List<String> ids) {
        List<Cart> list = cartService.list(ids);
        return RespResult.ok(list);
    }


    @GetMapping("/{id}/{num}")
    public RespResult addCart(@PathVariable("id") String id, @PathVariable("num") Integer num) {
        String userName = "testUser";
        cartService.addCart(id, userName, num);
        return RespResult.ok();
    }

    @DeleteMapping
    public RespResult<List<Cart>> delCartByCartIds(@RequestBody List<String> ids) {
        cartService.delCart(ids);
        return RespResult.ok();
    }


}
