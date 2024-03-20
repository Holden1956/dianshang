package com.example.config;

import com.example.interceptors.TokenHandlerInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:xuanhe
 * @date:2024/2/28 15:56
 * @modyified By:
 * 配置类（暂时注册了token拦截器）
 */
@SpringBootConfiguration
public class StoreWebMVCConfig implements WebMvcConfigurer {
    /**注册token拦截器**/
    /**
     * eg:双十一对根本抢不了该商品的用户进行拦截，从而减轻服务器负载
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenHandlerInterceptor())
                .addPathPatterns("/user/**", "/api/**")// 拦截的url
                .excludePathPatterns("/user/log",
                        "/user/reg",
                        "/api/product/**",
                        "/api/back/**",
                        "/api/category/**"//放行商品所有页面，不登录也能看
                );// 放行的url
    }
}
