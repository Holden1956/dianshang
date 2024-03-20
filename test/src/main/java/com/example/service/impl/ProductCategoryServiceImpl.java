package com.example.service.impl;


import com.example.pojo.commonPojo.ProductCategory;
import com.example.pojo.vo.ProductCategoryVO;
import com.example.service.ProductCategoryService;
import com.example.mapper.productCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: cqjtu-store
 * @Titile: ProductCategoryServiceImpl
 * @Author: Lucky
 * @Description: 商品分类的业务类
 */
@Service
@Transactional
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private productCategoryMapper productCategoryMapper;
    @Override
    public List<ProductCategoryVO> getList() {
        List<ProductCategory> productCategoryList = productCategoryMapper.getList();
        //创建vo列表对象
        List<ProductCategoryVO> productCategoryVOList = new ArrayList<>();
        productCategoryList.forEach(productCategory -> {
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            productCategoryVO.setId(productCategory.getId());
            productCategoryVO.setName(productCategory.getName());
            productCategoryVO.setParent_id(productCategory.getParent_id());
            productCategoryVOList.add(productCategoryVO);
        });
        return productCategoryVOList;
    }
}
