package com.lord.mp.user;

import java.util.Date;

import org.junit.Test;

import com.lord.mp.MpApplicationTests;
import com.lord.mp.entity.User;

/**
 * AR:ActiveRecord 通过继承model来实现crud操作
 *
 */
public class ARTest extends MpApplicationTests {

    @Test
    public void insert() {
        User user = new User();
        user.setName("丁茂义").setAge(40).setEmail("dmy@baomidou.com")
            .setManagerId(2).setCreateTime(new Date());
        boolean flag = user.insert();
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void selectById1() {
        User user = new User();
        User userSelect = user.selectById(1088250446457389058L);
        System.out.println(userSelect == user);
        System.out.println(userSelect);
    }

    @Test
    public void selectById2() {
        User user = new User();
        user.setId(1);
        User userSelect = user.selectById();
        System.out.println(userSelect == user);
        System.out.println(userSelect);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setId(4);
        user.setName("张无忌@@@");
        boolean flag = user.updateById();
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void deleteById() {
        User user = new User();
        user.setId(4);
        boolean flag = user.deleteById();
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void insertOrUpdate() {
        User user = new User();
        user.setAge(35);
        user.setCreateTime(new Date());
        boolean flag = user.insertOrUpdate();
        System.out.println("是否成功：" + flag);
    }
}
