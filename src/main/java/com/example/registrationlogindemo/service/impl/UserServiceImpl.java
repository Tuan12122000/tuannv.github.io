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
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
        user.setType(1);

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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    //Dùng cho hàm PaymentUser

//    @Override
//    public PaymentDto getUserId(Long userId) {
//        return this.convertPaymentToDto(paymentRepository.findByUserId(userId));
//    }
//
//    private PaymentDto convertPaymentToDto(Payment payment) {
//        PaymentDto paymentDto = new PaymentDto();
//        paymentDto.setId(payment.getId());
//        paymentDto.setName(payment.getName());
//        paymentDto.setUserId(payment.getUserId());
//        paymentDto.setAmount(payment.getAmount());
//        paymentDto.setAddress(payment.getAddress());
//        return paymentDto;
//    }

    @Override
    public void savePayment(PaymentDto paymentDto) {
        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();
        Payment payment = new Payment();
        payment.setName(paymentDto.getName());
        payment.setUserId(paymentDto.getUserId());
        payment.setAmount(paymentDto.getAmount());
        payment.setTimeCreated(timeStampMillis);
        payment.setAddress(paymentDto.getAddress());
        payment.setDescription(Constant.DESCRIPTION);
        paymentRepository.save(payment);
    }

    //Xuất Excel
    public List<Payment> listAllPayment() {
        return paymentRepository.findAll(Sort.by("userId").ascending());
    }
}
