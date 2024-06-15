package com.example.redisapi.service;

import com.example.redisapi.domain.CouponType;
import com.example.redisapi.repository.CouponTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponTypeService {

    @Autowired
    private CouponTypeRepository couponTypeRepository;

    public CouponType save(CouponType couponType) {
        return couponTypeRepository.save(couponType);
    }

    public Optional<CouponType> findByCodeId(String codeId) {
        return couponTypeRepository.findByCodeId(codeId);
    }

    public Iterable<CouponType> findByCode(String code) {
        return couponTypeRepository.findByCode(code);
    }

    public Iterable<CouponType> findAll() {
        return couponTypeRepository.findAll();
    }
}
