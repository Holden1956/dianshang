package com.example.interceptors;

import com.example.common.JWTUtils;
import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:xuanhe
 * @date:2024/2/28 16:08
 * @modyified By:
 */
@Slf4j
public class TokenHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws Exception {
        log.debug("开始拦截.....");
        // 获取用户的token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            // token为空
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "token不能为空");
        }
        // token不为空
        // 校验token的合法性
        JWTUtils.verifyToken(token);
        // token是ok的
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
