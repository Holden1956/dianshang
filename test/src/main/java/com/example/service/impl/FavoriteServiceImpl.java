package com.example.service.impl;

import com.example.common.PageVO;
import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import com.example.mapper.favoritemapper;
import com.example.mapper.productmapper;
import com.example.pojo.commonPojo.Favorite;
import com.example.pojo.commonPojo.Product;
import com.example.pojo.dto.FavoritePageDTO;
import com.example.pojo.vo.ProductVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.FavoriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/3/5 15:50
 * @Author qixuan he
 * @Description: 收藏业务
 */
@Service
@Transactional
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private favoritemapper favoritemapper;

    @Autowired
    private productmapper productmapper;

    @Override// 获取某用户的全部收藏
    public PageVO<List<ProductVO>> getFavoriteList(FavoritePageDTO favoritePageDTO, UserLoginVO userLoginVO) {
        // 填装用户id
        Integer user_id = userLoginVO.getId();
        favoritePageDTO.setUser_id(user_id);

        List<Product> productList = favoritemapper.getFavoriteProduct(favoritePageDTO);
        if (productList == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "不存在收藏");
        }

        // 封装
        List<ProductVO> productVOList = new ArrayList<>();
        productList.forEach(product -> {
            ProductVO productVO = new ProductVO();
            productVO.setId(product.getId());
            productVO.setTitle(product.getTitle());
            productVO.setPrice(product.getPrice());
            productVO.setImage(product.getImage());
            productVOList.add(productVO);
        });
        // 获取收藏商品的数量
        int count = favoritemapper.count(userLoginVO.getId());
        PageVO<List<ProductVO>> pageVO = new PageVO<>(favoritePageDTO.getPageIndex(), favoritePageDTO.getPageSize(), count, productVOList);
        return pageVO;
    }

    @Override//添加收藏接口
    public void addFavorite(Integer product_id, UserLoginVO userLoginVO) {
        //判断商品编号是否合法
        if(product_id ==null){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"商品编号参数不能为空");
        }
        Product product = productmapper.getProductByID(product_id);
        if(product == null){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"商品不存在");
        }
        //执行到这里说明商品是存在的
        Favorite favorite = favoritemapper.getFavorite(product_id, userLoginVO.getId());
        if(favorite !=null){
            //已经收藏
            throw new ServiceException(ServiceCode.ERR_EXISTS,"该商品已经被收藏");
        }
        favorite = new Favorite();
        favorite.setUser_id(userLoginVO.getId());
        favorite.setProduct_id(product_id);
        favorite.setCreated_user(userLoginVO.getUsername());
        //添加收藏
        int result =  favoritemapper.insert(favorite);
        if(result != 1){
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED,"收藏失败");
        }
    }

    @Override//删除收藏接口
    public void cancelFavorite(Integer product_id, UserLoginVO userLoginVO) {
        //判断商品编号是否合法
        if(product_id ==null){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST,"商品编号参数不能为空");
        }
        int result = favoritemapper.delete(product_id, userLoginVO.getId());
        if(result == 0){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"收藏的商品不存在");
        }
    }
}
