// src/main/java/com/example/gptpassport/dto/VisaReqDto.java
package com.example.gptpassport.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisaReqDto {
    @NotBlank
    @Pattern(regexp = "[A-Z]{2}")  // ISO country code
    private String countryCode;
}
