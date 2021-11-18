package com.self.highperformance.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
// MyBatisPlus表映射注解
@TableName(value = "seckill_goods")
public class SeckillGoods {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String supId;
    private String skuId;
    private String name;
    private String images;
    private String content;
    private Integer price;
    private Integer seckillPrice;
    private Integer num;
    private Integer storeCount;
    private Date createTime;
    private String activityId;
}
