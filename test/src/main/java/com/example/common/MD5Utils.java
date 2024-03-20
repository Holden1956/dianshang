package com.example.common;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @titile: MD5Utils
 * @author: Lucky
 * @description: 加密工具
 */
public class MD5Utils {
    /**
     * 加密
     * @param password
     * @param salt
     * @param times
     * @return
     */
    public static String enctype(String password,String salt,int times){//MD5加密方法之一
        password = password + salt;
        for (int i = 0; i < times; i++) {
            password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        }
        return password;
    }
}
