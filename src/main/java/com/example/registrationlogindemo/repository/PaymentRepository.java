package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.User;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.lang.model.element.NestingKind;
import java.util.Collection;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserIdOrderByIdDesc(Long userId);
    Page<Payment> findByUserIdAndOrderCodeLike(Long userId, String orderCode, Pageable pageable);
    Page<Payment> findByOrderCodeLike(String orderCode, Pageable pageable);
    Payment findByOrderCode (String orderCode);
    @Query("select u from Payment u where u.userId = ?1 and u.orderCode like ?2")
    List<Payment> searchPaymentByOrderCode(Long userId, String orderCode);
}
