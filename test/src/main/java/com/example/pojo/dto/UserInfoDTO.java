package com.example.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author:xuanhe
 * @date:2024/2/28 17:17
 * @modyified By:
 */
@Data
@ApiModel(value = "用户个人信息",description = "对用户个人信息进行封装")
public class UserInfoDTO {
        //todo 非空校验
        /*用户名*/
        @NotBlank(message = "用户名不能为空")
        @ApiModelProperty(value = "用户名",name = "username",example = "miku",required = true)
        private String username;

        /*用户电话*/
        @NotBlank(message = "电话不能为空")
        @ApiModelProperty(value = "电话",name = "phone",example = "152****1123",required = true)
        private String phone;

        /*用户邮箱*/
        @NotBlank(message = "邮箱不能为空")
        @ApiModelProperty(value = "邮箱",name = "email",example = "xxxx@qq.com",required = true)
        private String email;

        /*用户性别*/
        @NotNull(message = "性别不能为空")
        @ApiModelProperty(value = "性别",name = "gender",example = "2",required = true)
        private Integer gender;
}
