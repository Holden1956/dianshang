package com.example.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Titile: R
 * @Description: 封装返回类型
 */
@Data
@ApiModel("R类")
public class R<T> {
    /*状态码*/
    @NotNull(message = "状态码不能为空")
    @ApiModelProperty(value = "状态码",name="1",example = "500",required = true)
    private int code;
    /*信息提示*/
    @NotBlank(message = "提示信息不能为空")
    @ApiModelProperty(value = "提示信息",name = "name",example = "Here",required = true)
    private String message;
    /*携带的数据*/
    @NotNull(message = "携带数据不能为空")
    @ApiModelProperty(value = "携带数据",name = "name",example = "Here",required = true)
    private T data;//泛型T

    /**
     * 操作成功，并且携带数据
     * @param data 数据
     * @return
     * @param <T>
     */
    public static <T> R<T> ok(T data){
        R<T> r = new R<T>();
        r.setCode(ServiceCode.OK.getValue());
        r.setData(data);
        r.message="ok";
        return r;
    }

    /**
     * 操作成功，不携带数据
     * @return
     */
    public static R<Void> ok(){
        return ok(null);
    }

    /**
     * 操作失败
     * @param serviceCode
     * @param message
     * @return
     * @param <Void>
     */
    public static<Void> R<Void> fail(ServiceCode serviceCode,String message){
        R<Void> r = new R<>();
        r.setCode(serviceCode.getValue());
        r.setMessage(message);
        return r;
    }

    /**
     * 由于业务异常，操作失败
     * @param e
     * @return
     */
    public static R<Void> fail(ServiceException e){
        return fail(e.getServiceCode(),e.getMessage());
    }
}
