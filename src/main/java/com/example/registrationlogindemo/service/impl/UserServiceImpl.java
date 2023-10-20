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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
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
        user.setType(0);
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
        user.getType();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        if (user.getType() == 1) {
//            Role role = new Role();
//            role.setName("ROLE_ADMIN");
//            user.setRoles(Arrays.asList(role));
//            roleRepository.save(role);
//        }
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
    public List<PaymentDto> getListPayments() {
        AtomicInteger idx = new AtomicInteger(1);
        return paymentRepository.findAll().stream()
                .map(e -> {
                    PaymentDto paymentDto = this.convertPaymentToDto(e);
                    paymentDto.setStt(idx.getAndIncrement());
                    return paymentDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPayments() {
        AtomicInteger idx = new AtomicInteger(1);
        return paymentRepository.findAll().stream()
                .map(e -> {
                    PaymentDto paymentDto = this.convertPaymentToDto(e);
                    paymentDto.setStt(idx.getAndIncrement());
                    return paymentDto;
                })
                .collect(Collectors.toList());
    }

    private PaymentDto convertPaymentToDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setName(payment.getName());
        paymentDto.setUserId(payment.getUserId());
        paymentDto.setMobile(payment.getMobile());
        paymentDto.setOrderCode(payment.getOrderCode());
        paymentDto.setAmount(String.format(Locale.US, "%,.0f", payment.getAmount()));
        paymentDto.setAddress(payment.getAddress());
        paymentDto.setDescription(payment.getDescription());
        paymentDto.setTimeCreated(payment.getTimeCreated());
        paymentDto.setStatus(this.resolveStatus(payment.getStatus()));
        return paymentDto;
    }

    private String resolveStatus(int status) {
        switch (status) {
            case 2:
                return "Thất bại";
            case 1:
                return "Thành công";
            default:
                return "Chưa thanh toán";
        }
    }

    @Override
    public List<PaymentDto> findByUserIdListAllPayment(Long userId) {
        AtomicInteger idx = new AtomicInteger(1);
        return paymentRepository.findByUserIdOrderByIdDesc(userId).stream()
                .map(e -> {
                    PaymentDto paymentDto = this.convertPaymentToDto(e);
                    paymentDto.setStt(idx.getAndIncrement());
                    return paymentDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> searchPaymentByOrderCode(Long userId, String orderCode) {
        AtomicInteger idx = new AtomicInteger(1);
        return paymentRepository.searchPaymentByOrderCode(userId, orderCode).stream()
                .map(e -> {
                    PaymentDto paymentDto = this.convertPaymentToDto(e);
                    paymentDto.setStt(idx.getAndIncrement());
                    return paymentDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void savePayment(PaymentDto paymentDto) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = formatter.format(date);
        Payment payment = new Payment();
        double amount = Double.parseDouble(paymentDto.getAmount().replace(".", ""));
        payment.setName(paymentDto.getName());
        payment.setUserId(paymentDto.getUserId());
        payment.setMobile(Constant.Mobile);
        payment.setAmount(amount);
        payment.setTimeCreated(strDate);
        payment.setOrderCode(paymentDto.getOrderCode());
        payment.setAddress(paymentDto.getAddress());
        payment.setStatus(0);
        payment.setDescription(Constant.DESCRIPTION);
        paymentRepository.save(payment);
    }

    @Override
    public PaymentDto updatePaymentByOrderCode(String orderCode, int status) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = formatter.format(date);
        Payment payment = paymentRepository.findByOrderCode(orderCode);
        payment.setTimeCreated(strDate);
        payment.setStatus(status);
        paymentRepository.save(payment);
        return this.convertPaymentToDto(payment);
    }

    //Xuất Excel All
    public List<Payment> listAllPayment() {
        return paymentRepository.findAll(Sort.by("userId").ascending());
    }

    //phân trang giao dịch của user
    @Override
    public Page<PaymentDto> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, Long userId, String orderCode) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.paymentRepository.findByUserIdAndOrderCodeLike(userId, orderCode, pageable)
                .map(this::convertPaymentToDto);
    }
    //phân trang theo tất cả giao dịch
    @Override
    public Page<PaymentDto> findAllPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String orderCode) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.paymentRepository.findByOrderCodeLike(orderCode,pageable)
                .map(this::convertPaymentToDto);
    }
}
