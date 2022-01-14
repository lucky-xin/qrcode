package com.pistonint.qrcode.svc;

import java.net.URL;

/**
 * 二维码服务定义接口
 *
 * @author Luchaoxin
 * @version V 1.0
 * @date 2021-11-24
 */
public interface QrCodeSvc {
    /**
     * 生成二维码图片
     *
     * @param content 二维码内容
     * @param url     logo图片地址
     * @return 二维码图片字节数组
     * @throws Exception
     */
    byte[] genQrCodeImage(String content, URL url) throws Exception;
}
