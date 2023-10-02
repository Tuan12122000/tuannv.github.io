package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    //User
    void saveUser(UserDto userDto);
    void saveUpdateUser(User user);
    User findByEmail(String email);


    //Payment
    List<Payment> listAllPayment();
    List<Payment> getListPayments();
    Page<Payment> getPayments();
    List<Payment> findByUserIdListAllPayment(String userId);

    void savePayment(PaymentDto paymentDto);
}
