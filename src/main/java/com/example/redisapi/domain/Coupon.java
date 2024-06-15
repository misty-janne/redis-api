package com.example.redisapi.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "coupon_table")
@Data
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "codeId")
    private CouponType couponType;

    private LocalDate validFrom;
    private LocalDate validTo;

    @PrePersist
    public void prePersist() {
        // 현재 날짜 설정
        validFrom = LocalDate.now();

        // 한 달 뒤의 날짜 설정
        validTo = validFrom.plusMonths(1);
    }

}
