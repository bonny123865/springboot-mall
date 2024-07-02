package com.bonny.springbootmall.controller;

import com.bonny.springbootmall.dto.UserLoginRequest;
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

    // 會將帳密回傳到系統，所以適合用 POST、(PUT強烈表達更新已存在數據)
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){

        User user = userService.login(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }



}
