package com.example.registrationlogindemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private int stt;
    private long id;
    private String name;
    private Long userId;        //Là tên của đơn vị
    private String mobile;
    private String orderCode;
    private String amount;
    private String address;
    private String description;
    private String timeCreated;
    private String status;
}
