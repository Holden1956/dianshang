package com.example.mapper;

import com.example.pojo.commonPojo.Operate;
import com.example.pojo.commonPojo.Order;
import com.example.pojo.commonPojo.OrderItem;
import com.example.pojo.vo.OrderItemDetailVO;
import com.example.pojo.vo.OrderOptVO;
import com.example.pojo.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ordermapper {
    List<OrderVO> getOrderList(Integer user_id);

    int save(Order order);

    int saveOrderItem(OrderItem orderItem);

    Order getNewOrder(Integer user_id);

    int updateOrderTotalPrice(@Param("total_price") Integer total_price, @Param("user_id") Integer user_id);

    List<OrderItem> getNewOrderItemList(Integer order_id);

    int updateOrderPayTime(Integer id);

    void updateNewOrderStatus(Integer id);

    //----
    OrderItemDetailVO getOrderItemDetail(@Param("orderId") Integer orderId, @Param("productId") Integer productId);

    List<OrderOptVO> getOrderOpt(@Param("productId") Integer productId, @Param("id") Integer id);

    Integer getZip(@Param("provinceName") String provinceName,
                   @Param("recvCity") String recvCity, @Param("recvArea") String recvArea);

    List<OrderVO> getOrderListByStatus(@Param("userId") Integer userId, @Param("status") Integer status);

    int updateOrder(Order order);

    List<OrderItem> getOrderItemList(Integer id);

    int insertOperate(Operate operate);

    Order getOrderById(Integer orderId);

    int updateOrderStatus(Order order);

    List<Order> getAllOrder();
}
