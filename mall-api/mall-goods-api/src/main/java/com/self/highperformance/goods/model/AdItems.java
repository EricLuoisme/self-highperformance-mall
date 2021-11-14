package com.self.highperformance.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ad_items")
@Accessors(chain = true)
public class AdItems implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer type;
    private String skuId;
    private Integer sort;
}
