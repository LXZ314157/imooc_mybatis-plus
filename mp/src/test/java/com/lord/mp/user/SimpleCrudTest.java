package com.lord.mp.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lord.mp.MpApplicationTests;
import com.lord.mp.dao.UserMapper;
import com.lord.mp.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Mapper层的简单的crud操作
 */
public class SimpleCrudTest extends MpApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增===================================================================================================
     */
    //新增一条记录里
    @Test
    public void insert() {
        User user = new User().setName("丁茂义").setAge(40).setEmail("dmy@baomidou.com").setManagerId(2).setCreateTime(new Date());
        int rows = userMapper.insert(user);
        System.out.println("影响记录数：" + rows);
        System.out.println("主键：" + user.getId());
    }


    /**
     * 查询===================================================================================================
     */
    //根据id查找一条记录
    @Test
    public void selectById(){
        User user = userMapper.selectById(2);
        System.out.println(user);
    }

    //根据ids查找多条记录
    @Test
    public void selectBatchIds(){
        List<Integer> list = Arrays.asList(1,2,3);
        List<User> userList = userMapper.selectBatchIds(list);
        System.out.println(userList);
    }

    //根据map封装的参数查询
    @Test
    public void selectByMap(){
        Map<String,Object> params = new HashMap<>();
        //key对应的是数据表的字段
        params.put("name","张三丰");
        List<User> list = userMapper.selectByMap(params);
        System.out.println(list);
    }


    //查询所有记录列表
    @Test
    public void select() {
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    //======条件构造器查询
    @Test
    public void selectByWrapper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //column是表字段的名称，不是实体字段的名称
        //1、姓名含有”张“，年龄小于40岁
//        queryWrapper.like("name","张").lt("age",40);

        //2、姓名含有“张"，年龄在20到40之间，email字段不为空
//        queryWrapper.like("name","张").between("age",20,40).isNotNull("email");

//        3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
//        name like '王%' or age>=25 order by age desc,id asc
//        queryWrapper.likeRight("name","王").or().ge("age",25).orderByDesc("age").orderByAsc("id");

//        4、创建日期为2019年2月14日并且直属上级为名字为王姓
//        date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
//          queryWrapper.ge("create_time","2019-02-05");

//        5、名字为王姓并且（年龄小于40或邮箱不为空）
//        name like '王%' and (age<40 or email is not null)

//        6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
//        name like '王%' or (age<40 and age>20 and email is not null)
//        queryWrapper.likeRight("name","王").and(qw->qw.lt("age",40).or().isNotNull("email"));

//        7、（年龄小于40或邮箱不为空）并且名字为王姓
//       (age<40 or email is not null) and name like '王%'
//        queryWrapper.nested(qw->qw.lt("age",40).or().isNotNull("email")).likeRight("name","王");

//        8、年龄为30、31、34、35
//        age in (30、31、34、35)
//        queryWrapper.in("age",Arrays.asList(30,31,34,35));

//        9、只返回满足条件的其中一条语句即可
//        limit 1
//        queryWrapper.in("age",Arrays.asList(30,31,34,35)).last("limit 1");

        //限制查询特定字段（数据库字段，select('xxx','xxx')）
//        queryWrapper.select("id","name").in("age",Arrays.asList(30,31,34,35)).last("limit 1");

        //排除不显示的字段
        queryWrapper.in("age",Arrays.asList(30,31,34,35)).last("limit 1")
                .select(User.class,info->!info.getColumn().equals("id")&& !info.getColumn().equals("name"));

        List<User> list = userMapper.selectList(queryWrapper);
        System.out.println(list);
    }

    //======condition查询
    @Test
    public void testCondition(){
        String name = "王";
        String email = "@";
        condition(name,email);
    }

    public void condition(String name,String email){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),"name",name)
                .like(StringUtils.isNotEmpty(email),"email",email);
        List<User> list = userMapper.selectList(queryWrapper);
        System.out.println(list);
    }


    @Test
    public void testConditionWithEntity(){

        //条件构造器传入实体对象
//        User user = new User();
//        user.setName("张三丰").setEmail("3423");
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
//        List<User> list = userMapper.selectList(queryWrapper);
//        System.out.println(list);

        //返回结果是List<Map<String,Object>>类型 只会显示值不为null的字段
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("id","name").like("name","张").eq("age",40);
//        List<Map<String,Object>> mapList = userMapper.selectMaps(queryWrapper);
//        System.out.println(mapList);

        //查询返回记录行数
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","张").eq("age",40);
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }


    //统计查询
    @Test
    public void testFunction(){
//        11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组。
//        select avg(age) avg_age,min(age) min_age,max(age) max_age from user
//        group by manager_id having sum(age) <500
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(age) avg_age","min(age) min_age","max(age) max_age")
                .groupBy("manager_id").having("sum(age)<{0}",300);
        List<Map<String,Object>> mapList = userMapper.selectMaps(queryWrapper);
        System.out.println(mapList);
    }

}
