package com.example.registrationlogindemo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NotEmpty
public class OmipayCallBackDto implements Serializable {
    private String transaction_info;
    private Integer price;
    private Integer payment_id;
    private Integer payment_type;
    private String error_text;
    private String secure_code;
    private String token_omipay;
    private String order_code;
    private String bank_code;
    private String card_number;
    private String tokenization;
}
