package com.example.registrationlogindemo.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "omipayResponse")
public class OmipayResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionInfo;
    private Integer price;
    private Integer paymentId;
    private Integer paymentType;
    private String errorText;
    private String secureCode;
    private String tokenOmipay;
    private String orderCode;
    private String bankCode;
    private String cardNumber;
    private String tokenization;
}
