package com.self.highperformance.pay.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.highperformance.pay.model.PayLog;
import com.self.highperformance.pay.service.PayLogService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 本地事务
 */
@Component
@RocketMQTransactionListener(txProducerGroup = "rocket")
public class TransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private PayLogService payLogService;


    /**
     * 向RMQ的Broker发送Half消息成功后, 调用该方法 (既可以执行本地事务操作)
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        /**
         * 本地事务控制, 接收到WeChatPay发送的成功支付, 向RocketMQ投递存储Success消息, 后续由Order服务消费
         */
        try {
            String msgStr = new String((byte[]) message.getPayload(), "UTF-8");
            PayLog payLog = JSON.parseObject(msgStr, PayLog.class);
            payLogService.add(payLog);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 超时回查操作
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        /**
         * 暂不处理
         */
        return RocketMQLocalTransactionState.COMMIT;
    }
}
