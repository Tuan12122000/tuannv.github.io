package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<Payment> listAllPayment();

//    PaymentDto getUserId(Long userId);
    void savePayment(PaymentDto paymentDto);
}
