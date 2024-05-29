package com.example.redisapi.controller;

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
    public List<String> generateCoupons(@RequestParam int count) {
        return couponService.generateCoupons(count);
    }

    @GetMapping("/issue")
    public String issueCoupon() {
        return couponService.issueCoupon();
    }

}
