package com.example.redisapi.controller;

import com.example.redisapi.domain.User;
import com.example.redisapi.service.UserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
//@Api(tags = "User Management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
//    @ApiOperation(value = "Create a new user")
    public User createUser (@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/{id}")
//    @ApiOperation(value = "Get a user by ID")
    public Optional<User> getUserById (@PathVariable String id) {
        return userService.findById(id);
    }
    @GetMapping
//    @ApiOperation(value = "Get all users")
    public Iterable<User> getAllUsers () {
        return userService.findAll();
    }
    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Delete a user by ID")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteById(id);
    }

}
