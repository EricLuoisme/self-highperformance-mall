package com.self.highperformance.pay.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
// MyBatisPlus表映射注解
@TableName(value = "pay_log")
@Accessors(chain = true)
public class PayLog {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private Integer status;

    private String content;

    private String payId;

    private Date createTime;
}