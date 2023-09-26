package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.dto.PaymentDto;
import com.example.registrationlogindemo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    PaymentDto findByUserId(Long userId);

    List<Payment> findByUserIdContainingIgnoreCase(Long userId);
//    @Query(value = "SELECT u FROM Payment u WHERE u.name IN :names")
//    List<Payment> findUserByNameList(@Param("names") Collection<String> names);
}
