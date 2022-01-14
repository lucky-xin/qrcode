package com.pistonint.qrcode.svc.impl;

import com.pistonint.qrcode.svc.QrCodeSvc;
import com.pistonint.qrcode.util.QrCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * 二维码服务实现类
 *
 * @author Luchaoxin
 * @version V 1.0
 * @date 2021-11-24
 */
@Service
public class QrCodeSvcImpl implements QrCodeSvc {

    /**
     * 生成带logo的二维码到response
     */
    @Override
    public byte[] genQrCodeImage(String content, URL url) throws Exception {
        BufferedImage bufferedImage = QrCodeUtil.genQrCodeImage(content, url);
        try (FastByteArrayOutputStream out = new FastByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "JPG", out);
            return out.toByteArray();
        }
    }
}
