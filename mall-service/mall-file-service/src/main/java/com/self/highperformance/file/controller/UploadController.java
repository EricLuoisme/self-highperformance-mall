package com.self.highperformance.file.controller;

import com.self.highperformance.resp.RespResult;
import com.self.highperformance.file.ceph.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/file")
public class UploadController {

    @Autowired
    private FileHandler fileHandler;


    @PostMapping("/upload")
    public RespResult upload(MultipartFile file) throws IOException {
        fileHandler.upload(file.getOriginalFilename(), file.getBytes());
        return RespResult.ok();
    }

    @PostMapping("/download/{fileName}")
    public RespResult download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        byte[] download = fileHandler.download(fileName);
        ServletOutputStream os = response.getOutputStream();
        os.write(download);
        os.flush();
        return RespResult.ok();
    }


}
