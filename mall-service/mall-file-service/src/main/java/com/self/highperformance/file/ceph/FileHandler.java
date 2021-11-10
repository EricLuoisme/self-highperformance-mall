package com.self.highperformance.file.ceph;

import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileHandler {

    @Autowired
    private Container container;


    public void upload(String fileName, byte[] buffer) {
        // 获取容器对象
        StoredObject object = container.getObject(fileName);
        // 文件上传
        object.uploadObject(buffer);
    }

    public byte[] download(String fileName) {
        StoredObject object = container.getObject(fileName);
        byte[] bytes = object.downloadObject();
        return bytes;
    }


}
