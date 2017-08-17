package com.wdm.upload.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(
            @RequestParam("file") MultipartFile file, 
            @RequestParam("word") String word) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String fileName = word + file.getOriginalFilename();
        System.out.println("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "/Users/duanyong/Documents/picture/";//"E://picture//";
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public void show(
            @RequestParam("file") MultipartFile file,
            HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type","image/jpeg");//设置响应的媒体类型，这样浏览器会识别出响应的是图片
        response.getOutputStream().write(file.getBytes());
        response.flushBuffer();
    }
}
