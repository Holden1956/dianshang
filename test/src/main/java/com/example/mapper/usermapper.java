package com.example.mapper;

import com.example.pojo.commonPojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface usermapper {
    /**
     * 新增用户
     * @param user
     * @return
     */
    int insert(User user);

    User getUserByName(String username);

    User getUserById(Integer id);

    int updatePwd(User updateUser);

    int updateUserINfo(User updateUser);

    int saveAvatar(User user);
}
