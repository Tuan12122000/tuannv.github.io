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
    private long id;
    private String name;
    private String userId;        //Là tên của đơn vị
    private String mobile;
    private String amount;
    private String address;
    private String timeCreated;
}
