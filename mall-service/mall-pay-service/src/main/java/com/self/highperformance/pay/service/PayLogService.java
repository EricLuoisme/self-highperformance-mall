package com.self.highperformance.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.self.highperformance.pay.model.PayLog;

public interface PayLogService extends IService<PayLog> {

    /**
     * 从RocketMq获取支付log, 并写入数据库
     */
    void add(PayLog payLog);
}
