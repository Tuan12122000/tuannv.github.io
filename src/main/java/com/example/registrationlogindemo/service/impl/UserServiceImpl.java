package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.Constant;
import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.PaymentRepository;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PaymentRepository paymentRepository;


    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setMobile(userDto.getMobileUer());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public void saveUpdateUser(User user) {
        user.getName();
        user.getMobile();
        user.getEmail();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    //Dùng cho hàm PaymentUser
    @Override
    public List<Payment> getListPayments() {
        return paymentRepository.findAll();
    }
    @Override
    public Page<Payment> getPayments() {
        return paymentRepository.findAll(PageRequest.of(0,10));
    }

//    private PaymentDto convertPaymentToDto(Payment payment) {
//        PaymentDto paymentDto = new PaymentDto();
//        paymentDto.setId(payment.getId());
//        paymentDto.setName(payment.getName());
//        paymentDto.setUserId(String.valueOf(payment.getUserId()));
//        paymentDto.setOderId(payment.getOderId());
//        paymentDto.setAmount(payment.getAmount());
//        paymentDto.setAddress(payment.getAddress());
//        paymentDto.setDescription(payment.getDescription());
//        paymentDto.setTimeCreated(Instant.ofEpochMilli(Long.parseLong(payment.getTimeCreated())).atZone(ZoneId.of("GMT+7")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        return paymentDto;
//    }

    public List<Payment> findByUserIdListAllPayment(String userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public void savePayment(PaymentDto paymentDto) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = formatter.format(date);
        Payment payment = new Payment();
        payment.setName(paymentDto.getName());
        payment.setUserId(String.valueOf(paymentDto.getUserId()));
        payment.setOderId(paymentDto.getOderId());
        payment.setAmount(paymentDto.getAmount());
        payment.setTimeCreated(strDate);
        payment.setAddress(Constant.Mobile);
        payment.setDescription(Constant.DESCRIPTION);
        paymentRepository.save(payment);
    }

    //Xuất Excel All
    public List<Payment> listAllPayment() {
        return paymentRepository.findAll(Sort.by("userId").ascending());
    }
}
