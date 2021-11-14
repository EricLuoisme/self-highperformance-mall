package com.self.highperformance.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.protobuf.InvalidProtocolBufferException;
import com.self.highperformance.goods.feign.SkuFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 根据Canal触发内容, 进行缓存的对应处理
 */
@Component
@Slf4j
public class SkuCanalHandler {

    @Autowired
    private SkuFeign skuFeign;


    /**
     * ad_item表db更新even统一处理入口
     */
    public void eventHandler(CanalEntry.Entry entry) {
        // sql执行类型
        CanalEntry.EventType eventType = entry.getHeader().getEventType();
        CanalEntry.RowChange change = null;
        // 获取数据
        try {
            change = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
        } catch (InvalidProtocolBufferException e) {
            log.error("Canal Event: Parsing BinLog Error " + this.getClass().getName());
            e.printStackTrace();
            return;
        }
        // 进行路由, 现默认每次仅一条记录发生改变
        if (null != change) {
            switch (eventType) {
                case INSERT: {
                    insertHandler(change);
                    break;
                }
                case UPDATE: {
                    updateHandler(change);
                    break;
                }
                case DELETE: {
                    deleteHandler(change);
                    break;
                }
                default: {
                    log.info("Canal Event: " + eventType + " will not be handled by default");
                }
            }
        }
    }

    /**
     * db插入同步更新缓存
     */
    private void insertHandler(CanalEntry.RowChange rowChange) {
        List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDataList) {
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            // 找到type值
            Integer type = Integer.valueOf(afterColumnsList.get(2).getValue());
            // 调用接口更新缓存
            skuFeign.updTypeItems(type);
        }
    }

    /**
     * db跟新同步更新cache
     */
    private void updateHandler(CanalEntry.RowChange rowChange) {
        List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDataList) {
            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            // 找到type值
            Integer beforeType = Integer.valueOf(beforeColumnsList.get(2).getValue());
            Integer afterType = Integer.valueOf(afterColumnsList.get(2).getValue());
            // 如果分类不同了, 则需要把之前的进行重新加载
            if (!beforeType.equals(afterType)) {
                skuFeign.updTypeItems(beforeType);
            }
            skuFeign.updTypeItems(afterType);
        }
    }

    /**
     * db删除同步删除cache
     */
    private void deleteHandler(CanalEntry.RowChange rowChange) {
        List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
        for (CanalEntry.RowData rowData : rowDataList) {
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            // 找到type值
            Integer type = Integer.valueOf(afterColumnsList.get(2).getValue());
            // 调用接口删除缓存
            skuFeign.delTypeItems(type);
        }
    }

}



