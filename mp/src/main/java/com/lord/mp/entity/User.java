package com.lord.mp.entity;

import java.beans.Transient;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 用户信息表的对应实体
 * @author Lord
 * @date 2019年5月30日
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User extends Model<User> {

    private static final long serialVersionUID = 2603890837103787306L;

    /**
     * 主键 设置类型为自动曾长
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名 value为该属性对应的数据库表中的字段名称
     * SqlCondition.LIKE 在设置queryWrapper查询时类似like的作用
     */
    @TableField(value = "name", condition = SqlCondition.LIKE)
    private String name;

    /**
     * 年龄 设置字段格式类型
     */
    @TableField(condition = "%s&lt;#{%s}")
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 直属上级id
     */
    private Integer managerId;

    /**
     * 创建时间
     */
    private Date createTime;

    /*
     * 备注（不与数据库字段对应） # transient 不参与序列化
     * exist = false 表示不是数据库中的字段，在实例化对象set值以后不会保存到数据库
     */
    @TableField(exist = false)
    private String remark;

}
