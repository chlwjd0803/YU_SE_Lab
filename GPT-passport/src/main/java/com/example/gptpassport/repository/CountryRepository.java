package com.example.gptpassport.repository;

import com.example.gptpassport.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCode(String code);
}