package com.self.highperformance.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.highperformance.cart.feign.CartFeign;
import com.self.highperformance.cart.model.Cart;
import com.self.highperformance.goods.feign.SkuFeign;
import com.self.highperformance.order.mapper.OrderMapper;
import com.self.highperformance.order.mapper.OrderSkuMapper;
import com.self.highperformance.order.model.Order;
import com.self.highperformance.order.model.OrderSku;
import com.self.highperformance.order.service.OrderService;
import com.self.highperformance.util.RespResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private SkuFeign skuFeign;


    /**
     * 下单操作流程跨服务, 使用Seata分布式事务机制
     */
    @GlobalTransactional
    @Override
    public Boolean addOrder(Order order) {
        // 1. 查询购物车数据 (查询 mall-cart-service 微服务, 查询操作主要确定购物车内容, )
        RespResult<List<Cart>> cartResp = cartFeign.list(order.getCartIds());
        List<Cart> cartList = cartResp.getData();
        if (null == cartList || cartList.size() == 0) {
            return false;
        }

        // 2. 库存递减 (操作 mall-goods-service 微服务)
        skuFeign.dcount(cartList);

        // 3. 添加订单明细 (操作 mall-order-service 微服务 LOCAL)
        int totalNum = 0;
        int moneys = 0;
        for (Cart cart : cartList) {
            // 转实体
            OrderSku orderSku = JSON.parseObject(JSON.toJSONString(cart), OrderSku.class);
            orderSku.setId(IdWorker.getIdStr());
            orderSku.setOrderId(order.getId());
            // 简化操作使用, 生产上必须使用BigDecimal
            orderSku.setMoney(orderSku.getPrice() * orderSku.getNum());
            // 添加订单明细
            orderSkuMapper.insert(orderSku);
            // 统计计算
            totalNum += orderSku.getNum();
            moneys += orderSku.getMoney();
        }

        // 4. 添加订单 (操作 mall-order-service 微服务 LOCAL)
        order.setTotalNum(totalNum);
        order.setMoneys(moneys);
        orderMapper.insert(order);

        // 5. 删除购物车临时相关商品
        cartFeign.delete(order.getCartIds());
        return true;
    }
}
