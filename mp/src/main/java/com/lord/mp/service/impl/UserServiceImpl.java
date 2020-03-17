package com.lord.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lord.mp.dao.UserMapper;
import com.lord.mp.entity.User;
import com.lord.mp.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserInfo(Integer id) {
        return userMapper.getUserInfo(id);
    }
}
