package com.example.registrationlogindemo.controller;


import com.example.registrationlogindemo.Excel.PaymentExcelExporter;
import com.example.registrationlogindemo.Excel.UserPaymentExcelExporter;
import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExcelExportController {
    private final UserService service;


    @GetMapping("/payments/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<PaymentDto> paymentList = service.getListPayments(1);

        PaymentExcelExporter excelExporter = new PaymentExcelExporter(paymentList);

        excelExporter.export(response);
    }

    @GetMapping("/user/payment/export/excel")
    public void userExportToExcel(HttpServletResponse response, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        User user = service.findByEmail(userDetails.getUsername());
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<PaymentDto> payment = service.findByUserIdListAllPayment(user.getId(), 1);

        UserPaymentExcelExporter excelExporter = new UserPaymentExcelExporter(payment);
        excelExporter.export(response);
    }
}
