package com.example.gptpassport.entity;

import com.example.gptpassport.dto.PassportReqDto;
import com.example.gptpassport.dto.PassportResDto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(
        name = "passport",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "passport_no")
        }
)
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 여권 발급 국가
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // 여권 번호 (예: "BL13AB1234")
    @Column(name = "passport_no", nullable = false, unique = true)
    private String passportNo;

    // 여권 소지자 전체 이름
    @Column(name = "full_name", nullable = false)
    private String fullName;

    // 생년월일
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    // 발급일 (생성 시점 자동 설정)
    @Column(name = "issue_date", nullable = false, updatable = false)
    private LocalDate issueDate;

    // 만료일 (발급일로부터 10년 후 자동 설정)
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @PrePersist
    private void prePersistDates() {
        this.issueDate = LocalDate.now();
        this.expiryDate = this.issueDate.plusYears(10);
    }

    /** PassportResDto로 변환 **/
    public PassportResDto toDto() {
        return PassportResDto.builder()
                .passportNo(this.passportNo)
                .countryCode(this.country.getCode())
                .fullName(this.fullName)
                .birthDate(this.birthDate)
                .issueDate(this.issueDate)
                .expiryDate(this.expiryDate)
                .build();
    }

    /**
     * 부분 업데이트 메서드
     * - passportNo: 정규식 "BL13[A-Z]{2}[0-9]{4}"에 맞지 않으면 변경하지 않음
     * - fullName: null이 아니면 변경
     */
    public void patch(PassportReqDto dto) {
        if (dto.getPassportNo() != null && dto.getPassportNo().matches("BL13[A-Z]{2}[0-9]{4}")) {
            this.passportNo = dto.getPassportNo();
        }
        if (dto.getFullName() != null) {
            this.fullName = dto.getFullName();
        }
    }
}

