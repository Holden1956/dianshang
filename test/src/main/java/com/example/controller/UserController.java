package com.example.controller;

import com.example.common.JWTUtils;
import com.example.common.R;
import com.example.pojo.dto.PwdUpdateDTO;
import com.example.pojo.dto.UserInfoDTO;
import com.example.pojo.dto.UserLoginDTO;
import com.example.pojo.dto.UserRegisterDTO;
import com.example.pojo.vo.UserInfoVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:xuanhe
 * @date:2024/2/27 11:03
 * @modyified By:
 */
@RestController
@CrossOrigin(origins ={"http://localhost"},allowCredentials="true")
@RequestMapping("user")// 一级
@Slf4j
@Api(tags = "用户模块")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("reg")// 二级locathost:port/user/reg
    @ApiOperation("用户注册接口")
    public R<Void> register(@RequestBody @Validated UserRegisterDTO userRegisterDTO) {
        log.debug("用户注册信息：{}", userRegisterDTO);
        // todo 处理请求
        userService.register(userRegisterDTO);
        return R.ok();
    }

    @PostMapping("log")
    //在线文档的注解
    @ApiOperation("用户登录接口")
    public R<UserLoginVO> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        log.debug("用户登录信息:{}", userLoginDTO);
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        System.out.println(userLoginVO.getToken());
        return R.ok(userLoginVO);
    }

    @PutMapping("update_Pwd")
    @ApiOperation("用户密码修改接口")
    public R<Void> update(@RequestBody @Validated PwdUpdateDTO pwdUpdateDTO, HttpServletRequest request) {
        // 获取用户的身份：请求头中是含有token的
        String token = request.getHeader("Authorization");
        // 解析token：知道用户的身份
        UserLoginVO loinUser = JWTUtils.parseToken(token);
        // 知道了是谁在修改密码
        userService.updatePwd(pwdUpdateDTO, loinUser);
        return R.ok();
    }

    @GetMapping("info")
    @ApiOperation("用户个人资料查询接口")
    public R<UserInfoVO> getUserInfo(HttpServletRequest request){
        //获取用户信息
        String token=request.getHeader("Authorization");
        UserLoginVO userLoginVO=JWTUtils.parseToken(token);
        UserInfoVO userInfoVO=userService.getUser(userLoginVO);
        return R.ok(userInfoVO);
    }

    @PutMapping("update_Info")
    @ApiOperation("用户个人资料修改接口")
    public R<Void> updateInfo(@RequestBody @Validated UserInfoDTO userInfoDTO, HttpServletRequest request) {
        // 获取用户的身份：请求头中是含有token的
        String token = request.getHeader("Authorization");
        // 解析token：知道用户的身份
        UserLoginVO loinUser = JWTUtils.parseToken(token);
        // 知道了当前是谁在修改密码
        userService.updateUserInfo(userInfoDTO, loinUser);
        return R.ok();
    }

    @PostMapping("Avatar_upload")
    @ApiOperation("头像上传接口")
    public R<Void> uploadAvatar(MultipartFile avatar,HttpServletRequest request){
        String token=request.getHeader("Authorization");
        UserLoginVO loginVO=JWTUtils.parseToken(token);

        userService.uploadAva(avatar,loginVO);
        return R.ok();
    }

    @PostMapping("logout")
    @ApiOperation("登出接口")
    public R<Void> logout(){
        // 后台清空token
        //前端处理：只要用户退出，前端就会让用户页面跳转到登录界面，并且清空用户的本地存储（清除token）
        //后端处理：在企业实践中，会将token存储在一个缓存介质（redis，mcache等），只要用户退出，就会在缓冲中清空token。
        return R.ok();
    }

}
