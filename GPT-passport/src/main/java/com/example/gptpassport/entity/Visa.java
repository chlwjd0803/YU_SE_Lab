package com.example.gptpassport.entity;

import com.example.gptpassport.dto.VisaResDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        name = "visa",
        uniqueConstraints = @UniqueConstraint(columnNames = {"passport_id", "country_id"})
)
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 비자가 발급된 여권
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passport_id", nullable = false)
    private Passport passport;

    // 비자가 발급된 국가
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // 비자 유효 시작일 (생성 시점)
    @Column(name = "start_date", nullable = false, updatable = false)
    private LocalDate startDate;

    // 비자 유효 종료일 (시작일로부터 1년 후)
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @PrePersist
    private void prePersistDates() {
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusYears(1);
    }

    /** VisaResDto로 변환 **/
    public VisaResDto toDto() {
        return VisaResDto.builder()
                .countryCode(this.country.getCode())
                .countryName(this.country.getName())
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}