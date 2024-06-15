package com.example.redisapi.controller;

import com.example.redisapi.domain.CouponType;
import com.example.redisapi.service.CouponTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/types")
public class CouponTypeController {

    @Autowired
    private CouponTypeService couponTypeService;

    @PostMapping
    public CouponType createType (@RequestBody CouponType couponType) {
        return couponTypeService.save(couponType);
    }

    @GetMapping("/{code}")
    public Iterable<CouponType> getTypeByCode (@PathVariable String code) {
        return couponTypeService.findByCode(code);
    }

    @GetMapping
    public Iterable<CouponType> getAllTypes () {
        return couponTypeService.findAll();
    }
}
