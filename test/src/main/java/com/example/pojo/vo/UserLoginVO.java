package com.example.pojo.vo;

import lombok.Data;

/**
 * @ProjectName: cqjtu-store
 * @Titile: UserLoginVO
 * @Author: Lucky
 * @Description: 登录成功后，返回给前端的VO
 */
@Data
public class UserLoginVO {// 当前登录用户，后台为其生成token，再一并返回给前端
    // 用户id
    private Integer id;
    // 用户名
    private String username;
    // 后台产生的令牌token，携带用户信息
    private String token;
}
