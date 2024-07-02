package com.bonny.springbootmall.service.impl;

import com.bonny.springbootmall.dao.UserDao;
import com.bonny.springbootmall.dto.UserLoginRequest;
import com.bonny.springbootmall.dto.UserRegisterRequest;
import com.bonny.springbootmall.model.User;
import com.bonny.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
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
        // 使用 MD5 Hash 生成雜湊值，將 String 轉換成 Byte 類型
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    // 在Service層當中，多增加 if-else 的判斷，不要加在 Dao層
    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());


        // 檢查 user 是否存在
        if (user == null){
            log.warn("該 email 【{}】 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用 MD5 Hash 生成雜湊值，將 String 轉換成 Byte 類型
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 要用 "equals" 比較密碼
        if (user.getPassword().equals(hashedPassword)){
            return user;
        } else {
            log.warn("該 email 【{}】 密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}
