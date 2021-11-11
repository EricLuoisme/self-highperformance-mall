package com.self.highperformance.goods.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "category_brand")
public class CategoryBrand {

    @TableField
    private Integer categoryId;

    @TableField
    private Integer brandId;
}
