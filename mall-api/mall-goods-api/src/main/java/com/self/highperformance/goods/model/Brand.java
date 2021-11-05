package com.self.highperformance.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Brand实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "brand")
public class Brand {

    /** 主键id */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 品牌名称 */
    private String name;

    /** 品牌图片 */
    private String image;

    /** 品牌首字母 */
    private String initial;

    /** 排序 */
    private Integer sort;
}
