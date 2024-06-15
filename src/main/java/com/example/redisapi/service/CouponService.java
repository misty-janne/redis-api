package com.example.redisapi.service;

import com.example.redisapi.domain.Coupon;
import com.example.redisapi.domain.CouponType;
import com.example.redisapi.domain.User;
import com.example.redisapi.domain.UserCoupon;
import com.example.redisapi.repository.CouponRepository;
import com.example.redisapi.repository.CouponTypeRepository;
import com.example.redisapi.repository.UserCouponRepository;
import com.example.redisapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponTypeRepository couponTypeRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> couponRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    private static final String COUPON_KEY = "Coupon";
    private static final String COUPON_CACHE_KEY = "available_coupons";

    //랜덤한 쿠폰 번호 생성
    private String generateRandomCoupon() {
        return UUID.randomUUID().toString();
    }

    //쿠폰 생성
    public List<String> generateCoupons(int count, String codeId) {
        Optional<CouponType> typeOptional = couponTypeRepository.findByCodeId(codeId);
        if (!typeOptional.isPresent()) {
            throw new IllegalArgumentException("유효한 코드타입이 아닙니다.");
        }

        CouponType couponType = typeOptional.get();
        List<String> couponCodes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Coupon coupon = new Coupon();
            coupon.setCode(generateRandomCoupon());
            coupon.setCouponType(couponType);
            coupon.setValidFrom(LocalDate.now());
            // 기본 유효기간 한달
            coupon.setValidTo(LocalDate.now().plusMonths(1));
            couponRepository.save(coupon);
            couponCodes.add(coupon.getCode());
        }

        // 쿠폰생성시 redis cache 삭제(업데이트)
        redisTemplate.delete(COUPON_CACHE_KEY);

        return couponCodes;
    }

    public List<Coupon> getAvailableCoupons() {
        List<Coupon> coupons = (List<Coupon>) redisTemplate.opsForValue().get(COUPON_CACHE_KEY);
        if (coupons == null) {
            coupons = couponRepository.findByValidToAfter(LocalDate.now());
            redisTemplate.opsForValue().set(COUPON_CACHE_KEY, coupons, Duration.ofMinutes(10));
        }
        return coupons;
    }

    public Integer getRemainingCoupons(Long userId) {
        String key = "user:" + userId + ":remaining_coupons";
        Integer remainingCoupons = (Integer) redisTemplate.opsForValue().get(key);
        if (remainingCoupons == null) {
            remainingCoupons = userCouponRepository.countByUserIdAndUsedFalse(userId);
            redisTemplate.opsForValue().set(key, remainingCoupons, Duration.ofMinutes(10));
        }
        return remainingCoupons;
    }

    public void updateRemainingCouponsInCache(Long userId) {
        String key = "user:" + userId + ":remaining_coupons";
        Integer remainingCoupons = userCouponRepository.countByUserIdAndUsedFalse(userId);
        redisTemplate.opsForValue().set(key, remainingCoupons, Duration.ofMinutes(10));
    }

    public String issueCoupon(Long userId, String couponCode) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return "User not found";
        }

        User user = userOptional.get();
        Coupon coupon = couponRepository.findByCode(couponCode);

        if (coupon == null || coupon.isIssued()) {
            return "사용할 수 없는 쿠폰입니다.";
        }

        coupon.setIssued(true);
        couponRepository.save(coupon);

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);
        userCoupon.setUsed(false);
        userCouponRepository.save(userCoupon);

        String key = "user:" + userId + ":coupons";
        List<UserCoupon> userCoupons = (List<UserCoupon>) redisTemplate.opsForValue().get(key);
        if (userCoupons == null) {
            userCoupons = new ArrayList<>();
        }
        userCoupons.add(userCoupon);
        redisTemplate.opsForValue().set(key, userCoupons, Duration.ofMinutes(10));

        //쿠폰 발행시 redis cache 클리어
        redisTemplate.delete(COUPON_CACHE_KEY);

        updateRemainingCouponsInCache(userId);

        return "Coupon issued: " + coupon.getCode();
    }

    public List<UserCoupon> getCouponsByUserId(Long userId) {
        Iterable<UserCoupon> userCoupons = userCouponRepository.findByUserId(userId);
        List<UserCoupon> userCouponList = new ArrayList<>();
        userCoupons.forEach(userCouponList::add);
        return userCouponList;
    }

    public void useCoupon(Long userId, String couponCode) {
        UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponCode(userId, couponCode);
        if (userCoupon != null && !userCoupon.isUsed()) {
            userCoupon.setUsed(true);
            userCoupon.setUsedDate(LocalDateTime.now());
            userCoupon.getCoupon().setUsed(true);
            userCouponRepository.save(userCoupon);
            couponRepository.save(userCoupon.getCoupon());
            updateRemainingCouponsInCache(userId);

            //쿠폰 사용시 redis cache 클리어
            redisTemplate.delete(COUPON_CACHE_KEY);

        } else {
            throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
        }
    }


    /** redis DB 테스트
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
     **/

}
