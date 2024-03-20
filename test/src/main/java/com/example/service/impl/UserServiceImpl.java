package com.example.service.impl;

import com.example.common.*;
import com.example.mapper.usermapper;
import com.example.pojo.dto.PwdUpdateDTO;
import com.example.pojo.dto.UserInfoDTO;
import com.example.pojo.dto.UserLoginDTO;
import com.example.pojo.dto.UserRegisterDTO;
import com.example.pojo.commonPojo.User;
import com.example.pojo.vo.UserInfoVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ProjectName: test
 * @Titile: UserServiceImpl
 * @Author: Lucky
 * @Description: 用户业务实现类
 */
@Service// 服务层
@Transactional // 开启事务
@Slf4j// 日志
public class UserServiceImpl implements UserService {// 实现接口方法
    @Autowired// 注入
    private usermapper userMapper;

    /**
     * 注册的业务
     * @param userRegisterDTO 前端传回来的新用户数据
     */
    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        // todo 判断 密码和重复密码是否一致
        if (!(userRegisterDTO.getPassword().equals(userRegisterDTO.getRepassword()))) {
            throw new ServiceException(ServiceCode.ERR_BAD_REPWD, "确认密码错误.");
        }
        // todo 业务中是否有要求：用户名要求不一致
        User existsUser = userMapper.getUserByName(userRegisterDTO.getUsername());
        if (existsUser != null) {
            throw new ServiceException(ServiceCode.ERR_EXISTS, "该用户已经存在,不能重复注册.");
        }

        User user = new User();
        user.setId(userRegisterDTO.getId());
        // todo 密码加密
        String salt = UUID.randomUUID().toString().replace("-", "");
        user.setSalt(salt);
        String password = MD5Utils.enctype(userRegisterDTO.getPassword(), salt, Constants.HASH_TIME);
        user.setPassword(password);

        // todo 是否被删除
        user.setIsDelete(Constants.IS_NOT_DELETE);
        user.setUsername(userRegisterDTO.getUsername());
        int result = userMapper.insert(user);
        if (result != 1) {
            // todo 抛出新增用户失败的异常
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "注册用户失败");
        }

    }

    /**
     * 登录业务逻辑
     * @param userLoginDTO 谁要登陆，前端传回来的登录用户信息
     * @return 状态码
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        // 根据用户名去查询用户
        User user = userMapper.getUserByName(userLoginDTO.getUsername());
        if (user == null) {
            // 返回的是前端开发人员
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "用户不存在");
        }
        // 对密码进行加密后才判断
        String password = MD5Utils.enctype(userLoginDTO.getPassword(), user.getSalt(), Constants.HASH_TIME);
        if (!password.equals(user.getPassword())) {
            throw new ServiceException(ServiceCode.ERR_PWD, "密码错误");
        }
        // 从user中取出需要的数据：id,username,封装
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setId(user.getId());
        userLoginVO.setUsername(user.getUsername());
        // 生成token
        String token = JWTUtils.generateToken(userLoginVO);
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    /**
     * 登录业务逻辑
     * @param  userLoginVO 谁要修改，前端传回来的当前登录用户信息
     * @param  pwdUpdateDTO 修改成多少，前端传回来的当前登录用户信息填写的原密码，新密码，确认新密码
     */
    @Override// 密码修改
    public void updatePwd(PwdUpdateDTO pwdUpdateDTO, UserLoginVO userLoginVO) {
        // 根据用户名去查询用户
        User user = userMapper.getUserByName(userLoginVO.getUsername());// 根据当前登录用户查到的User
        if (user == null) {
            // 返回的是前端开发人员
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "用户不存在");
        }
        // 对oldPwd密码进行加密后才判断
        String password = MD5Utils.enctype(pwdUpdateDTO.getOldPwd(), user.getSalt(), Constants.HASH_TIME);
        if (!password.equals(user.getPassword())) {
            throw new ServiceException(ServiceCode.ERR_PWD, "密码错误");
        }
        // 判断newPwd与reNewPwd
        if (!pwdUpdateDTO.getNewPwd().equals(pwdUpdateDTO.getReNewPwd())) {
            throw new ServiceException(ServiceCode.ERR_BAD_REPWD, "确认密码错误");
        }
        String salt = UUID.randomUUID().toString().replace("-", "");
        // 加密
        String newPassword =
                MD5Utils.enctype(pwdUpdateDTO.getNewPwd(), salt, Constants.HASH_TIME);
        // 构造一个用户对象
        User updateUser = new User();
        updateUser.setId(userLoginVO.getId());
        updateUser.setPassword(newPassword);
        updateUser.setSalt(salt);
        updateUser.setModifiedUser(userLoginVO.getUsername());
        int result = userMapper.updatePwd(updateUser);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "修改密码失败");
        }
    }

    @Override// 获取当前登录用户信息
    public UserInfoVO getUser(UserLoginVO userLoginVO) {// 根据当前登录用户获取个人资料
        User user = userMapper.getUserByName(userLoginVO.getUsername());
        if (user == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "用户不存在");
        }
        // 封装查询的用户信息

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setPhone(user.getPhone());
        userInfoVO.setEmail(user.getEmail());
        userInfoVO.setGender(user.getGender());
        return userInfoVO;
    }

    @Override// 修改个人资料
    public void updateUserInfo(UserInfoDTO userInfoDTO, UserLoginVO userLoginVO) {
        // 根据用户名去查询用户
        User user = userMapper.getUserByName(userLoginVO.getUsername());// 根据当前登录用户查到的User
        if (user == null) {
            // 返回的是前端开发人员
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "用户不存在");
        }
        if (!userInfoDTO.getUsername().equals(userLoginVO.getUsername())) {// 自己才能修改自己的
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "该用户禁止修改");
        }

        // 构造一个用户对象
        User updateUser = new User();
        updateUser.setId(userLoginVO.getId());// id不能改

        updateUser.setUsername(userInfoDTO.getUsername());
        updateUser.setPhone(userInfoDTO.getPhone());
        updateUser.setEmail(userInfoDTO.getEmail());
        updateUser.setGender(userInfoDTO.getGender());

        updateUser.setModifiedUser(userLoginVO.getUsername());// 修改人是当前登录用户

        int result = userMapper.updateUserINfo(updateUser);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "修改密码失败");
        }
    }

    @Override// 上传或修改用户头像
    public void uploadAva(MultipartFile avatar, UserLoginVO userLoginVO) {
        // 明确图片保存在哪里？？？(看笔记，视频)
        // 为了演示案例的方便，我们把图片保存在源码中: /resources/static/images2
        String savePath = System.getProperty("user.dir") + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "resources" + File.separator +
                "static" + File.separator +
                "images2";
        log.debug("保存的路径:{}", savePath);

        // 获取文件的名称（全名称，包括后缀）
        String filename = avatar.getOriginalFilename();

        // 判断文件的类型是否符合要求
        String type = avatar.getContentType(); // 获取文件mime类型
        type = type.substring(type.indexOf("/") + 1);// png,jpeg,TIFF,gif,jpg,raw,svg,
        System.out.println("文件类型" + type);
        if (!Constants.FILE_TYPE.contains(type)) {
            throw new ServiceException(ServiceCode.ERR_UNKNOWN, "格式错误");
        }
        System.out.println(avatar.getSize());

        // 判断文件的大小是否符合要求
        if (avatar.getSize() > Constants.FILE_MAX_SIZE) {
            throw new ServiceException(ServiceCode.ERR_FILE_SIZE_TOO_BIG, "文件尺寸过大");
        }

        // 生成一个随机的名称+拼接后缀
        // abc.png =>随机名称.png
        filename = UUID.randomUUID().toString().replace("-", "") +
                filename.substring(filename.lastIndexOf("."));
        log.debug("文件的新名：{}", filename);

        // 上传
        File saveFile = new File(savePath, filename);
        try {
            String oldAvatar = userMapper.getUserById(userLoginVO.getId()).getAvatar();// 获取当前登录用户的原始Avatar
            // todo 删除旧头像，
            avatar.transferTo(saveFile);
        } catch (IOException e) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "文件上传失败");
        }

        // 将头像的路径持久化到数据库去
        User user = new User();

        user.setId(userLoginVO.getId());
        user.setUsername(userLoginVO.getUsername());
        user.setAvatar(filename);
        user.setModifiedUser(userLoginVO.getUsername());

        int result = userMapper.saveAvatar(user);
        if (result != 1) { // 为了模拟数据事务回滚
            // 删除文件
            if (saveFile.exists()) {
                saveFile.delete();
            }
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "头像路径保存数据库失败");
        }
    }

}
