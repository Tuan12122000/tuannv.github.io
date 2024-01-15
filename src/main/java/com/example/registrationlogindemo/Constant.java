package com.example.registrationlogindemo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;


@RequiredArgsConstructor
public class Constant {
    public static final String DESCRIPTION = "Thu giá dịch vụ thu gom, vận chuyển và xử lý rác thải sinh hoạt";
    public static final String ADMIN = "admin@gmail.com";
    public static final String Mobile = "0972346074";

    //Chuỗi format
    public static <S> String getMD5(S ob) {
        return DigestUtils.md5Hex(ob.toString());
    }
}
