package com.bonny.springbootmall.service.impl;

import com.bonny.springbootmall.dao.UserDao;
import com.bonny.springbootmall.dto.UserRegisterRequest;
import com.bonny.springbootmall.model.User;
import com.bonny.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;



@Component
public class UserServiceImpl implements UserService {

    // 利用 "log" 來紀錄檢查資訊
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    // 區分
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查 註冊的 email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null){
            log.warn("該 email 【{}】 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }




}
