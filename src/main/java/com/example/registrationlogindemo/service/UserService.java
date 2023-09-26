package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<Payment> listAllPayment();

    List<PaymentDto> getListPayments();

    void savePayment(PaymentDto paymentDto);
}
