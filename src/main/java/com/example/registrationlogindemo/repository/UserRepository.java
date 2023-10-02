package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Payment;
import com.example.registrationlogindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByEmailContainingIgnoreCase(String keyword);

    @Query("select u from User u where u.email = ?1")
    List<User> search(String emailAddress);
}
