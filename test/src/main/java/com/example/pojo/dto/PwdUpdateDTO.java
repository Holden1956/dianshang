package com.example.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author:xuanhe
 * @date:2024/2/28 14:16
 * @modyified By:
 */
@Data
// todo   添加在线文档需要的注解
@ApiModel(value = "密码修改信息",description = "对用户修改密码信息进行封装")
public class PwdUpdateDTO {
    //todo 非空校验
    /*旧密码*/
    @ApiModelProperty(value = "用户旧密码",name = "oldPwd",example = "123",required = true)
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;

    /*新密码*/
    @ApiModelProperty(value = "用户新密码",name = "newPwd",example = "223",required = true)
    @NotBlank(message = "新密码不能为空")
    private String newPwd;

    /*确认新密码*/
    @ApiModelProperty(value = "确认新密码",name = "reNewPwd",example = "223",required = true)
    @NotBlank(message = "确认新密码不能为空")
    private String reNewPwd;
}
