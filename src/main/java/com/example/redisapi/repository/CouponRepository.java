package com.example.redisapi.repository;

import com.example.redisapi.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findByCode(String code);
    List<Coupon> findByValidToAfter(LocalDate date);  // 유효기간이 현재 날짜 이후인 쿠폰을 조회
}
