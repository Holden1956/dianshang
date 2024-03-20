package com.example.service;

import com.example.pojo.dto.AddressUpdateDTO;
import com.example.pojo.vo.AddressUpdateVO;
import com.example.pojo.dto.AddressInsertDTO;
import com.example.pojo.vo.AddressVO;
import com.example.pojo.vo.DistrictVO;
import com.example.pojo.vo.UserLoginVO;

import java.util.List;

public interface AddressService {
    List<AddressVO> getAddressList(UserLoginVO userLoginVO);

    void delete(Integer id, UserLoginVO userLoginVO);

    void setDefault(Integer id, UserLoginVO userLoginVO);

    void insertAddress(AddressInsertDTO addressInsertDTO, UserLoginVO userLoginVO);

    List<DistrictVO> getDistrictList(Integer parent);

    AddressUpdateVO getAddressById(Integer id, UserLoginVO userLoginVO);

    void updateAddress(AddressUpdateDTO addressUpdateDTO, UserLoginVO userLoginVO);
}
