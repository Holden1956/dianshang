package com.example.common;

import lombok.Getter;

/**
 * @Titile: ServiceException
 * @Author: Lucky
 * @Description: 业务异常
 */

public class ServiceException extends RuntimeException {
    @Getter
    private ServiceCode serviceCode;

    public ServiceException( ServiceCode serviceCode,String message) {
        super(message);
        this.serviceCode = serviceCode;
    }
}
