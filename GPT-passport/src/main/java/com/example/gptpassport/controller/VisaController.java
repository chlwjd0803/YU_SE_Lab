package com.example.gptpassport.controller;

import com.example.gptpassport.dto.VisaReqDto;
import com.example.gptpassport.dto.VisaResDto;
import com.example.gptpassport.service.VisaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passports/{passportNo}/visas")
@RequiredArgsConstructor
public class VisaController {

    private final VisaService visaService;

    /** 비자 목록 조회 */
    @GetMapping
    public ResponseEntity<List<VisaResDto>> getVisas(
            @PathVariable String passportNo) {
        return ResponseEntity.ok(visaService.getVisasByPassportNo(passportNo));
    }

    /** 비자 발급 */
    @PostMapping
    public ResponseEntity<VisaResDto> issueVisa(
            @PathVariable String passportNo,
            @RequestBody VisaReqDto req) {
        VisaResDto dto = visaService.issueVisa(passportNo, req);
        return ResponseEntity.status(201).body(dto);
    }

    /** 비자 취소 */
    @DeleteMapping("/{countryCode}")
    public ResponseEntity<Void> cancelVisa(
            @PathVariable String passportNo,
            @PathVariable String countryCode) {
        visaService.cancelVisa(passportNo, countryCode);
        return ResponseEntity.noContent().build();
    }
}