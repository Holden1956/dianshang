package com.example.pojo.vo;

import lombok.Data;

/**
 * @author:xuanhe
 * @date:2024/2/28 16:36
 * @modyified By:
 */
@Data
public class UserInfoVO {// 用于个人资料修改
    // 用户名
    private String username;
    // 电话
    private String phone;
    // 邮箱
    private String email;
    // 性别
    private Integer gender;
}
