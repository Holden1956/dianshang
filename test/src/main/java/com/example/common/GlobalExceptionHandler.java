package com.example.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Objects;
import java.util.Set;

/**
 *  全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {
    /**
     * 处理业务异常
     */
    @ExceptionHandler
    public R<Void> handleServiceException(ServiceException e){
        return R.fail(e);
    }

    /**
     *处理数据绑定异常的（传参出现的异常）
     */
    @ExceptionHandler
    public R<Void>handleBindException(BindException e){
        //获取错误信息
        String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        return R.fail(ServiceCode.ERR_BAD_REQUEST,message);
    }
    //数据校验的异常统一处理
    @ExceptionHandler
    public R<Void> handleConstraintViolationException(ConstraintViolationException e) {
        String message = null;
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            message = constraintViolation.getMessage();
        }
        return R.fail(ServiceCode.ERR_BAD_REQUEST, message);
    }

    // 注意：以下方法存在的意义主要在于：避免因为某个异常未被处理，导致服务器端响应500错误
    // 注意：e.printStackTrace()通常是禁止使用的，因为其输出方式是阻塞式的！
    //      以下方法中使用了此语句，是因为用于发现错误，并不断的补充处理对应的异常的方法
    //      随着开发进度的推进，执行到以下方法的概率会越来越低，
    //      出现由于此语句导致的问题的概率也会越来越低，
    //      甚至补充足够多的处理异常的方法后，根本就不会执行到以下方法了
    //      当项目上线后，可以将此语句删除
    @ExceptionHandler
    public R<Void> handleThrowable(Throwable e) {
        log.warn("程序运行过程中出现Throwable，将统一处理！");
        log.warn("异常类型：{}", e.getClass());
        log.warn("异常信息：{}", e.getMessage());
        String message = "服务器忙，请稍后再次尝试！（开发过程中，如果看到此提示，请检查控制台的信息，并补充处理异常的方法）";
        // String message = "服务器忙，请稍后再尝试！"; // 项目上线时应该使用此提示文本
        e.printStackTrace(); // 打印异常的跟踪信息，主要是为了在开发阶段更好的检查出现异常的原因
        return R.fail(ServiceCode.ERR_UNKNOWN, message);
    }
}
