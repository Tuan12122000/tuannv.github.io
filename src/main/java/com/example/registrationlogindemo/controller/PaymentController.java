package com.example.registrationlogindemo.controller;


import com.example.registrationlogindemo.Constant;
import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final UserService userService;

    @GetMapping("/user/payments/list")
    public String listUserPayment(@RequestParam(value = "keyword", required = false) String keyword,
                                  @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }
        return findPaginated(1, "id", "desc", keyword, userDetails, model);
    }

    @GetMapping("/user/payments/list/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "sortField") String sortField,
                                @RequestParam(value = "sortDir") String sortDir,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @AuthenticationPrincipal UserDetails userDetails,
                                Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }
        int pageSize = 5;

        if (StringUtils.isBlank(keyword)) {
            keyword = "";
        }
        Page<PaymentDto> page = userService.findPaginated(pageNo, pageSize, sortField, sortDir, user.getId(), "%" + keyword + "%");
        List<PaymentDto> payments = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("payments", payments);
        model.addAttribute("keyword", keyword);
        return "userHistoryPayment";
    }

    @GetMapping("/payments/list")
    public String listUserAllPayment(@RequestParam(value = "keyword", required = false) String keyword,
                                     @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }
        return findAllPaginated(1, "id", "desc", keyword, userDetails, model);
    }

    @GetMapping("/payment/list/all/user/page/{pageNo}")
    public String findAllPaginated(@PathVariable(value = "pageNo") int pageNo,
                                   @RequestParam(value = "sortField") String sortField,
                                   @RequestParam(value = "sortDir") String sortDir,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return "login";
        }
        int pageSize = 6;

        if (StringUtils.isBlank(keyword)) {
            keyword = "";
        }

        user.getEmail().equals(Constant.ADMIN);
        Page<PaymentDto> page = userService.findAllPaginated(pageNo, pageSize, sortField, sortDir, "%" + keyword + "%");

        List<PaymentDto> payments = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("payments", payments);
        model.addAttribute("keyword", keyword);
        return "historyPayment";
    }
}
