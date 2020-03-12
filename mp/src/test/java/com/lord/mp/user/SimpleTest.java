package com.lord.mp.user;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lord.mp.MpApplicationTests;
import com.lord.mp.dao.UserMapper;
import com.lord.mp.entity.User;

public class SimpleTest extends MpApplicationTests {

    @Autowired
    private UserMapper userMapper;

    //新增一条记录里
    @Test
    public void insert() {
        User user = new User();
        user.setName("丁茂义");
        user.setAge(40);
        user.setEmail("dmy@baomidou.com");
        user.setManagerId(2);
        user.setCreateTime(new Date());
        int rows = userMapper.insert(user);
        System.out.println("影响记录数：" + rows);
        System.out.println("主键：" + user.getId());
    }

    //查询所有记录列表
    @Test
    public void select() {
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
