package com.bonny.springbootmall.service.impl;

import com.bonny.springbootmall.dao.UserDao;
import com.bonny.springbootmall.dto.UserRegisterRequest;
import com.bonny.springbootmall.model.User;
import com.bonny.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }




}
