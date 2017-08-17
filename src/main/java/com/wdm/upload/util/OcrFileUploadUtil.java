package com.wdm.upload.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.web.multipart.MultipartFile;

public class OcrFileUploadUtil {

    public static String formUpload(String urlStr, 
            Map<String, String> textMap, MultipartFile multipartFile) {
        String res = ""; 
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符 
        try { 
            URL url = new URL(urlStr); 
            conn = (HttpURLConnection) url.openConnection(); 
            conn.setConnectTimeout(5000); 
            conn.setReadTimeout(30000); 
            conn.setDoOutput(true); 
            conn.setDoInput(true); 
            conn.setUseCaches(false); 
            conn.setRequestMethod("POST"); 
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Authorization", "s0fUnsVrdQJCm4CIqQ9Nhqd0YPphPTEyNTE2MzMzNTImYj1naXNob3djaSZrPUFLSUR6NElEV0wyemtkQTRiWlJOYThNcHZRaGI4MDZabm1OUyZlPTE1MDQzNDc4NTcmdD0xNTAxNzU1ODU3JnI9MTUwMTg0NjQ1OCZ1PTAmZj0=");
            conn 
              .setRequestProperty("User-Agent", 
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)"); 
            conn.setRequestProperty("Content-Type", 
              "multipart/form-data; boundary=" + BOUNDARY); 
            OutputStream out = new DataOutputStream(conn.getOutputStream()); 
            // text 
            if (textMap != null) { 
             StringBuffer strBuf = new StringBuffer(); 
             Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator(); 
             while (iter.hasNext()) { 
              Map.Entry<String, String> entry = iter.next();
              String inputName = (String) entry.getKey(); 
              String inputValue = (String) entry.getValue(); 
              if (inputValue == null) { 
               continue; 
              } 
              strBuf.append("\r\n").append("--").append(BOUNDARY).append( 
                "\r\n"); 
              strBuf.append("Content-Disposition: form-data; name=\""
                + inputName + "\"\r\n\r\n"); 
              strBuf.append(inputValue); 
             } 
             out.write(strBuf.toString().getBytes()); 
            } 
            // file 
            if (multipartFile != null) { 
             
              String contentType = new MimetypesFileTypeMap()
                      .getContentType(multipartFile.getContentType()); 
              contentType = "image/jpg"; 
              StringBuffer strBuf = new StringBuffer(); 
              strBuf.append("\r\n").append("--").append(BOUNDARY).append( 
                "\r\n"); 
              strBuf.append("Content-Disposition: form-data; name=\""
                + "image[0]" + "\"; filename=\"" + "1.jpg"
                + "\"\r\n"); 
              strBuf.append("Content-Type:" + contentType + "\r\n\r\n"); 
              out.write(strBuf.toString().getBytes()); 
              DataInputStream in = new DataInputStream(multipartFile.getInputStream()); 
              int bytes = 0; 
              byte[] bufferOut = new byte[1024]; 
              while ((bytes = in.read(bufferOut)) != -1) { 
               out.write(bufferOut, 0, bytes); 
              } 
              in.close(); 
             } 
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(); 
            out.write(endData); 
            out.flush(); 
            out.close(); 
            // 读取返回数据 
            StringBuffer strBuf = new StringBuffer(); 
            BufferedReader reader = new BufferedReader(new InputStreamReader( 
              conn.getInputStream())); 
            String line = null; 
            while ((line = reader.readLine()) != null) { 
             strBuf.append(line).append("\n"); 
            } 
            res = strBuf.toString(); 
            reader.close(); 
            reader = null; 
           } catch (Exception e) { 
            System.out.println("发送POST请求出错。" + urlStr); 
            e.printStackTrace(); 
           } finally { 
            if (conn != null) { 
               conn.disconnect(); 
               conn = null; 
            } 
        } 
        return res; 
    } 
}
