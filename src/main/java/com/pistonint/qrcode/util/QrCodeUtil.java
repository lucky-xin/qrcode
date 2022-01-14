package com.pistonint.qrcode.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 二维码工具类
 *
 * @author Luchaoxin
 * @version V 1.0
 * @date 2021-11-24
 */
@UtilityClass
public class QrCodeUtil {

    /**
     * 图片格式
     */
    private static final String FORMAT = "JPG";
    /**
     * 二维码宽度像素pixels数量
     */
    private static final int QRCODE_WIDTH = 300;
    /**
     * 二维码高度像素pixels数量
     */
    private static final int QRCODE_HEIGHT = 300;
    /**
     * LOGO宽度像素pixels数量
     */
    private static final int LOGO_WIDTH = 100;
    /**
     * LOGO高度像素pixels数量
     */
    private static final int LOGO_HEIGHT = 100;

    /**
     * 生成二维码图片
     *
     * @param content 二维码内容
     * @param url     logo图片地址
     * @return
     * @throws Exception
     */
    private BufferedImage createImage(String content, URL url) throws Exception {
        Map<EncodeHintType, Object> hints = Map.of(
                EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H,
                EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name(),
                EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (url == null) {
            return image;
        }
        // 插入图片
        QrCodeUtil.insertImage(image, url);
        return image;
    }

    /**
     * 在图片上插入LOGO
     *
     * @param source 二维码图片内容
     * @param url    LOGO图片地址
     * @throws Exception
     */
    private void insertImage(BufferedImage source, URL url) throws Exception {
        Image src = ImageIO.read(url);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (width > LOGO_WIDTH) {
            width = LOGO_WIDTH;
        }
        if (height > LOGO_HEIGHT) {
            height = LOGO_HEIGHT;
        }
        Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        // 绘制缩小后的图
        g.drawImage(image, 0, 0, null);
        g.dispose();
        src = image;
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_WIDTH - width) / 2;
        int y = (QRCODE_HEIGHT - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码图片
     *
     * @param content 二维码内容
     * @param url     logo图片地址
     * @throws Exception
     */
    public BufferedImage genQrCodeImage(String content, URL url)
            throws Exception {
        BufferedImage image = QrCodeUtil.createImage(content, url);
        return image;
    }

    /**
     * 解析二维码图片，得到包含的内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String decode(String path) throws Exception {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        return decode(image);
    }

    public String decode(BufferedImage image) throws NotFoundException {
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Map<DecodeHintType, Object> hints = Map.of(DecodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }
}
