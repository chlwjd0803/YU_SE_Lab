// src/main/java/com/example/gptpassport/service/PassportService.java
package com.example.gptpassport.service;

import com.example.gptpassport.dto.PassportCreateDto;
import com.example.gptpassport.dto.PassportReqDto;
import com.example.gptpassport.dto.PassportResDto;
import com.example.gptpassport.entity.Country;
import com.example.gptpassport.entity.Passport;
import com.example.gptpassport.repository.CountryRepository;
import com.example.gptpassport.repository.PassportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PassportService {

    private final PassportRepository passportRepository;
    private final CountryRepository countryRepository;

    /** 새 여권 등록 */
    @Transactional
    public PassportResDto createPassport(PassportCreateDto dto) {
        Country country = countryRepository.findByCode(dto.getCountryCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid countryCode: " + dto.getCountryCode()));

        // 여권번호 중복 검사
        if (passportRepository.findByPassportNo(dto.getPassportNo()).isPresent()) {
            throw new IllegalStateException("Passport no already exists: " + dto.getPassportNo());
        }

        Passport passport = Passport.builder()
                .country(country)
                .passportNo(dto.getPassportNo())
                .fullName(dto.getFullName())
                .birthDate(dto.getBirthDate())
                .build();

        Passport saved = passportRepository.save(passport);
        return saved.toDto();
    }

    /** 여권 조회 */
    public PassportResDto getPassport(String passportNo) {
        Passport passport = passportRepository.findByPassportNo(passportNo)
                .orElseThrow(() -> new IllegalArgumentException("Passport not found: " + passportNo));
        return passport.toDto();
    }

    /** 여권 정보 일부 수정 */
    @Transactional
    public PassportResDto updatePassport(String passportNo, PassportReqDto dto) {
        Passport passport = passportRepository.findByPassportNo(passportNo)
                .orElseThrow(() -> new IllegalArgumentException("Passport not found: " + passportNo));

        passport.patch(dto);
        return passport.toDto();
    }

    /** 여권 삭제 */
    @Transactional
    public void deletePassport(String passportNo) {
        Passport passport = passportRepository.findByPassportNo(passportNo)
                .orElseThrow(() -> new IllegalArgumentException("Passport not found: " + passportNo));
        passportRepository.delete(passport);
    }
}
