package com.self.highperformance.cart.mapper;

import com.self.highperformance.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartMapper extends MongoRepository<Cart, String> {
}
