package com.lord.mp.user;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lord.mp.MpApplicationTests;
import com.lord.mp.entity.User;
import com.lord.mp.service.UserService;

/**
 * service层的crud
 */
public class UserServiceTest extends MpApplicationTests {

    @Autowired
    private UserService userService;

    // 此处会报错，因为数据库存在多条记录
    @Test
    public void getOne() {
//        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getName, "张三丰"));
//        User one = userService.getOne(new QueryWrapper<User>().eq("name","张三丰"));

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName,"张三丰");
        User one = userService.getOne(lambdaQueryWrapper);
        System.out.println(one);
    }

    @Test
    public void insertBatch() {
        User user1 = new User();
        user1.setName("小可爱");
        user1.setAge(28);
        user1.setManagerId(2);

        User user2 = new User();
        user2.setName("大傻瓜");
        user2.setAge(19);
        user2.setManagerId(2);

        List<User> userList = Arrays.asList(user1, user2);
        boolean flag = userService.saveBatch(userList);
        System.out.println("是否成功：" + flag);
    }

    /**
     * 根据主键判断，如果存在就更新，不存在就新增
     */
    @Test
    public void insertOrUpdateBatch() {
        User user1 = new User();
        user1.setName("李莫愁");
        user1.setAge(28);

        User user2 = new User();
        user2.setId(3);
        user2.setName("张世勋");
        user2.setAge(20);
        user2.setManagerId(4);

        List<User> userList = Arrays.asList(user1, user2);
        boolean flag = userService.saveOrUpdateBatch(userList);
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void selectChain() {
        List<User> userList = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
        userList.forEach(System.out::println);
    }

    /**
     * 根据条件修改
     */
    @Test
    public void updateChain() {
        boolean flag = userService.lambdaUpdate().eq(User::getAge, 25).set(User::getAge, 26).update();
        System.out.println("是否成功：" + flag);
    }

    /**
     * 根据条件删除
     */
    @Test
    public void removeChain() {
        boolean flag = userService.lambdaUpdate().eq(User::getName, "大傻瓜").remove();
        System.out.println("是否成功：" + flag);
    }

    //分页查询
    @Test
    public void testPageIng() {
        PageHelper.startPage(2, 3);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age",30);
        List<User> list = userService.list(queryWrapper);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        System.out.println(JSON.toJSONString(pageInfo));
    }

    //普通的查询
    @Test
    public void commonSelecct(){
        User user = userService.getUserInfo(6);
        System.out.println(user);
    }

}
