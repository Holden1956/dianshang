package com.example.service.impl;

import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import com.example.mapper.*;
import com.example.pojo.commonPojo.*;
import com.example.pojo.dto.CartAddDTO;
import com.example.pojo.dto.CartComputePriceDTO;
import com.example.pojo.dto.CartIdDTO;
import com.example.pojo.vo.*;
import com.example.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/3/3 15:01
 * @Author qixuan he
 * @Description: 购物车业务层
 */
@Service
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private cartmapper cartmapper;
    @Autowired
    private productmapper productmapper;
    @Autowired
    private addressmapper addressmapper;
    @Autowired
    private usermapper usermapper;
    @Autowired
    private ordermapper ordermapper;

    /**
     * 根据当前登录用户查询他的购物车
     */
    @Override
    public List<CartVO> getCartList(UserLoginVO userLoginVO) {
        if (userLoginVO == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "未登录，没有购物车");
        }
        List<Cart> cartList = cartmapper.getCartListByUserId(userLoginVO.getId());
        if (cartList.isEmpty()) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该用户购物车为空");
        }
        // 封装：将实体类封装的数据，转成VO供控制层调用
        List<CartVO> cartVOList = new ArrayList<>();
        cartList.forEach(cart -> {
            CartVO cartVO = new CartVO();
            Product product = productmapper.getProductByID(cart.getProduct_id());
            cartVO.setId(cart.getId());
            cartVO.setProduct_id(cart.getProduct_id());
            cartVO.setProduct_name(product.getTitle());
            cartVO.setProduct_image(product.getImage()+"1.jpg");
            cartVO.setProduct_price(product.getPrice());
            cartVO.setProduct_num(cart.getNum());
            cartVO.setProduct_total(cart.getNum() * product.getPrice());

            cartVOList.add(cartVO);
        });
        return cartVOList;
    }

    @Override// 根据所选商品，计算总价
    public Integer computePrice(CartComputePriceDTO[] cartComputePriceDTOList, UserLoginVO userLoginVO) {
        if (cartComputePriceDTOList.length == 0) {// 如果没有选择任何商品，总价初始值为0
            return 0;
        }
        Integer total = 0;
        for (CartComputePriceDTO t :
                cartComputePriceDTOList) {
            Product product = productmapper.getProductByID(t.getId());
            // 这是哪个用户的哪些商品
            List<Cart> cartList = cartmapper.getCartListByPUId(userLoginVO.getId(), t.getId());
            for (Cart m:
                 cartList) {
                total += product.getPrice() * m.getNum();
            }
        }
        return total;
    }

    @Override// 结算与生成订单
    public CartSettlementVO settlementCart(CartComputePriceDTO[] cartComputePriceDTOS, UserLoginVO userLoginVO) {
        User user = usermapper.getUserById(userLoginVO.getId());
        if (user == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "不存在该用户");
        }
        if (cartComputePriceDTOS.length == 0) {// 如果没有选择任何商品，总价初始值为0
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST, "还未选择结算的商品");
        }
        Order order = new Order();
        OrderItem orderItem = new OrderItem();

        Address address = addressmapper.getDefaultByUserId(userLoginVO.getId());// 默认地址
        if(address==null){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"没有默认地址");
        }
        // 设置order
        // 设置用户相关信息
        order.setUser_id(userLoginVO.getId());
        order.setRecv_name(userLoginVO.getUsername());
        order.setRecv_phone(user.getPhone());
        order.setCreated_user(userLoginVO.getUsername());
        // 设置是否删除
        order.setIs_delete(0);

        // 设置地址
        order.setRecv_address(address.getAddress());// 详细地址

        order.setRecv_province(address.getProvince_name());
        order.setRecv_city(address.getCity_name());
        order.setRecv_area(address.getArea_name());
        // 设置状态
        order.setStatus(1);

        int result=ordermapper.save(order);// 先保存
        if(result!=1){
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "新建订单失败");
        }

        Order NewOrder = ordermapper.getNewOrder(userLoginVO.getId());
        if(NewOrder==null){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "未查询到最新订单");
        }
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        // 设置order_item
        Integer total = 0;// Order总金额
        for (CartComputePriceDTO t :
                cartComputePriceDTOS) {
            // 设置item总价
            Product product = productmapper.getProductByID(t.getId());
            if(product==null){
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"仓库不存在"+t.getId()+"商品");
            }
            // 这是哪个用户的哪件商品
            Cart cart = cartmapper.getCartByPUId(userLoginVO.getId(), t.getId());
            if(cart==null){
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND,"购物车不存在"+t.getId()+"商品");
            }
            if (cart.getNum() > product.getNum()) {
                throw new ServiceException(ServiceCode.ERR_BAD_REQUEST, "商品库存不足");
            }
            Integer itemTotal = 0;// 订单项总金额
            itemTotal = product.getPrice() * cart.getNum();
            total += itemTotal;

            // 设置订单项属于哪个Order
            orderItem.setOrder_id(NewOrder.getId());

            orderItem.setProduct_id(t.getId());
            orderItem.setNum(cart.getNum());
            orderItem.setPrice(itemTotal);
            orderItem.setImage(product.getImage());
            orderItem.setTitle(product.getTitle());
            orderItem.setCreated_user(userLoginVO.getUsername());

            int re=ordermapper.saveOrderItem(orderItem);
            if(re!=1){
                throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "订单项保存失败");
            }
            // 删除购物车选中项
            int re2=cartmapper.delCartCell(userLoginVO.getId(), t.getId());
            if(re2!=1){
                if(re!=1){
                    throw new ServiceException(ServiceCode.ERR_DELETE_FAILED, "购物车删除失败");
                }
            }
        }
        // 订单total_price更新
        int re3=ordermapper.updateOrderTotalPrice(total, userLoginVO.getId());
        if(re3!=1){
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "总订单的总金额更新失败");
        }
        // 开始返回数据
        CartSettlementVO cartSettlementVO = new CartSettlementVO();

        // 封装订单信息
        cartSettlementVO.setStatus(1);
        cartSettlementVO.setId(NewOrder.getId());
        cartSettlementVO.setRecv_name(userLoginVO.getUsername());
        cartSettlementVO.setTotal_price(total);
        // 时间格式转换
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = NewOrder.getOrder_time().toInstant().atZone(zoneId).toLocalDateTime();
        cartSettlementVO.setOrder_time(localDateTime);
        // 封装订单项VO
        List<OrderItem> orderItemList = ordermapper.getNewOrderItemList(NewOrder.getId());
        for (OrderItem t :
                orderItemList) {
            OrderItemVO orderItemVO = new OrderItemVO();

            orderItemVO.setId(t.getId());
            orderItemVO.setOrder_id(t.getOrder_id());
            orderItemVO.setTitle(t.getTitle());
            orderItemVO.setPrice(t.getPrice());
            orderItemVO.setProduct_id(t.getProduct_id());
            orderItemVO.setTotal(t.getPrice() * t.getNum());
            orderItemVO.setImage(t.getImage());
            orderItemVO.setNum(t.getNum());

            orderItemVOList.add(orderItemVO);
        }
        cartSettlementVO.setOrderItemVOList(orderItemVOList);

        // 封装地址信息
        cartSettlementVO.setAddressList(addressmapper.getAddressList(userLoginVO.getId()));
        return cartSettlementVO;
    }

    @Override// 购物车商品加减
    public CartNumVO getCartUpList(UserLoginVO userLoginVO, CartIdDTO cartIdDTO) {
        if (userLoginVO == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "未登录，没有购物车");
        }
        if(productmapper.getProductByID(cartIdDTO.getProduct_id())==null){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该商品不存在");
        }
        if (!cartIdDTO.getProduct_status()) {
            int result = cartmapper.updateDecreaseNum(cartIdDTO.getProduct_id(),userLoginVO.getId());
            if (result == 0) {
                throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "该商品数量减少失败");
            }
        } else {
            int result = cartmapper.updateAddNum(cartIdDTO.getProduct_id(),userLoginVO.getId());
            if (productmapper.getProductByID(cartIdDTO.getProduct_id()).getNum() <= cartmapper.getProductId(cartIdDTO.getProduct_id()).getNum()) {
                throw new ServiceException(ServiceCode.ERR_EXISTS, "超过商品最大库存");
            }
            if (result == 0) {
                throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "该商品数量增加失败");
            }
        }
        // 封装
        Cart cart = cartmapper.getProductId(cartIdDTO.getProduct_id());
        CartNumVO cartNumVO = new CartNumVO();
        cartNumVO.setProduct_num(cart.getNum());
        cartNumVO.setProduct_total(cart.getNum() * productmapper.getProductByID(cartIdDTO.getProduct_id()).getPrice());
        return cartNumVO;
    }

    @Override// 在线支付信息
    public CartOnlinePayVO getOnlinePayInfo(UserLoginVO loinUser,Integer id) {
        if (usermapper.getUserById(loinUser.getId()) == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "不存在该用户");
        }
        Order order = ordermapper.getNewOrder(loinUser.getId());
        if(order.getStatus()==2){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST, "您已支付该订单");
        }
        order.setModified_user(loinUser.getUsername());
        order.setStatus(1);
        Address address=addressmapper.getById(id);
        order.setRecv_province(address.getProvince_name());
        order.setRecv_city(address.getCity_name());
        order.setRecv_area(address.getArea_name());
        order.setRecv_address(address.getAddress());
        if (order == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "不存在您的订单");
        }
        int result=ordermapper.updateOrderPayTime(order.getId());
        int result2= ordermapper.updateOrder(order);
        if(result!=1||result2!=1){
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "支付时间更新失败");
        }
        CartOnlinePayVO payVO = new CartOnlinePayVO();
        payVO.setId(order.getId());
        payVO.setTotal_price(order.getTotal_price());

        return payVO;
    }

    @Override//确认付款信息
    public CartOnlinePayVO getPayOKInfo(UserLoginVO loinUser) {
        if (usermapper.getUserById(loinUser.getId()) == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "不存在该用户");
        }
        Order order = ordermapper.getNewOrder(loinUser.getId());
        if(order.getStatus()==2){
            throw new ServiceException(ServiceCode.ERR_BAD_REQUEST, "您已支付该订单");
        }
        order.setModified_user(loinUser.getUsername());
        order.setStatus(2);

        if (order == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "不存在您的订单");
        }
        int result=ordermapper.updateOrderPayTime(order.getId());
        int result2= ordermapper.updateOrderStatus(order);
        if(result!=1||result2!=1){
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "支付时间更新失败");
        }
        CartOnlinePayVO payVO = new CartOnlinePayVO();
        payVO.setId(order.getId());
        payVO.setTotal_price(order.getTotal_price());

        return payVO;
    }
    /**
     * 删除某一个商品
     *
     * @param product_id
     * @param userLoginVO
     */
    public void delete(Integer product_id, UserLoginVO userLoginVO) {
        // 判断这个商品是否存在
        log.debug("商品id:{}", product_id);
        Cart cart = cartmapper.getCartByPUId(userLoginVO.getId(), product_id);
        if (cart == null) {
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "该商品id不存在");
        }
        // 判断这个商品是否是属于当前用户
        if (cart.getUser_id() != userLoginVO.getId()) {
            throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "你没有权限删除该商品");
        }
        // 删除
        int result = cartmapper.delCartCell(userLoginVO.getId(), product_id);
        if (result != 1) {
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "删除失败");
        }
    }

    /**
     * 选中删除所有的商品
     *
     * @param productIdList
     * @param userLoginVO
     */
    public void delete1(List<Integer> productIdList, UserLoginVO userLoginVO) {
        for (Integer product_id : productIdList) {
            // 判断这个商品是否存在
            log.debug("商品id:", product_id);
            Cart cart = cartmapper.getCartByPUId(userLoginVO.getId(), product_id);
            if (cart == null) {
                throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "商品id不存在");
            }
            // 判断这个商品是否是属于当前用户
            if (cart.getUser_id() != userLoginVO.getId()) {
                throw new ServiceException(ServiceCode.ERR_FORBIDDEN, "你没有权限删除商品");
            }
            // 删除
            int result = cartmapper.delCartCell(userLoginVO.getId(), product_id);
            if (result != 1) {
                throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "删除失败");
            }
        }
    }

    @Override//添加进购物车
    public void addIntoCart(CartAddDTO cartAddDTO, UserLoginVO userLoginVO) {
        if(userLoginVO==null){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "还未登录");
        }
        System.out.println(cartAddDTO.getId()+cartAddDTO.getNum());
        Product product=productmapper.getProductByID(cartAddDTO.getId());
        if(product==null){
            throw new ServiceException(ServiceCode.ERR_NOT_FOUND, "不存在此商品");
        }

        Cart cart=new Cart();
        cart.setNum(cartAddDTO.getNum());
        cart.setPrice(product.getPrice());
        cart.setProduct_id(product.getId());
        cart.setCreated_user(userLoginVO.getUsername());
        cart.setUser_id(userLoginVO.getId());

        int result=cartmapper.save(cart);
        if(result!=1){
            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "保存失败");
        }
    }


}
