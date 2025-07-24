package com.example.gptpassport.repository;

import com.example.gptpassport.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    Optional<Passport> findByPassportNo(String passportNo);
}
