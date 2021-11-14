package com.self.highperformance.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@Slf4j
public class CanalScheduling implements Runnable, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private CanalConnector canalConnector;

    @Autowired
    private SkuCanalHandler skuCanalHandler;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 固定监控刷新
     */
    @Override
    @Scheduled(fixedDelay = 100)
    public void run() {
        long batchId = -1;
        try {
            int batchSize = 1000;
            Message msg = canalConnector.getWithoutAck(batchSize);
            batchId = msg.getId();
            List<CanalEntry.Entry> entries = msg.getEntries();
            if (-1 != batchId && entries.size() > 0) {
                for (CanalEntry.Entry entry : entries) {
                    if (CanalEntry.EntryType.ROWDATA == entry.getEntryType()) {
                        // 解析处理
                        publishCanalEvent(entry);
                        // 额外逻辑以实现针对操作执行
                        skuCanalHandler.eventHandler(entry);
                    }
                }
            }
            // 告知Canal Server消费成功
            canalConnector.ack(batchId);
        } catch (Exception e) {
            e.printStackTrace();
            // 出错需要rollback
            canalConnector.rollback(batchId);
        }
    }

    /**
     * 对Canal的Entry对象进行输出
     */
    private void publishCanalEvent(CanalEntry.Entry entry) {
        CanalEntry.EventType eventType = entry.getHeader().getEventType();
        String tableName = entry.getHeader().getTableName();
        CanalEntry.RowChange change = null;

        try {
            change = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            log.error("Canal Event: Parsing BinLog Error " + this.getClass().getName());
            e.printStackTrace();
            return;
        }

        log.info("Canal Event: " + eventType.toString().toLowerCase(Locale.ROOT) + " on Table: " + tableName);

        if (null != change) {
            // 迭代每一条变更数据
            for (CanalEntry.RowData rowData : change.getRowDatasList()) {
                // 判断是否为删除事件
                if (entry.getHeader().getEventType() == CanalEntry.EventType.DELETE) {
                    System.out.println("---delete---");
                    printColumnList(rowData.getBeforeColumnsList());
                    System.out.println("---");
                }
                // 判断是否为更新事件
                else if (entry.getHeader().getEventType() == CanalEntry.EventType.UPDATE) {
                    System.out.println("---update---");
                    printColumnList(rowData.getBeforeColumnsList());
                    System.out.println("---");
                    printColumnList(rowData.getAfterColumnsList());
                }
                // 判断是否为插入事件
                else if (entry.getHeader().getEventType() == CanalEntry.EventType.INSERT) {
                    System.out.println("---insert---");
                    printColumnList(rowData.getAfterColumnsList());
                    System.out.println("---");
                }
            }
        }
    }

    /**
     * 打印所有列名和列值
     */
    private static void printColumnList(List<CanalEntry.Column> columnList) {
        for (CanalEntry.Column column : columnList) {
            System.out.println(column.getName() + "\t" + column.getValue());
        }
    }

}
