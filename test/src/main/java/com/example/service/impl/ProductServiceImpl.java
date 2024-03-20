package com.example.service.impl;

import com.example.common.PageVO;
import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import com.example.mapper.favoritemapper;
import com.example.mapper.productmapper;
import com.example.mapper.productCategoryMapper;
import com.example.pojo.commonPojo.Favorite;
import com.example.pojo.commonPojo.Product;
import com.example.pojo.commonPojo.ProductCategory;
import com.example.pojo.dto.ProductCategoryPageDTO;
import com.example.pojo.vo.ProductDetailVO;
import com.example.pojo.vo.ProductVO;
import com.example.pojo.vo.ProductWithFavoriteVO;
import com.example.pojo.vo.UserLoginVO;
import com.example.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/3/4 10:49
 * @Author qixuan he
 * @Description:
 */
@Service// 业务层
@Transactional // 开启事务
@Slf4j// 日志
public class ProductServiceImpl implements ProductService {
    @Autowired
    private productmapper productmapper;
    @Autowired
    private productCategoryMapper productCategoryMapper;
    @Autowired
    private favoritemapper favoritemapper;

    @Override
    public List<ProductVO> getNewList() {// 新到好货
        List<Product> productList = productmapper.getNewList();
        return toCon(productList);
    }

    @Override
    public List<ProductVO> getHotList() {// 热销商品
        List<Product> productList = productmapper.getHotList();
        return toCon(productList);
    }

    @Override
    public PageVO<List<ProductWithFavoriteVO>> getListByCategoryId(ProductCategoryPageDTO productCategoryPageDTO, UserLoginVO userLoginVO) {
        // 判断该商品分类id是否存在，去分类表查询是否该商品有分类
        // 一是分类表数据量肯定要小些可以减少product查询时间
        // 判断该商品分类id是否存在
        ProductCategory productCategory = productCategoryMapper.getById(productCategoryPageDTO.getCategory_id());
        if (productCategory == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该商品分类不存在");
        }

        // List<Product> productList = productmapper.getListByCategoryId(productCategoryPageDTO.getCategory_id());
        // SQL语句进行分页
        List<Product> productList = productmapper.getProductByPage(productCategoryPageDTO);
        if (productList == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该商品分类没有商品");
        }

        // 封装成列表vo（分页中的data）
        List<ProductWithFavoriteVO> productListVOList = new ArrayList<>();

        productList.forEach(product -> {
            ProductWithFavoriteVO productListVO = new ProductWithFavoriteVO();
            productListVO.setId(product.getId());
            productListVO.setTitle(product.getTitle());
            productListVO.setImage(product.getImage());
            productListVO.setPrice(product.getPrice());
            // 判断这系列的商品是否被收藏
            if (userLoginVO != null) {
                // 登录了
                Favorite favorite = favoritemapper.getFavorite(product.getId(), userLoginVO.getId());
                if (favorite != null) {
                    // 设置收藏
                    productListVO.setIs_Favorite(1);
                }
            }
            productListVOList.add(productListVO);
        });
        int count = productmapper.count(productCategory.getId());
        PageVO<List<ProductWithFavoriteVO>> pageVO = new PageVO<>(productCategoryPageDTO.getPageIndex(),
                productCategoryPageDTO.getPageSize(), count, productListVOList);
        return pageVO;
    }

    // @Override// 分页，pageHelper实现，不建议
    // public PageInfo<Product> getListByCategoryId(ProductCategoryPageDTO categoryPageDTO) {
    //     // 判断该商品分类id是否存在
    //     ProductCategory productCategory = productCategoryMapper.getById(categoryPageDTO.getCategory_id());
    //     if (productCategory == null) {
    //         throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该商品分类不存在");
    //     }
    //     // 开启分页：设置分页参数:页码值；每页显示的数量
    //     PageHelper.startPage(categoryPageDTO.getPageIndex(), categoryPageDTO.getPageSize());
    //     // 调用持久层方法
    //     List<Product> productList = productmapper.getListByCategoryId(categoryPageDTO.getCategory_id());
    //     if (productList == null) {
    //         throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该商品分类没有商品");
    //     }
    //     // 封装成vo ：分页会失效，分页插件不会对sql语句进行拦截处理的
    //     // List<ProductVO> productVOList = toConvert(productList);
    //     // 分页封装
    //     PageInfo<Product> pageInfo = new PageInfo<>(productList);
    //     return pageInfo;
    // }

    @Override// 分页，自定义方法，技术更难但是流行
    public PageVO<List<ProductVO>> getListByCategoryId3(ProductCategoryPageDTO categoryPageDTO) {
        // 判断该商品分类id是否存在
        ProductCategory productCategory = productCategoryMapper.getById(categoryPageDTO.getCategory_id());
        if (productCategory == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该商品分类不存在");
        }
        // 调用持久层方法
        List<Product> productList = productmapper.getProductByPage(categoryPageDTO);

        if (productList == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该商品分类没有商品");
        }
        List<ProductVO> productVOList = toCon(productList);
        int count = productmapper.count(categoryPageDTO.getCategory_id());
        PageVO<List<ProductVO>> pageVO = new PageVO<>(categoryPageDTO.getPageIndex(), categoryPageDTO.getPageSize(), count, productVOList);
        return pageVO;
    }

    @Override// 查询商品详情业务
    public ProductDetailVO getDetailById(Integer id, UserLoginVO userLoginVO) {

        // 判断这个id是否合法
        Product product = productmapper.getProductByID(id);
        if (product == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "没有此商品");
        }
        // 有商品
        Favorite favorite = null;
        if (userLoginVO != null) {
            // 登录过，是否被当前用户收藏了
            Integer userId = userLoginVO.getId();
            // 根据商品编号以及用户编号去查询收藏表，检查该商品是否被收藏
            favorite = favoritemapper.getFavorite(id, userId);
        }
        // 封装vo
        ProductDetailVO detailVO = new ProductDetailVO();
        detailVO.setId(product.getId());
        detailVO.setTitle(product.getTitle());
        detailVO.setImage(product.getImage());
        detailVO.setPrice(product.getPrice());
        detailVO.setSell_point(product.getSell_point());
        if (favorite != null) {
            // 被收藏了
            detailVO.setIs_favorite(1);
        }
        return detailVO;
    }

    private List<ProductVO> toCon(List<Product> productList) {// 封装实体类到VO
        List<ProductVO> productVOList = new ArrayList<>();

        for (Product t :
                productList) {
            ProductVO productVO = new ProductVO();

            productVO.setId(t.getId());
            productVO.setTitle(t.getTitle());
            productVO.setImage(t.getImage());
            productVO.setPrice(t.getPrice());

            productVOList.add(productVO);
        }
        return productVOList;
    }
}

