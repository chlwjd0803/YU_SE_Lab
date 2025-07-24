// src/main/java/com/example/gptpassport/dto/PassportResDto.java
package com.example.gptpassport.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PassportResDto {
    private String passportNo;
    private String countryCode;
    private String fullName;
    private LocalDate birthDate;
    private LocalDate issueDate;
    private LocalDate expiryDate;
}
