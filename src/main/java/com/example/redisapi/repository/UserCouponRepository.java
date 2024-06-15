package com.example.redisapi.repository;

import com.example.redisapi.domain.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    Iterable<UserCoupon> findByUserId(Long userId);
    Integer countByUserIdAndUsedFalse(Long userId);
    UserCoupon findByUserIdAndCouponCode(Long userId, String couponCode);
}
