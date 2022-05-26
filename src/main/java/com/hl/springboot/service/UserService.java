package com.hl.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.springboot.entity.User;
import com.hl.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private UserMapper userMapper;


    public boolean saveUser(User user) {
        return saveOrUpdate(user);
    }
}
