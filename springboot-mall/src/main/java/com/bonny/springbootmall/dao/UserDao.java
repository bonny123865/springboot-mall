package com.bonny.springbootmall.dao;

import com.bonny.springbootmall.dto.UserRegisterRequest;
import com.bonny.springbootmall.model.User;

public interface UserDao {
    User getUserById(Integer userId);
    User getUserByEmail(String email);
    Integer createUser(UserRegisterRequest userRegisterRequest);
}
