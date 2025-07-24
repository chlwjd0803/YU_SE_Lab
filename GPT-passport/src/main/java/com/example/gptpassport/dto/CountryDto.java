// src/main/java/com/example/gptpassport/dto/CountryDto.java
package com.example.gptpassport.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDto {
    private String code;
    private String name;
}
