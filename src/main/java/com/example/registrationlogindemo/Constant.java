package com.example.registrationlogindemo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;


@RequiredArgsConstructor
public class Constant {
    public static final String DESCRIPTION = "Thu giá dịch vụ thu gom, vận chuyển và xử lý rác thải sinh hoạt";
    public static final String Mobile = "0972346074";
    public static final String Curren = "vnd";
    public static final String MERCHANT_SITE_CODE = "66457";
    public static final String EmailDemo = "demo@omipay.vn";
    public static final String SECURE_PASS = "1af52a56c761eb79160185b19395e105";
    public static final String URL_SEND_OMIPAY = "https://checkout-sandbox.omipay.vn/v1/checkout.php";


    //Chuỗi format
    public static <S> String getMD5(S ob) {
        return DigestUtils.md5Hex(ob.toString());
    }
}
