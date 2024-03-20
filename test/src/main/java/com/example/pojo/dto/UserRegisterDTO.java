package com.example.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author:xuanhe
 * @date:2024/2/27 11:19
 * @modyified By:
 */
@Data
@ApiModel(value = "用户注册信息",description = "对用户注册的信息进行封装")
public class UserRegisterDTO {//将前台数据转为特定对象，封装
    /*用户id*/
    @NotNull(message = "注册id不能为空")
    @ApiModelProperty(value = "用户ID",name = "id",example = "1",required = true)
    private Integer id;

    /*注册用户名*/
    @NotBlank(message = "注册用户名不能为空")
    @ApiModelProperty(value = "用户名",name = "username",example = "miku",required = true)
    private String username;

    /*注册的密码*/
    @ApiModelProperty(value = "用户密码",name = "password",example = "123",required = true)
    @NotBlank(message = "注册密码不能为空")
    private String password;

    /*注册的重复密码*/
    @NotBlank(message = "注册的重复密码不能为空")
    @ApiModelProperty(value = "重复用户密码",name = "repassword",example = "123",required = true)
    private String repassword;
}
