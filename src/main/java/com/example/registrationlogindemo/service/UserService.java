package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void saveUpdateUser(User user);
    User findByEmail(String email);

    List<Payment> listAllPayment();

    List<PaymentDto> getListPayments();
    void savePayment(PaymentDto paymentDto);
}
