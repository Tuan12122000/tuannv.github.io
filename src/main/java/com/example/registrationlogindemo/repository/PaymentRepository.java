package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUserId(Long userId);

    List<Payment> findByNameContainingIgnoreCase(String keyword);
//    @Query(value = "SELECT u FROM Payment u WHERE u.name IN :names")
//    List<Payment> findUserByNameList(@Param("names") Collection<String> names);
}
