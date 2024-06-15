package com.example.redisapi.controller;

import com.example.redisapi.domain.Coupon;
import com.example.redisapi.domain.UserCoupon;
import com.example.redisapi.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/generate")
    public List<String> generateCoupons(@RequestParam int count, @RequestParam String type) {
        return couponService.generateCoupons(count, type);
    }

    @GetMapping("/available")
    public List<Coupon> getAvailableCoupons() {
        return couponService.getAvailableCoupons();
    }

    @GetMapping("/remaining/{userId}")
    public Integer getRemainingCoupons(@PathVariable Long userId) {
        return couponService.getRemainingCoupons(userId);
    }

    @PostMapping("/issue/{userId}")
    public String issueCoupon(@PathVariable Long userId, @RequestParam String couponCode) {
        return couponService.issueCoupon(userId, couponCode);
    }

    @GetMapping("/user/{userId}")
    public List<UserCoupon> getUserCoupons(@PathVariable Long userId) {
        return couponService.getCouponsByUserId(userId);
    }

    @PostMapping("/use/{userId}")
    public String useCoupon(@PathVariable Long userId, @RequestParam String couponCode) {
        try {
            couponService.useCoupon(userId, couponCode);
            return "사용된 쿠폰: " + couponCode;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

}
