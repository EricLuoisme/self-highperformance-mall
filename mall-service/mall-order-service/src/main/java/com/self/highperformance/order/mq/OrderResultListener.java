package com.self.highperformance.order.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 用于监听MQ队列, 并消费
 */
@Component
@RocketMQMessageListener(topic = "log", consumerGroup = "resultgroup")
public class OrderResultListener implements RocketMQListener, RocketMQPushConsumerLifecycleListener {

    /**
     * 消息监听
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
                                                            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    for (MessageExt messageExt : list) {
                        String s = new String(messageExt.getBody(), "UTF-8");
                        System.out.println("result::::::" + s);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }

    /**
     * 暂没有实质作用
     */
    @Override
    public void onMessage(Object o) {

    }
}
