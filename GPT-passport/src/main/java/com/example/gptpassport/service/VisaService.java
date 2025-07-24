package com.example.gptpassport.service;

import com.example.gptpassport.entity.Country;
import com.example.gptpassport.entity.Passport;
import com.example.gptpassport.entity.Visa;
import com.example.gptpassport.repository.CountryRepository;
import com.example.gptpassport.repository.PassportRepository;
import com.example.gptpassport.repository.VisaRepository;
import com.example.gptpassport.dto.VisaReqDto;
import com.example.gptpassport.dto.VisaResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisaService {

    private final PassportRepository passportRepository;
    private final CountryRepository countryRepository;
    private final VisaRepository visaRepository;

    /** 여권 번호로 비자 목록 조회 */
    public List<VisaResDto> getVisasByPassportNo(String passportNo) {
        Passport passport = passportRepository.findByPassportNo(passportNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid passportNo: " + passportNo));
        return visaRepository.findByPassport(passport)
                .stream()
                .map(Visa::toDto)
                .collect(Collectors.toList());
    }

    /** 새 비자 발급 */
    @Transactional
    public VisaResDto issueVisa(String passportNo, VisaReqDto req) {
        Passport passport = passportRepository.findByPassportNo(passportNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid passportNo: " + passportNo));
        Country country = countryRepository.findByCode(req.getCountryCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid countryCode: " + req.getCountryCode()));

        // 중복 발급 방지
        visaRepository.findByPassportAndCountry(passport, country)
                .ifPresent(v -> { throw new IllegalStateException("Visa already exists for this country"); });

        Visa visa = Visa.builder()
                .passport(passport)
                .country(country)
                .build();
        Visa saved = visaRepository.save(visa);
        return saved.toDto();
    }

    /** 비자 취소(삭제) */
    @Transactional
    public void cancelVisa(String passportNo, String countryCode) {
        Passport passport = passportRepository.findByPassportNo(passportNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid passportNo: " + passportNo));
        Country country = countryRepository.findByCode(countryCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid countryCode: " + countryCode));

        Visa visa = visaRepository.findByPassportAndCountry(passport, country)
                .orElseThrow(() -> new IllegalArgumentException("Visa not found"));
        visaRepository.delete(visa);
    }
}