package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.OmipayCallBackDto;
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
    void saveRep(OmipayCallBackDto omipayCallBackDto);


    //Payment
    List<Payment> listAllPayment();

    List<PaymentDto> getListPayments(int status);


    List<PaymentDto> findByUserIdListAllPayment(Long userId, int status);

    List<PaymentDto> searchPaymentByOrderCode(Long userId, String orderCode);

    void savePayment(PaymentDto paymentDto);

    PaymentDto updatePaymentByOrderCode(String orderCode, int status);
    //phân trang
    Page<PaymentDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, Long userId, String keyword);
    Page<PaymentDto> findAllPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword);
}
