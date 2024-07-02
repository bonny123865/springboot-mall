package com.bonny.springbootmall.controller;

import com.bonny.springbootmall.dto.UserRegisterRequest;
import com.bonny.springbootmall.model.User;
import com.bonny.springbootmall.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@NotBlank

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 類似於新增商品 (createProduct)
    // 創建 : 對應到 POST 方法
    // 隱密的東西需要用 "RequestBody" 傳遞參數 (POST)
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);


    }




}
