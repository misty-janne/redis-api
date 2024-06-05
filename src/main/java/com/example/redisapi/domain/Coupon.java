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
    @JoinColumn(name = "type", referencedColumnName = "code")
    private CouponType couponType;

    private LocalDate validFrom;
    private LocalDate validTo;

}
