//package com.self.highperformance.canal;
//
//import com.alibaba.otter.canal.protocol.CanalEntry;
//import com.self.highperformance.search.feign.SkuSearchFeign;
//import com.self.highperformance.search.model.SkuEs;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class SkuIndexHandler {
//
//    @Autowired
//    private SkuSearchFeign searchFeign;
//
//
//    public void addIndex(CanalEntry.RowChange rowChange) {
//        List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
//        for (CanalEntry.RowData rowData : rowDataList) {
//            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
//            SkuEs skuEs = new SkuEs()
//                    .setId(afterColumnsList.get(0).getValue())
//                    .setName(afterColumnsList.get(1).getValue())
//                    .setPrice(Integer.valueOf(afterColumnsList.get(2).getValue()))
//                    .setNum(Integer.valueOf(afterColumnsList.get(3).getValue()))
//                    .setImage(afterColumnsList.get(4).getValue())
//                    .setImages(afterColumnsList.get(5).getValue());
//            // 调用接口更新es的索引
//            searchFeign.add(skuEs);
//        }
//    }
//
//    public void delIndex(CanalEntry.RowChange rowChange) {
//        List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
//        for (CanalEntry.RowData rowData : rowDataList) {
//            List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
//            // 调用接口更新es的索引
//            searchFeign.del(beforeColumnsList.get(0).getValue());
//        }
//    }
//}
