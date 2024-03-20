package com.example.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Date 2024/3/4 14:13
 * @Author qixuan he
 * @Description: 分页参数封装，自定义，不用PageHelper的封装
 */
@Data
@ApiModel(value = "查询页面信息", description = "为用户呈现某一页面信息进行封装")
public class PageDTO {
    // 每页显示数量
    @NotNull(message = "页面数据大小不能为空")
    @ApiModelProperty(value = "每页显示数量", name = "pageSize", example = "12", required = true)
    private Integer pageSize;
    // 页码
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "页码", name = "pageIndex", example = "1", required = true)
    private Integer pageIndex;
    // 偏移量，后续计算
    private Integer offSet;

    // 每一页从(pageIndex-1)*pageSize;

    public Integer getOffSet() {
        // 当mybatis调用实体类get，set方法时，是调用的默认方法
        // 重写get某属性方法get后面一定要是属性名，一模一样
        return (pageIndex - 1) * pageSize;
    }
}
