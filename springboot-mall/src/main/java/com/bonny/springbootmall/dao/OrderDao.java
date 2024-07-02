package com.bonny.springbootmall.dao;

import com.bonny.springbootmall.dto.CreateOrderRequest;
import com.bonny.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
