package com.example.pojo.commonPojo;

import lombok.Data;

import java.util.Date;

/**
 * @author:xuanhe
 * @date:2024/2/27 10:13
 * @modyified By:
 */
@Data
public class User {
    // 用户id,使用包装类，全部使用的是引用数据类型
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 盐值
    private String salt;
    // 是否删除
    private Integer isDelete;
    // 电话号码
    private String phone;
    // 邮箱
    private String email;
    // 性别
    private Integer gender;
    // 头像
    private String avatar;
    // 创建用户
    private String createdUser;
    // 创建时间
    private Date createdTime;
    // 最后修改用户
    private String modifiedUser;
    // 最后修改时间
    private Date modifiedTime;
}