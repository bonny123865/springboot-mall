package com.bonny.springbootmall.dao;

import com.bonny.springbootmall.dto.UserRegisterRequest;
import com.bonny.springbootmall.model.User;

public interface UserDao {
    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);
}
