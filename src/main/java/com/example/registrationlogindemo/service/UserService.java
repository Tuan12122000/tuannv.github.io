package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.User;

import java.util.List;

public interface UserService {
    //User
    void saveUser(UserDto userDto);

    void saveUpdateUser(User user);

    User findByEmail(String email);


    //Payment
    List<Payment> listAllPayment();

    List<PaymentDto> getListPayments();

    List<PaymentDto> getPayments();

    List<PaymentDto> findByUserIdListAllPayment(Long userId);

    List<PaymentDto> searchPaymentByOrderCode(Long userId, String orderCode);

    void savePayment(PaymentDto paymentDto);

    PaymentDto updatePaymentByOrderCode(String orderCode, int status);
//    PaymentDto updatePaymentByOrderCodeError(String orderCode ,int status);
}
