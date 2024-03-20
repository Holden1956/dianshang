package com.example.controller;

import com.example.common.JWTUtils;
import com.example.common.R;
import com.example.pojo.dto.AddressUpdateDTO;
import com.example.pojo.vo.AddressUpdateVO;
import com.example.pojo.dto.AddressInsertDTO;
import com.example.pojo.vo.AddressVO;
import com.example.pojo.vo.DistrictVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author:xuanhe
 * @date:2024/2/29 14:31
 * @modyified By:
 */
@RestController
@RequestMapping("api/address")// 一级
@Slf4j
@Api(tags = "地址模块")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("list")
    @ApiOperation("收获地址查询接口")
    public R<List<AddressVO>> getAddressList(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        // 解析token：知道用户的身份
        UserLoginVO loinUser = JWTUtils.parseToken(token);
        // 知道了是谁在修改地址
        return R.ok(addressService.getAddressList(loinUser));
    }

    /**
     * 删除地址信息
     * @param id
     * @param request
     * @return
     */
    @DeleteMapping("delete/{id}")
    @ApiOperation("地址删除接口")
    public R<Void> delete(@PathVariable Integer id,HttpServletRequest request){
        //获取用户的身份
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        addressService.delete(id,userLoginVO);
        return R.ok();
    }

    @PutMapping("setDefault/{id}")//匹配前端传来的地址编号id
    @ApiOperation("地址删除接口")
    public R<Void> setDefault(@PathVariable Integer id,HttpServletRequest request){
        //获取用户的身份
        String token = request.getHeader("Authorization");
        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        addressService.setDefault(id,userLoginVO);
        return R.ok();
    }

    /**
     * 查询行政地址，新增收获地址接口
     * @param parent 父级行政地址的编号
     * @return
     */
    @GetMapping("district") // /api/address/district?parent=1001
    @ApiOperation("获取行政地址接口")
    public R<List<DistrictVO>> getDistrictList(Integer parent) {
        List<DistrictVO> districtVOList = addressService.getDistrictList(parent);
        return R.ok(districtVOList);
    }

    @PostMapping("insert")
    @ApiOperation("新增地址接口")//只要前端传来数据，可以用@RequestBody获取数据
    public R<Void> insertAddress(@RequestBody @Validated AddressInsertDTO addressInsertDTO,HttpServletRequest request){
        log.debug("新增地址信息：{}", addressInsertDTO);
        String token=request.getHeader("Authorization");//新增谁的地址
        UserLoginVO userLoginVO=JWTUtils.parseToken(token);
        addressService.insertAddress(addressInsertDTO,userLoginVO);
        return R.ok();
    }

    @GetMapping("{id}")//这种就叫restful风格/api/address/1001 注意：1001->id
    @ApiOperation("根据地址id查询要修改的地址")
    public R<AddressUpdateVO> getAddressById(@PathVariable Integer id, HttpServletRequest request){
        log.debug("修改地址id：{}", id);
        String token=request.getHeader("Authorization");
        UserLoginVO userLoginVO=JWTUtils.parseToken(token);
        AddressUpdateVO addressUpdateVO=addressService.getAddressById(id,userLoginVO);
        return R.ok(addressUpdateVO);
    }

    @PutMapping("update")
    @ApiOperation("修改收货地址")
    public R<Void> updateAddress(@RequestBody @Validated AddressUpdateDTO addressUpdateDTO, HttpServletRequest request){
        log.debug("修改地址信息为：{}",addressUpdateDTO);
        String token=request.getHeader("Authorization");
        UserLoginVO userLoginVO= JWTUtils.parseToken(token);
        addressService.updateAddress(addressUpdateDTO,userLoginVO);
        return R.ok();
    }
}
