package com.example.redisapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    private RedisTemplate<String, String> couponRedisTemplate;

    private static final String COUPON_KEY = "Coupon";

    //랜덤한 쿠폰 번호 생성
    private String generateRandomCoupon() {
        return UUID.randomUUID().toString();
    }

    //쿠폰 생성 및 redis에 저장
    public List<String> generateCoupons(int count) {
        List<String> coupons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String coupon = generateRandomCoupon();
            couponRedisTemplate.opsForList().rightPush(COUPON_KEY, coupon);
            coupons.add(coupon);
        }
        return coupons;
    }

    //쿠폰 발급 (pop)
    public String issueCoupon() {
        return couponRedisTemplate.opsForList().leftPop(COUPON_KEY);
    }

}
