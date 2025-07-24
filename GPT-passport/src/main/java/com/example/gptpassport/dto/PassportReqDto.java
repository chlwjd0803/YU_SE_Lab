// src/main/java/com/example/gptpassport/dto/PassportReqDto.java
package com.example.gptpassport.dto;

import lombok.*;

import jakarta.validation.constraints.Pattern;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportReqDto {
    /**
     * 여권 번호 변경 시 사용.
     * 패턴: BL13 + 알파벳 2글자 + 숫자 4글자
     */
    @Pattern(regexp = "BL13[A-Z]{2}[0-9]{4}")
    private String passportNo;

    /** 여권 소지자 이름 변경 시 사용 */
    private String fullName;
}
