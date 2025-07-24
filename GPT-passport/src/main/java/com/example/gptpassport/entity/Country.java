package com.example.gptpassport.entity;

import com.example.gptpassport.dto.CountryDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        name = "country",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code"),
                @UniqueConstraint(columnNames = "name")
        }
)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    public CountryDto toDto() {
        return CountryDto.builder()
                .code(this.code)
                .name(this.name)
                .build();
    }
}
