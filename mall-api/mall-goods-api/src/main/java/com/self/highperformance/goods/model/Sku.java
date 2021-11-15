package com.self.highperformance.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
// 这里是canal-spring-boot-starter, 通过表明绑定表
@TableName(value = "sku")
// 这里是JPA中的Table注解
@Table
public class Sku implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String name;
    private Integer price;
    private Integer num;
    private String image;
    private String images;

    // 这里使驼峰转换生效
    private Date createTime;
    private Date updateTime;
    private String spuId;
    private Integer categoryId;
    private String categoryName;
    private Integer brandId;
    private String brandName;
    private String skuAttribute;
    private Integer status;
}
