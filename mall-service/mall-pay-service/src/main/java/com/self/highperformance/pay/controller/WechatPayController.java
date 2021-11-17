package com.self.highperformance.pay.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.self.highperformance.pay.model.PayLog;
import com.self.highperformance.util.RespResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/wx")
@CrossOrigin
public class WechatPayController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 接收WechatPay返回的支付成功结果
     */
    @GetMapping("/result")
    public RespResult payLog() {
        // 创建日志对象
        PayLog log = new PayLog()
                .setId(IdWorker.getIdStr())
                .setStatus(1)
                .setContent("Successful Paid")
                .setPayId("AA")
                .setCreateTime(new Date());
        // 构建, 并向RocketMQ发送消息
        Message<String> msg = MessageBuilder.withPayload(JSON.toJSONString(log)).build();
        rocketMQTemplate.sendMessageInTransaction("rocket", "log", msg, null);
        return RespResult.ok();
    }


}
