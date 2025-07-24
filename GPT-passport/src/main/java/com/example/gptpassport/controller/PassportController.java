package com.example.gptpassport.controller;

import com.example.gptpassport.dto.PassportCreateDto;
import com.example.gptpassport.dto.PassportReqDto;
import com.example.gptpassport.dto.PassportResDto;
import com.example.gptpassport.service.PassportService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passports")
@RequiredArgsConstructor
public class PassportController {

    private final PassportService passportService;

    /** 여권 생성 */
    @PostMapping
    public ResponseEntity<PassportResDto> create(
            @RequestBody @Valid PassportCreateDto dto) {
        PassportResDto res = passportService.createPassport(dto);
        return ResponseEntity.status(201).body(res);
    }

    /** 여권 조회 */
    @GetMapping("/{passportNo}")
    public ResponseEntity<PassportResDto> get(
            @PathVariable String passportNo) {
        return ResponseEntity.ok(passportService.getPassport(passportNo));
    }

    /** 여권 정보 수정 (passportNo, fullName 중 선택적 변경) */
    @PatchMapping("/{passportNo}")
    public ResponseEntity<PassportResDto> update(
            @PathVariable String passportNo,
            @RequestBody @Valid PassportReqDto dto) {
        return ResponseEntity.ok(passportService.updatePassport(passportNo, dto));
    }

    /** 여권 삭제 */
    @DeleteMapping("/{passportNo}")
    public ResponseEntity<Void> delete(
            @PathVariable String passportNo) {
        passportService.deletePassport(passportNo);
        return ResponseEntity.noContent().build();
    }
}
