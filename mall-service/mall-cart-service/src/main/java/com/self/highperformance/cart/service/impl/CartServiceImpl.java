package com.self.highperformance.cart.service.impl;

import com.google.common.collect.Lists;
import com.self.highperformance.cart.mapper.CartMapper;
import com.self.highperformance.cart.model.Cart;
import com.self.highperformance.cart.service.CartService;
import com.self.highperformance.goods.feign.SkuFeign;
import com.self.highperformance.goods.model.Sku;
import com.self.highperformance.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<Cart> list(String userName) {
        Cart cart = new Cart().setUserName(userName);
        return cartMapper.findAll(Example.of(cart), Sort.by("_id"));
    }

    @Override
    public List<Cart> list(List<String> ids) {
        if (null != ids && ids.size() > 0) {
            Iterable<Cart> allById = cartMapper.findAllById(ids);
            return Lists.newArrayList(allById);
        }
        return null;
    }

    /**
     * @param id       商品id
     * @param userName 用户名称(唯一键)
     * @param num      商品总数量
     * @return 是否成功加入
     */
    @Override
    public void addCart(String id, String userName, Integer num) {

        // 1. 拼接商品id和用户名作为主键, 先删除db内容
        cartMapper.deleteById(userName + id);

        // 2. 根据id查询sku详情
        RespResult<Sku> skuResp = skuFeign.findBySkuId(id);

        // 3. 存入mongo
        Sku sku = skuResp.getData();
        Cart cart = new Cart()
                .set_id(userName + id)
                .setUserName(userName)
                .setName(sku.getName())
                .setPrice(sku.getPrice())
                .setImage(sku.getImage())
                .setSkuId(id)
                .setNum(num);
        cartMapper.save(cart);
    }

    @Override
    public void delCart(List<String> ids) {
        // 这里使用mongoTemplate更方便的组装条件删除语句
        mongoTemplate.remove(Query.query(Criteria.where("_id").in(ids)), Cart.class);
    }
}
