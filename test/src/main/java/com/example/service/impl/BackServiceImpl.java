package com.example.service.impl;

import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import com.example.mapper.optmapper;
import com.example.mapper.ordermapper;
import com.example.mapper.productmapper;
import com.example.pojo.commonPojo.Opt;
import com.example.pojo.commonPojo.Order;
import com.example.pojo.commonPojo.Product;
import com.example.pojo.vo.OrderVO;
import com.example.pojo.vo.ProductViewVO;
import com.example.service.BackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/3/13 22:46
 * @Author qixuan he
 * @Description: 后台系统业务层
 */
@Service
@Transactional// 事务注解
@Slf4j// 日志注解
public class BackServiceImpl implements BackService {

    @Autowired
    private ordermapper ordermapper;

    @Autowired
    private optmapper optmapper;

    @Autowired
    private productmapper productmapper;

    @Override
    public List<OrderVO> getOrderList() {
        List<OrderVO> orderVOList = new ArrayList<>();
        List<Order> orderList = ordermapper.getAllOrder();
        for (Order t :
                orderList) {
            OrderVO orderVO = new OrderVO();
            orderVO.setId(t.getId());
            orderVO.setStatus(t.getStatus());
            orderVO.setTotal_price(t.getTotal_price());
            orderVO.setRecv_name(t.getRecv_name());
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public List<Opt> getUserDataByUserId(Integer user_id) {
        List<Opt> opts = optmapper.getUserOptByUserID(user_id);
        return opts;
    }

    @Override
    public List<ProductViewVO> getProductView() {
        List<ProductViewVO> productViewVOS = new ArrayList<>();
        List<Product> productList = productmapper.getAllProduct();
        for (Product t :
                productList) {
            ProductViewVO productViewVO = new ProductViewVO();
            productViewVO.setId(t.getId());
            productViewVO.setNum(t.getNum());
            productViewVO.setPrice(t.getPrice());
            productViewVO.setStatus(t.getStatus());
            productViewVO.setPriority(t.getPriority());
            productViewVO.setSell_point(t.getSell_point());
            productViewVO.setTitle(t.getTitle());
            productViewVO.setImage(t.getImage());
            productViewVO.setCategory_id(t.getCategory_id());
            productViewVO.setCount(optmapper.getViewCountByProductId(t.getId()));

            productViewVOS.add(productViewVO);
        }
        return productViewVOS;
    }

    @Override
    public List<ProductViewVO> updateProductStatusByPID(Integer id,Integer status) {
        Integer result = productmapper.updateStatus(id,status);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "更新失败");
        }
        List<ProductViewVO> productViewVOS = new ArrayList<>();
        List<Product> productList = productmapper.getAllProduct();
        for (Product t :
                productList) {
            ProductViewVO productViewVO = new ProductViewVO();
            productViewVO.setId(t.getId());
            productViewVO.setNum(t.getNum());
            productViewVO.setPrice(t.getPrice());
            productViewVO.setStatus(t.getStatus());
            productViewVO.setPriority(t.getPriority());
            productViewVO.setSell_point(t.getSell_point());
            productViewVO.setTitle(t.getTitle());
            productViewVO.setImage(t.getImage());
            productViewVO.setCategory_id(t.getCategory_id());
            productViewVO.setCount(optmapper.getViewCountByProductId(t.getId()));

            productViewVOS.add(productViewVO);
        }
        return productViewVOS;
    }

    @Override
    public List<Opt> getAllUserDataByUserId() {
        List<Opt> opts=optmapper.getAllUserAction();
        return opts;
    }

    @Override
    public List<ProductViewVO> updateProductpriorityByPID(Integer id, Integer priority) {
        Integer result = productmapper.updatePriority(id,priority);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "更新失败");
        }
        List<ProductViewVO> productViewVOS = new ArrayList<>();
        List<Product> productList = productmapper.getAllProduct();
        for (Product t :
                productList) {
            ProductViewVO productViewVO = new ProductViewVO();
            productViewVO.setId(t.getId());
            productViewVO.setNum(t.getNum());
            productViewVO.setPrice(t.getPrice());
            productViewVO.setStatus(t.getStatus());
            productViewVO.setPriority(t.getPriority());
            productViewVO.setSell_point(t.getSell_point());
            productViewVO.setTitle(t.getTitle());
            productViewVO.setImage(t.getImage());
            productViewVO.setCategory_id(t.getCategory_id());
            productViewVO.setCount(optmapper.getViewCountByProductId(t.getId()));

            productViewVOS.add(productViewVO);
        }
        return productViewVOS;
    }

    @Override
    public List<ProductViewVO> delProduct(Integer productId) {
        Integer result = productmapper.delProductBuId(productId);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "更新失败");
        }
        List<ProductViewVO> productViewVOS = new ArrayList<>();
        List<Product> productList = productmapper.getAllProduct();
        for (Product t :
                productList) {
            ProductViewVO productViewVO = new ProductViewVO();
            productViewVO.setId(t.getId());
            productViewVO.setNum(t.getNum());
            productViewVO.setPrice(t.getPrice());
            productViewVO.setStatus(t.getStatus());
            productViewVO.setPriority(t.getPriority());
            productViewVO.setSell_point(t.getSell_point());
            productViewVO.setTitle(t.getTitle());
            productViewVO.setImage(t.getImage());
            productViewVO.setCategory_id(t.getCategory_id());
            productViewVO.setCount(optmapper.getViewCountByProductId(t.getId()));

            productViewVOS.add(productViewVO);
        }
        return productViewVOS;
    }
}
