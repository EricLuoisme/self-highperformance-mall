package com.self.highperformance.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.highperformance.pay.mapper.PayLogMapper;
import com.self.highperformance.pay.model.PayLog;
import com.self.highperformance.pay.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    PayLogMapper payLogMapper;

    @Override
    public void add(PayLog payLog) {
        payLogMapper.insert(payLog);
    }
}
