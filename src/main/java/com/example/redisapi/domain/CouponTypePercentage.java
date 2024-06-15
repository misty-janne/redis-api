package com.example.redisapi.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "coupon_type_percentage")
@Data
public class CouponTypePercentage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "coupon_type_id")
    private CouponType couponType;

    private Double discountPercentage;
}
