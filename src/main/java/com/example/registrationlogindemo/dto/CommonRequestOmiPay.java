package com.example.registrationlogindemo.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class CommonRequestOmiPay implements Serializable {
    public String error_code;
    public String message;
}
