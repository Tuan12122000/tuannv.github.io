package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.Constant;
import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.PaymentRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;


    @GetMapping("/")
    public String loginSlash() {
        return "login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("/register")
    private String showRegistrationForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }
        UserDto createUser = new UserDto();
        model.addAttribute("user", createUser);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    private String registration(@Valid @ModelAttribute("user") UserDto user,
                                BindingResult result,
                                Model model) {
        User existing = userService.findByEmail(user.getEmail());
        try {
            if (existing != null) {
                result.rejectValue("email", null, "Email đã tồn tại");
            }
            if (result.hasErrors()) {
                model.addAttribute("user", user);
                return "register";
            }
            userService.saveUser(user);
            model.addAttribute("user", user);
            return "users";
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "redirect:/register?success";
    }

    @GetMapping("/payment")
    public String payment(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }else if (user.getEmail().equals("admin@gmail.com")) {
            List<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "users";
        }
        model.addAttribute("payment", new PaymentDto());
        model.addAttribute("user", user);
        return "payment";
    }


    @PostMapping("/payment/save")
    private String depositSave(@Valid @ModelAttribute("payment") PaymentDto paymentDto,
                               BindingResult result, @AuthenticationPrincipal UserDetails userDetails, Model model) throws NoSuchAlgorithmException {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        } else {
            paymentDto.setUserId(String.valueOf(user.getId()));
        }
        String amount = paymentDto.getAmount().replace(".","");
        if (paymentDto.getAmount() == null){
            result.rejectValue("amount", null, "Số không hợp lệ");
        }else if (Double.parseDouble(amount) < 0){
            result.rejectValue("amount", null, "Số tiền phải lớn hơn 0");
        }
        if (result.hasErrors()) {
            model.addAttribute("payment", paymentDto);
            return "payment";
        }
        //Tạo ra Url để call sang OmiPay
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "application/json");
        MultiValueMap<String, String> mapParams = new LinkedMultiValueMap<>();
        mapParams.add("merchant_site_code", Constant.MERCHANT_SITE_CODE);
        mapParams.add("return_url", Constant.RETURN_URL_OMIPAY);
        mapParams.add("cancel_url", Constant.RETURN_URL_OMIPAY);
//        mapParams.add("receiver", user.getEmail());
        mapParams.add("receiver", Constant.EmailDemo);
        mapParams.add("order_code", String.valueOf(Constant.Mobile));
        mapParams.add("price", amount);
        mapParams.add("currency", Constant.Curren);
        mapParams.add("secure_pass", Constant.SECURE_PASS);
        String secure_code = Constant.getMD5(Constant.MERCHANT_SITE_CODE + '|' + Constant.EmailDemo + '|' + amount + '|' + Constant.Curren + '|' + Constant.Mobile + '|' + Constant.SECURE_PASS);
        mapParams.add("secure_code", secure_code);
        mapParams.add("installment", String.valueOf(0));
        //
        HttpEntity<String> entity = new HttpEntity<>(headers);
        UriComponentsBuilder builderUri = UriComponentsBuilder.fromHttpUrl(Constant.URL_SEND_OMIPAY)
                .queryParams(mapParams);
        userService.savePayment(paymentDto);
        return "redirect:" + builderUri.toUriString();
    }

    @GetMapping("/payments/list")
    private String getAll(@Param("keyword") String keyword, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }
        try {
            Page<Payment> paymentList = userService.getPayments();
            model.addAttribute("payments", paymentList);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "historyPayment";
    }

    @GetMapping("/user/payments/list")
    public String ListUserPayment(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }
        List<Payment> payment = userService.findByUserIdListAllPayment(String.valueOf(user.getId()));
        model.addAttribute("payments", payment);
        return "userHistoryPayment";
    }

    //lịch sử giao dịch của User
    @GetMapping("/user/payments/list/search")
    private String getListUserPayments(@Valid @ModelAttribute("payment") PaymentDto paymentDto, @AuthenticationPrincipal UserDetails userDetails, Model model, String keyword) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }
        try {
            List<Payment> payment = paymentRepository.findByUserId(paymentDto.getUserId());
            if (keyword == null) {
                paymentRepository.search(keyword).forEach(payment::add);
            } else {
                paymentRepository.findByUserId(keyword).forEach(payment::add);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("payment", payment);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "userHistoryPayment";
    }
}
