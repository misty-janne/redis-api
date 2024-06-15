package com.example.redisapi.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private boolean used;
    private LocalDateTime issuedDate;
    private LocalDateTime usedDate;

    @PrePersist
    public void prePersist() {
        // 현재 날짜 설정
        issuedDate = LocalDateTime.now();
    }

}
