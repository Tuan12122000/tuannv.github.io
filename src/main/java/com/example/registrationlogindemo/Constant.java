package com.example.registrationlogindemo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;


@RequiredArgsConstructor
public class Constant {
    public static final String DESCRIPTION = "Thu giá dịch vụ thu gom, vận chuyển và xử lý rác thải sinh hoạt";
    public static final String ADMIN = "admin@gmail.com";
    public static final String Mobile = "0972346074";

    ////MC product
    public static final String Curren = "vnd";
    public static final String MERCHANT_SITE_CODE = "65502";
    public static final String EmailDemo = "kyh261196@gmail.com";
    public static final String SECURE_PASS = "f86c8d293298827ad8a6967dbe71b13c";
    public static final String URL_SEND_OMIPAY = "https://checkout.omipay.vn/v1/checkout.php";
    public static final String RETURN_URL_OMIPAY = "http://pay.vinhyeneus.io.vn/omiPayCallBack";
    public static final String NOTIFY_URL_OMIPAY = "http://pay.vinhyeneus.io.vn/omiPayCallBack/requestCommon";
    public static final String CANCEL_URL_OMIPAY = "http://pay.vinhyeneus.io.vn/payment";

//    //    MC localhost
//    public static final String Curren = "vnd";
//    public static final String MERCHANT_SITE_CODE = "66457";
//    public static final String EmailDemo = "demo@omipay.vn";
//    public static final String SECURE_PASS = "1af52a56c761eb79160185b19395e105";
//    public static final String URL_SEND_OMIPAY = "https://checkout-sandbox.omipay.vn/v1/checkout.php";
//    public static final String RETURN_URL_OMIPAY = "http://localhost:8080/omiPayCallBack";
//    public static final String NOTIFY_URL_OMIPAY = "http://localhost:8080/omiPayCallBack/requestCommon";
//    public static final String CANCEL_URL_OMIPAY = "http://localhost:8080/payment";

    //Chuỗi format
    public static <S> String getMD5(S ob) {
        return DigestUtils.md5Hex(ob.toString());
    }
}
