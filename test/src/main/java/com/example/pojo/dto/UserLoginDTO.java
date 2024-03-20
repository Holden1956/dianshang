package com.example.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ProjectName: cqjtu-store
 * @Titile: UserLoginDTO
 * @Author: Lucky
 * @Description: 封装用户登录信息
 */
@Data
// todo   添加在线文档需要的注解
@ApiModel(value = "用户登录信息",description = "对用户登录信息进行封装")
public class UserLoginDTO {
    //todo 非空校验
    /*用户名*/
    @NotBlank(message = "登录用户名不能为空")
    @ApiModelProperty(value = "用户名",name = "username",example = "miku",required = true)
    private String username;
    /*用户密码*/
    @NotBlank(message = "登陆密码不能为空")
    @ApiModelProperty(value = "登陆密码",name = "password",example = "123",required = true)
    private String password;
}
