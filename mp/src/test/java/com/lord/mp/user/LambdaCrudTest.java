package com.lord.mp.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lord.mp.MpApplicationTests;
import com.lord.mp.dao.UserMapper;
import com.lord.mp.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Lambda条件构造器
 */
public class LambdaCrudTest extends MpApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectLambda(){
        LambdaQueryWrapper<User> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.like(User::getName,"张").lt(User::getAge,30);
        List<User> list =  userMapper.selectList(lambdaQuery);
        System.out.println(list);
    }

}
