package com.pistonint.qrcode.controller;

import com.pistonint.qrcode.svc.QrCodeSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 二维码服务API
 *
 * @author Luchaoxin
 * @version V 1.0
 * @date 2021-11-24
 */
@RestController
@RequestMapping("/api/v1/qrcode")
public class QrCodeController {

    @Autowired
    private QrCodeSvc qrCodeSvc;

    @GetMapping
    public void getQrCode(@RequestParam String content,
                          @RequestParam(name = "logo", required = false) URL url,
                          HttpServletResponse resp) throws Exception {

        resp.setContentType("application/force-download");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        //inline表示浏览器直接使用，attachment表示下载，fileName表示下载的文件名
        resp.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=qr-code.jpg");
        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        byte[] bytes = qrCodeSvc.genQrCodeImage(content, url);
        ServletOutputStream out = resp.getOutputStream();
        out.write(bytes);
        out.flush();
    }

}
