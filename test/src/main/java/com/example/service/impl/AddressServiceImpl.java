package com.example.service.impl;

import com.example.common.Constants;
import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import com.example.mapper.addressmapper;
import com.example.pojo.dto.AddressUpdateDTO;
import com.example.pojo.vo.AddressUpdateVO;
import com.example.pojo.commonPojo.Address;
import com.example.pojo.commonPojo.District;
import com.example.pojo.dto.AddressInsertDTO;
import com.example.pojo.vo.AddressVO;
import com.example.pojo.vo.DistrictVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author:xuanhe
 * @date:2024/2/29 14:35
 * @modyified By:
 */
@Service
@Transactional//事务注解
@Slf4j//日志注解
public class AddressServiceImpl implements AddressService {
    @Autowired// 注入
    private addressmapper addressmapper;

    /**
     * 查询所有地址的业务
     *
     * @param userLoginVO 查询人
     */
    @Override
    public List<AddressVO> getAddressList(UserLoginVO userLoginVO) {// 业务层获取收获地址
        List<Address> addressList = addressmapper.getAddressList(userLoginVO.getId());
        if (addressList.isEmpty()) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该用户没有地址");
        }
        // 封装：将实体类封装的数据，转成VO供控制层调用
        List<AddressVO> addressVOList = new ArrayList<>();
        addressList.forEach(address -> {
            AddressVO addressVO = new AddressVO();
            addressVO.setId(address.getId());
            addressVO.setName(address.getName());
            addressVO.setPhone(address.getPhone());
            addressVO.setTag(address.getTag());
            addressVO.setIs_default(address.getIs_default());
            // 详细地址：省份+城市+区县+小区编号
            addressVO.setAddress(address.getProvince_name() +
                    address.getCity_name() + address.getArea_name() + address.getAddress());
            addressVOList.add(addressVO);
        });
        return addressVOList;
    }

    /**
     * 删除地址的业务
     *
     * @param id          地址编号
     * @param userLoginVO 删除人
     */
    @Override
    public void delete(Integer id, UserLoginVO userLoginVO) {
        // 判断这个地址是否存在
        Address address = addressmapper.getById(id);
        if (address == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该地址编号不存在");
        }
        // 判断这个地址是否是属于当前用户
        if (address.getUser_id() != userLoginVO.getId()) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "你没有权限删除该地址");
        }
        // 删除
        int result = addressmapper.delete(id);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "删除失败");
        }
    }

    /**
     * 设置默认收获地址1：默认，0：不默认
     *
     * @param id          地址编号
     * @param userLoginVO 修改人
     */
    @Override
    public void setDefault(Integer id, UserLoginVO userLoginVO) {// 修改默认地址

        if (id == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "地址id不能为空");
        }
        // 判断这个地址是否存在
        Address address = addressmapper.getById(id);// 查询当前选中行的id
        if (address == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该地址编号不存在");
        }
        // 判断改地址是否属于该用户
        if (address.getUser_id() != userLoginVO.getId()) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "你没有权限修改该地址");
        }

        int result = addressmapper.setAllNotDefault(userLoginVO.getId());// 全部设置成非默认
        if (result == 0) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "更新失败");
        }

        address.setIs_default(Constants.DEFAULT_ADDRESS);
        address.setModified_user(userLoginVO.getUsername());
        // 设置当前地址为默认
        result = addressmapper.setDefault(address);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "设置默认地址失败");
        }
    }

    /**
     * 查询行政地址列表
     *
     * @param parent
     * @return
     */
    @Override
    public List<DistrictVO> getDistrictList(Integer parent) {
        if (parent == null) {
            // 查询的是省份,CN默认86
            parent = 86;
        }
        List<District> districtList = addressmapper.getDistrictList(parent);
        if (districtList.isEmpty()) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "没有数据");
        }
        List<DistrictVO> districtVOList = new ArrayList<>();
        // 封装
        districtList.forEach(district -> {
            DistrictVO districtVO = new DistrictVO();
            districtVO.setCode(district.getCode());
            districtVO.setName(district.getName());
            districtVOList.add(districtVO);
        });
        return districtVOList;
    }

    /**
     * 新增收货地址
     *
     * @param addressInsertDTO
     * @param userLoginVO
     */
    @Override
    public void insertAddress(AddressInsertDTO addressInsertDTO, UserLoginVO userLoginVO) {
        // 这里限制地址的条数，不能无限增加收货地址
        // so,先查询该用户地址数目
        int count = addressmapper.count(userLoginVO.getId());
        if (count >= Constants.INSERT_MAX_ADDRESS_COUNT) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "新增失败，地址条目数最多为" + Constants.INSERT_MAX_ADDRESS_COUNT);
        }
        // bug：怎么缺确定地址编号与名称对应
        // todo 根据地址编号去查询地址信息，判断地址对象中的地址名称跟传递过来的名称已否一致
        // todo 根据地址编号去查询地址信息
        // todo 判断这个地址的名称跟传递的名称是否一致
        String province_name = addressmapper.getDistrictNameByCode(addressInsertDTO.getProvince_code());
        String city_name = addressmapper.getDistrictNameByCode(addressInsertDTO.getCity_code());
        String area_name = addressmapper.getDistrictNameByCode(addressInsertDTO.getArea_code());
        // System.out.println(province_name+"::"+city_name+"::"+area_name);
        if (province_name == null) {
            throw new ServiceException(ServiceCode.ERR_EXISTS, "不存在该省级编码");
        }
        if (city_name == null) {
            throw new ServiceException(ServiceCode.ERR_EXISTS, "不存在该城市编码");
        }
        if (area_name == null) {
            throw new ServiceException(ServiceCode.ERR_EXISTS, "不存在该区域编码");
        }
        if (!province_name.equals(addressInsertDTO.getProvince_name())) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "省级名称不匹配");
        }
        if (!city_name.equals(addressInsertDTO.getCity_name())) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "城市名称不匹配");
        }
        if (!area_name.equals(addressInsertDTO.getArea_name())) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "区域名称不匹配");
        }
        // 封装地址
        Address address = new Address();
        address.setName(addressInsertDTO.getName());
        address.setProvince_code(addressInsertDTO.getProvince_code());
        address.setProvince_name(addressInsertDTO.getProvince_name());
        address.setCity_code(addressInsertDTO.getCity_code());
        address.setCity_name(addressInsertDTO.getCity_name());
        address.setArea_code(addressInsertDTO.getArea_code());
        address.setArea_name(addressInsertDTO.getArea_name());
        address.setAddress(addressInsertDTO.getAddress());
        address.setZip(addressInsertDTO.getZip());
        address.setPhone(addressInsertDTO.getPhone());
        address.setTel(addressInsertDTO.getTel());
        address.setTag(addressInsertDTO.getTag());
        // 添加人名称
        address.setCreated_user(userLoginVO.getUsername());
        // 地址所属人的编号
        address.setUser_id(userLoginVO.getId());
        int result = addressmapper.save(address);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "新增地址失败");
        }
    }

    @Override// 点击修改时还是要先查询一道，以便用户的修改
    public AddressUpdateVO getAddressById(Integer id, UserLoginVO userLoginVO) {
        // 根据编号去查询地址
        Address address = addressmapper.getById(id);

        if (address == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "地址不存在");
        }

        if (address.getUser_id() != userLoginVO.getId()) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "没有权限修改此地址");
        }
        // 封装VO返回
        // todo 对象的拷贝 ……
        AddressUpdateVO addressUpdateVO = new AddressUpdateVO();
        addressUpdateVO.setId(address.getId());
        addressUpdateVO.setName(address.getName());
        addressUpdateVO.setProvince_code(address.getProvince_code());
        addressUpdateVO.setProvince_name(address.getProvince_name());
        addressUpdateVO.setCity_code(address.getCity_code());
        addressUpdateVO.setCity_name(address.getCity_name());
        addressUpdateVO.setArea_code(address.getArea_code());
        addressUpdateVO.setArea_name(address.getArea_name());
        addressUpdateVO.setAddress(address.getAddress());
        addressUpdateVO.setZip(address.getZip());
        addressUpdateVO.setPhone(address.getPhone());
        addressUpdateVO.setTel(address.getTel());
        addressUpdateVO.setTag(address.getTag());
        return addressUpdateVO;
    }

    @Override// 修改当前收货地址
    public void updateAddress(AddressUpdateDTO addressUpdateDTO, UserLoginVO userLoginVO) {
        // 根据前端传来的数据获取地址编号
        Integer id = addressUpdateDTO.getId();
        if (id == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "修改的地址的编号不能为空");
        }
        // 根据id查询地址
        Address address = addressmapper.getById(id);
        if (address == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "修改的地址不存在");
        }
        // 判断这个地址是否属于当前用户
        if (address.getUser_id() != userLoginVO.getId()) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "没有权限修改地址");
        }
        // 为地址重新设置值
        address.setName(addressUpdateDTO.getName());

        // 根据地址编号去查询地址信息，判断地址对象中的地址名称跟传递过来的名称已否一致
        String province_name = addressmapper.getDistrictNameByCode(addressUpdateDTO.getProvince_code());
        String city_name = addressmapper.getDistrictNameByCode(addressUpdateDTO.getCity_code());
        String area_name = addressmapper.getDistrictNameByCode(addressUpdateDTO.getArea_code());
        // System.out.println(province_name+"::"+city_name+"::"+area_name);
        if (province_name == null) {
            throw new ServiceException(ServiceCode.ERR_EXISTS, "不存在该省级编码");
        }
        if (city_name == null) {
            throw new ServiceException(ServiceCode.ERR_EXISTS, "不存在该城市编码");
        }
        if (area_name == null) {
            throw new ServiceException(ServiceCode.ERR_EXISTS, "不存在该区域编码");
        }
        if (!province_name.equals(addressUpdateDTO.getProvince_name())) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "省级名称不匹配");
        }
        if (!city_name.equals(addressUpdateDTO.getCity_name())) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "城市名称不匹配");
        }
        if (!area_name.equals(addressUpdateDTO.getArea_name())) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "区域名称不匹配");
        }

        address.setProvince_code(addressUpdateDTO.getProvince_code());
        address.setProvince_name(addressUpdateDTO.getProvince_name());
        address.setCity_code(addressUpdateDTO.getCity_code());
        address.setCity_name(addressUpdateDTO.getCity_name());
        address.setArea_code(addressUpdateDTO.getArea_code());
        address.setArea_name(addressUpdateDTO.getArea_name());
        address.setAddress(addressUpdateDTO.getAddress());
        address.setZip(addressUpdateDTO.getZip());
        address.setPhone(addressUpdateDTO.getPhone());
        address.setTel(addressUpdateDTO.getTel());
        address.setTag(addressUpdateDTO.getTag());
        // 设置修改人
        address.setModified_user(userLoginVO.getUsername());
        int result = addressmapper.update(address);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_UPDATE_FAILED, "修改地址失败");

        }
    }
}
