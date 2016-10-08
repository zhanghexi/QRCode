package com.zhang.qrcode;

/**
 * Created by hexi on 2016/10/7.
 */
public class CreateQRCode {

    public static void main(String[] args) {
        String data = "zhanghexi的微信公众号";
        //生成二维码
        QRCode.encode(data, "E://二维码//zhanghexi.JPG");
        //解析二维码
        //QRCode.decode("E://二维码//zhanghexi.JPG");
    }

}
