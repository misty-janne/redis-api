package com.example.redisapi.repository;

import com.example.redisapi.domain.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponTypeRepository extends JpaRepository<CouponType, Long> {
    Iterable<CouponType> findByCode(String code);
    Optional<CouponType> findByCodeId(String codeId);
}
