package com.example.common;

/**
 * 业务状态码枚举类型
 *
 * @author HighEDU
 * @version 0.0.1
 */
public enum ServiceCode {

    /**
     * 成功
     */
    OK(20000),
    /**
     * 错误：请求参数格式有误
     */
    ERR_BAD_REQUEST(40000),
    /**
     * 错误：为空
     */
    ERR_PWD_EMPTY(300001),
    /**
     * 密码错误
     */
    ERR_PWD(40500),
    /**
     * 错误：确认密码错误
     */
    ERR_BAD_REPWD(30000),
    /**
     * 错误：文件过大
     */
    ERR_FILE_SIZE_TOO_BIG(30003),

    /**
     * 错误：登录失败，用户名或密码错
     */
    ERR_UNAUTHORIZED(40100),
    /**
     * 错误：登录失败，账号被禁用
     */
    ERR_UNAUTHORIZED_DISABLED(40101),
    /**
     * 错误：无权限
     */
    ERR_FORBIDDEN(40300),
    /**
     * 错误：数据不存在
     */
    ERR_NOT_FOUND(40400),
    /**
     * 错误：数据冲突
     */
    ERR_CONFLICT(40900),
    /**
     * 错误：数据冲突，已经存在
     */
    ERR_EXISTS(40901),
    /**
     * 错误：数据冲突，已经关联
     */
    ERR_IS_ASSOCIATED(40902),
    /**
     * 错误：插入数据错误
     */
    ERR_SAVE_FAILED(50000),
    /**
     * 错误：删除数据错误
     */
    ERR_DELETE_FAILED(50100),
    /**
     * 错误：修改数据错误
     */
    ERR_UPDATE_FAILED(50200),
    /**
     * 文件上载错误
     */
    ERR_FILE_UPLOAD(50300),
    /**
     * 错误：JWT已过期
     */
    ERR_JWT_EXPIRED(60000),
    /**
     * 错误：验证签名失败
     */
    ERR_JWT_SIGNATURE(60100),
    /**
     * 错误：JWT格式错误
     */
    ERR_JWT_MALFORMED(60200),
    /**
     * 错误：JWT已经登出
     */
    ERR_JWT_LOGOUT(60300),
    /**
     * 错误：未知错误
     */
    ERR_UNKNOWN(99999);

    private Integer value;

    ServiceCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}