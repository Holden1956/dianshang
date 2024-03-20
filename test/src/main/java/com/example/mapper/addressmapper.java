package com.example.mapper;

import com.example.pojo.commonPojo.Address;
import com.example.pojo.commonPojo.District;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// ps:当mapper接口方法中的形参有多个时需要为每个参数加@Param注解
// 原因是mybatis映射形参默认形参名为arg0,arg1...
// arg0,arg1...不能与形参顺序对应
// 当然，0-1个形参不用加注解
@Repository
@Mapper
public interface addressmapper {
    /**
     * 获取地址
     **/
    List<Address> getAddressList(Integer user_id);

    Address getById(Integer id);

    int delete(Integer id);

    int setAllNotDefault(Integer id);

    int setDefault(Address address);

    List<District> getDistrictList(Integer parent);

    int count(Integer id);

    int save(Address address);

    String getDistrictNameByCode(Integer code);

    int update(Address address);

    Address getDefaultByUserId(Integer user_id);
}
