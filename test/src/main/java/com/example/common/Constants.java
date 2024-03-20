package com.example.common;

import java.util.Arrays;
import java.util.List;

/**
 * @author:xuanhe
 * @date:2024/2/27 11:42
 * @modyified By:
 */
public class Constants {
    //未删除
    public static final int IS_NOT_DELETE=0;
    //删除
    public static final int IS_DELETE=1;
    /*hash次数*/
    public static final int HASH_TIME = 6;
    /*图像的类型*/
    public static final List<String> FILE_TYPE = Arrays.asList("png","gif","jpg","jpeg","raw","svg","pdf","tiff");
    /*图像的大小*/
    public static final int FILE_MAX_SIZE = 10*1024*1024;
    /*默认地址为1*/
    public static final int DEFAULT_ADDRESS = 1;
    /*默认地址为0*/
    public static final int NOT_DEFAULT_ADDRESS = 0;
    /*新增地址条数*/
    public static final int INSERT_MAX_ADDRESS_COUNT = 5;
    /*pv*/
    public static final String OPERATION_TYPE_PV = "pv";
    /*购物车*/
    public static final String OPERATION_TYPE_CART = "cart";
    /*购买*/
    public static final String OPERATION_TYPE_BUY = "buy";
    /*收藏*/
    public static final String OPERATION_TYPE_FAVORITE = "favorite";
}
