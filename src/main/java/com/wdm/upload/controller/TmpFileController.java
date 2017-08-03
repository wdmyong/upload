package com.wdm.upload.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wdm.upload.util.FileUploadUtil;

@RestController
@RequestMapping("/tmp")
public class TmpFileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        Map<String, String> textMap = new HashMap<>();
        textMap.put("word", "tmp");
        FileUploadUtil.formUpload("http://localhost:8080/file/upload", textMap, file);
        return "上传成功";
    }
}
