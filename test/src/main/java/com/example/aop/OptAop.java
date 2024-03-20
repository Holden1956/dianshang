package com.example.aop;

import com.example.common.JWTUtils;
import com.example.common.ServiceCode;
import com.example.common.ServiceException;
import com.example.mapper.optmapper;
import com.example.mapper.ordermapper;
import com.example.mapper.productmapper;
import com.example.pojo.commonPojo.Opt;
import com.example.pojo.commonPojo.Order;
import com.example.pojo.commonPojo.OrderItem;
import com.example.pojo.dto.CartAddDTO;
import com.example.pojo.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date 2024/3/12 10:11
 * @Author qixuan he
 * @Description: 环绕切面获取用户行为
 */
@Component// 组件
@Aspect// 切面
@Slf4j
public class OptAop {
    @Autowired
    private optmapper optmapper;
    @Autowired
    private productmapper productmapper;
    @Autowired
    private ordermapper ordermapper;

    @Around("execution(* com.example.service.impl.*.*(..))")
    public Object getOptAdvice(ProceedingJoinPoint jp) throws Throwable {
        Object result = null;
        try {
            // 获取方法名
            String methodName = jp.getSignature().getName();
            // 获取参数列表
            Object[] args = jp.getArgs();
            // 商品编号
            Integer product_id = -1;
            // 获取用户
            UserLoginVO user = getUser();
            if (user == null) {
                return null;
            }
            switch (methodName) {
                case "getDetailById": // 查看商品详情
                    // 获取用户的编号 以及商品编号 ……
                    System.out.println("pv被调用了");
                    product_id = (Integer) args[0];
                    // 获取用户id

                    // todo 持久化到数据库去
                    Opt opt = new Opt();
                    opt.setType("pv");
                    opt.setUser_id(user.getId());
                    opt.setProduct_id(product_id);
                    opt.setCategory_id(productmapper.getProductByID(product_id).getCategory_id());
                    int re = optmapper.createdOpt(opt);
                    if (re != 1) {
                        throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "保存失败");
                    }
                    // 判断是否已经收集过了
                    break;
                case "addIntoCart":// 加入购物车
                    System.out.println("cart被调用了");
                    CartAddDTO cartAddDTO = (CartAddDTO) args[0];

                    Opt opt1 = new Opt();
                    opt1.setType("cart");
                    opt1.setUser_id(user.getId());
                    opt1.setProduct_id(cartAddDTO.getId());
                    opt1.setCategory_id(productmapper.getProductByID(cartAddDTO.getId()).getCategory_id());
                    int re2 = optmapper.createdOpt(opt1);
                    if (re2 != 1) {
                        throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "保存失败");
                    }
                    break;
                case "getPayOKInfo":
                    System.out.println("buy被调用了");
                    Order order = ordermapper.getNewOrder(user.getId());
                    List<OrderItem> orderItemList = ordermapper.getNewOrderItemList(order.getId());
                    for (OrderItem t :
                            orderItemList) {
                        Opt opt2 = new Opt();
                        opt2.setType("buy");
                        opt2.setUser_id(user.getId());
                        opt2.setProduct_id(t.getProduct_id());
                        opt2.setCategory_id(productmapper.getProductByID(t.getProduct_id()).getCategory_id());
                        int re3 = optmapper.createdOpt(opt2);
                        if (re3 != 1) {
                            throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "保存失败");
                        }
                    }
                    break;
                case "addFavorite":
                    System.out.println("favorite被调用了");
                    product_id = (Integer) args[0];

                    Opt opt3 = new Opt();
                    opt3.setType("favorite");
                    opt3.setUser_id(user.getId());
                    opt3.setProduct_id(product_id);
                    opt3.setCategory_id(productmapper.getProductByID(product_id).getCategory_id());
                    int re4 = optmapper.createdOpt(opt3);
                    if (re4 != 1) {
                        throw new ServiceException(ServiceCode.ERR_SAVE_FAILED, "保存失败");
                    }
                    break;
                default:
                    // todo
                    break;
            }
        } catch (Exception e) {
            result = jp.proceed();
        }
        return jp.proceed();
    }


    private UserLoginVO getUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
        String token = httpServletRequest.getHeader("Authorization");

        UserLoginVO userLoginVO = JWTUtils.parseToken(token);
        return userLoginVO;
    }
}


