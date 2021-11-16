package com.self.highperformance.goods.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Product {
    // Spu
    private Spu spu;
    // Sku
    private List<Sku> skus;
}
