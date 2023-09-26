package com.example.registrationlogindemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private long id;
    private String name;
    private String userId;        //Là tên của đơn vị
    private double amount;
    private String address;
    private String description;
    private String timeCreated;
}
