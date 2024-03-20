package com.example.service;

import com.example.pojo.dto.CartAddDTO;
import com.example.pojo.dto.CartComputePriceDTO;
import com.example.pojo.dto.CartIdDTO;
import com.example.pojo.vo.*;

import java.util.List;

public interface CartService {

    List<CartVO> getCartList(UserLoginVO loinUser);

    Integer computePrice(CartComputePriceDTO[] cartComputePriceDTOList, UserLoginVO userLoginVO);

    CartSettlementVO settlementCart(CartComputePriceDTO[] cartComputePriceDTOS, UserLoginVO userLoginVO);

    CartNumVO getCartUpList(UserLoginVO loinUser, CartIdDTO cartIdDTO);

    CartOnlinePayVO getOnlinePayInfo(UserLoginVO loinUser, Integer address_id);

    void delete(Integer product_id, UserLoginVO userLoginVO);

    void delete1(List<Integer> product_id, UserLoginVO userLoginVO);

    void addIntoCart(CartAddDTO cartAddDTO, UserLoginVO userLoginVO);

    CartOnlinePayVO getPayOKInfo(UserLoginVO loinUser);
}
