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
     * F: 배송료 무료 쿠폰
     * P: n% 할인 쿠폰
     * S: 특정 상품에 한정된 쿠폰
     */
    @Column(nullable = false)
    private String code;
    @Column(unique = true)
    private String codeId;  // code + id
    private String description;
    // F: 배송료 무료 쿠폰
    @OneToOne(mappedBy = "couponType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CouponTypeFreeShip freeShipping;
    // P: n% 할인 쿠폰
    @OneToOne(mappedBy = "couponType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CouponTypePercentage discountPercentage;
    // S: 특정 상품에 한정된 쿠폰
    @OneToOne(mappedBy = "couponType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CouponTypeSpecific productCode;

    @OneToMany(mappedBy = "couponType")
    private Set<Coupon> coupons;

    @PostPersist
    public void generateCodeDetail() {
        if (code != null && !code.isEmpty()) {
            // ID 값을 이용하여 고유한 코드 생성
            codeId = code + "-" + id;
        }
    }

}
