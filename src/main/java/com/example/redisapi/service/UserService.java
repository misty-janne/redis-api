package com.example.redisapi.service;

import com.example.redisapi.domain.User;
import com.example.redisapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

//    @Autowired
//    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    //단일 해시키 방식
    private static final String KEY = "User";

    //개별 키 방식-> "User:"+{id} ex. User:123

    public User save(User user) {
        //sql 데이터베이스에 저장
        //todo..

        //redis 캐시에 저장
        redisTemplate.opsForHash().put(KEY, user.getId(), user);
        return user;
    }

    public Optional<User> findById(String id) {
        //먼저 redis 캐시에서 조회
        Object data = redisTemplate.opsForHash().get(KEY, id);
        if (data != null) {
            // LinkedHashMap을 User 객체로 변환 (역직렬화 수행)
            User user = objectMapper.convertValue(data, User.class);
            return Optional.of(user);
        }

        //redis 데이터 없으면 sql에서 조회
        //todo..

        return Optional.empty();
    }

    public Iterable<User> findAll() {
        List<Object> objects = redisTemplate.opsForHash().values(KEY);
        List<User> users = objects.stream()
                .map(object -> objectMapper.convertValue(object, User.class))
                .toList();
        return users;
    }

    public void deleteById(String id) {
        //sql DB 삭제
        //todo..

        //redis 캐시에서도 삭제
        redisTemplate.opsForHash().delete(KEY, id);
    }




    /**
     public User save(User user) {
     return userRepository.save(user);
     }

     public Optional<User> findById(String id) {
     return userRepository.findById(id);
     }

     public Iterable<User> findAll() {
     return userRepository.findAll();
     }

     public void deleteById(String id) {
     userRepository.deleteById(id);
     }
     *
     */
}
