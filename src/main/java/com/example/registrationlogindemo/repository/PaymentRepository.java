package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserIdAndStatus(Long userId, int status);

    List<Payment> findByStatus(int status);

    Page<Payment> findByUserIdAndOrderCodeLike(Long userId, String orderCode, Pageable pageable);

    Page<Payment> findByOrderCodeLike(String orderCode, Pageable pageable);

    Payment findByOrderCode(String orderCode);

    @Query("select u from Payment u where u.userId = ?1 and u.orderCode like ?2")
    List<Payment> searchPaymentByOrderCode(Long userId, String orderCode);
}
