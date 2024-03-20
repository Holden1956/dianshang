package com.example.pojo.vo;

import com.example.pojo.commonPojo.Address;
import lombok.Data;

import java.util.List;

/**
 * @Date 2024/3/6 12:37
 * @Author qixuan he
 * @Description: 购物车结算VO
 */
@Data
public class CartSettlementVO extends OrderVO{
    //地址列表
    private List<Address> addressList;
}
