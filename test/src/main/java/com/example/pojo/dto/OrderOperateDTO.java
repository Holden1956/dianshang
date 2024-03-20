package com.example.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class OrderOperateDTO {
    //操作码
    @NotNull(message = "操作码不能为空")
    private Integer operateCode;
    //订单id
    @NotNull(message = "订单id不能为空")
    private Integer id;
    //用户姓名
    @NotEmpty(message = "用户名不能为空")
    private String user_name;

}
