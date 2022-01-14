package com.pistonint.qrcode;

import com.pistonint.qrcode.util.QrCodeUtil;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author Luchaoxin
 * @version V 1.0
 * @date 2021-11-29
 */
public class QrCodeApplicationTest {

    @Test
    public void test() throws Exception {
        String content = "https://www.pistonint.com/";
        URL url = new URL("file:///home/xin/dev/workspace/pistonint-cloud/pistonint-qrcode/src/test/resources/logo-social.png");
        BufferedImage bufferedImage = QrCodeUtil.genQrCodeImage(content, url);
        try (OutputStream out = new FileOutputStream("qrcode.jpg")) {
            ImageIO.write(bufferedImage, "JPG", out);
        }
        String decode = QrCodeUtil.decode(bufferedImage);
        System.out.println(decode);
    }
}
