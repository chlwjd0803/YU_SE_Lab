// src/main/java/com/example/gptpassport/dto/PassportCreateDto.java
package com.example.gptpassport.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportCreateDto {
    @NotBlank
    @Pattern(regexp = "[A-Z]{2}", message = "countryCode는 ISO alpha-2여야 합니다")
    private String countryCode;

    @NotBlank
    @Pattern(regexp = "BL13[A-Z]{2}[0-9]{4}", message = "passportNo 형식이 올바르지 않습니다")
    private String passportNo;

    @NotBlank
    private String fullName;

    @NotNull
    private LocalDate birthDate;
}
