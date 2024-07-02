package com.bonny.springbootmall.service;

import com.bonny.springbootmall.dto.UserLoginRequest;
import com.bonny.springbootmall.dto.UserRegisterRequest;
import com.bonny.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);

    User login(UserLoginRequest userLoginRequest);


}
