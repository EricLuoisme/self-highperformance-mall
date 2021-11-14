package com.self.highperformance.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class CanalClient implements DisposableBean {

    private CanalConnector canalConnector;

    /**
     * 配置获取Canal的连接
     */
    @Bean
    public CanalConnector getCanalConnector() {
        // 创建单机Canal连接
        this.canalConnector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("127.0.0.1", 11111), "example", "", "");
        // 创建连接
        this.canalConnector.connect();
        // 订阅匹配日志
        this.canalConnector.subscribe();
        // 回滚上次请求的信息位置, 防止数据丢失
        this.canalConnector.rollback();
        return this.canalConnector;
    }

    @Override
    public void destroy() throws Exception {
        if (null != canalConnector) {
            canalConnector.disconnect();
        }
    }
}
