package com.example.gptpassport.repository;

import com.example.gptpassport.entity.Visa;
import com.example.gptpassport.entity.Passport;
import com.example.gptpassport.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VisaRepository extends JpaRepository<Visa, Long> {
    List<Visa> findByPassport(Passport passport);
    Optional<Visa> findByPassportAndCountry(Passport passport, Country country);
}
