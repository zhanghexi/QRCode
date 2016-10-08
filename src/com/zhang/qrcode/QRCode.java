package com.zhang.qrcode;

import com.swetake.util.Qrcode;
import jp.sourceforge.qrcode.QRCodeDecoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by hexi on 2016/10/7.
 */
public class QRCode {
    /**
     * 生成二维码的方法
     *
     * @param srcValue
     * @param qrcodePicFilePath
     * @return
     */
    public static boolean encode(String srcValue, String qrcodePicFilePath) {
        int MAX_DATA_LENGTH = 200;
        byte[] d = srcValue.getBytes();
        int dataLength = d.length;
        int imageWidth = 113;   //二维码的宽度
        int imageHeight = imageWidth;   //二维码的长度(跟宽度一样，正方形)

        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);

        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {
            Qrcode qrcode = new Qrcode();
            qrcode.setQrcodeErrorCorrect('M');
            qrcode.setQrcodeEncodeMode('B');
            qrcode.setQrcodeVersion(5);

            boolean[][] b = qrcode.calQrcode(d);
            int qrcodeDataLen = b.length;
            for (int i = 0; i < qrcodeDataLen; i++) {
                for (int j = 0; j < qrcodeDataLen; j++) {
                    if (b[j][i]) {
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
                    }
                }
            }
            System.out.println("二维码生成成功!");
        } else {
            System.out.println(dataLength + "大于" + MAX_DATA_LENGTH);
            return false;
        }

        g.dispose();
        bi.flush();
        File f = new File(qrcodePicFilePath);
        String suffix = f.getName().substring(f.getName().indexOf(".") + 1, f.getName().length());
        System.out.println("二维码输出成功");
        try {
            ImageIO.write(bi, suffix, f);
        } catch (IOException e) {
            System.out.println("二维码生成失败" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 解析二维码的方法
     *
     * @param qrcodePicFilePath
     * @return
     */
    public static String decode(String qrcodePicFilePath) {
        System.out.println("开始解析二维码");
        //读取二维码图像数据
        File imageFile = new File(qrcodePicFilePath);
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            System.out.println("读取二维码图片失败：" + e.getMessage());
            return null;
        }
        //解析二维码
        QRCodeDecoder decoder = new QRCodeDecoder();
        String decodedData = new String(decoder.decode(new J2SEImageGucas(image)));
        System.out.println("解析内容如下：" + decodedData);
        return decodedData;
    }

}
