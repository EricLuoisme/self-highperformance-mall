package com.self.highperformance.cart.feign;

import com.self.highperformance.cart.model.Cart;
import com.self.highperformance.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "mall-cart")
public interface CartFeign {

    /***
     * 删除购物车数据
     */
    @DeleteMapping(value = "/cart")
    RespResult delete(@RequestBody List<String> ids);


    /***
     * 指定ID集合的购物车数据
     * http://localhost:8087/cart/list
     */
    @PostMapping(value = "/cart/list")
    RespResult<List<Cart>> list(@RequestBody List<String> ids);
}
