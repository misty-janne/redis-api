package com.example.redisapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;

//@RedisHash("User")
@Entity
@Table(name = "user_table")
@Data
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;

    private String name;
    private String email;
    private String password;


}
