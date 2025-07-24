// src/main/java/com/example/gptpassport/dto/VisaResDto.java
package com.example.gptpassport.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisaResDto {
    private String countryCode;
    private String countryName;
    private LocalDate startDate;
    private LocalDate endDate;
}
