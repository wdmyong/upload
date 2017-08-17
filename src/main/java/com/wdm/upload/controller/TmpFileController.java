package com.wdm.upload.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wdm.upload.util.OcrFileUploadUtil;

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
        textMap.put("appid", "1251633352");
        textMap.put("bucket", "gishowci");
        textMap.put("card_type", "0");
        String result = OcrFileUploadUtil.formUpload("http://service.image.myqcloud.com/ocr/idcard", textMap, file);
        System.out.println(result);
        System.out.println(StringUtils.deleteWhitespace(result));
        return result;
    }
}
