package com.example.redisapi.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "coupon_type")
@Data
public class CouponType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 쿠폰타입
     * S: 특정 상품에 한정된 쿠폰
     * P: n% 할인 쿠폰
     * F: 배송료 무료 쿠폰
     */
    @Column(unique = true, nullable = false)
    private String code;
    private String description;
    private Double discountPercentage;  // 할인율
    private String productCode;         // 특정 할인 상품 코드
    private Boolean freeShipping;       // 무료 배송 여부

    @OneToMany(mappedBy = "couponType")
    private Set<Coupon> coupons;

}
