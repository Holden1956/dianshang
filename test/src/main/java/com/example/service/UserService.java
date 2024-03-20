package com.example.service;

import com.example.pojo.dto.PwdUpdateDTO;
import com.example.pojo.dto.UserInfoDTO;
import com.example.pojo.dto.UserLoginDTO;
import com.example.pojo.dto.UserRegisterDTO;
import com.example.pojo.vo.UserInfoVO;
import com.example.pojo.vo.UserLoginVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:xuanhe
 * @date:2024/2/27 11:58
 * @modyified By:
 */
public interface UserService {
    void register(UserRegisterDTO userRegisterDTO);

    UserLoginVO login(UserLoginDTO userLoginDTO);

    void updatePwd(PwdUpdateDTO pwdUpdateDTO,UserLoginVO userLoginVO);

    UserInfoVO getUser(UserLoginVO userLoginVO);

    void updateUserInfo(UserInfoDTO userInfoDTO, UserLoginVO loinUser);

    void uploadAva(MultipartFile avatar, UserLoginVO loginVO);
}
