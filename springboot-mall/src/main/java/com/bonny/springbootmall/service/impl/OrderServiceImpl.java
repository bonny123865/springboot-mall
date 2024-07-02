package com.bonny.springbootmall.service.impl;

import com.bonny.springbootmall.dao.OrderDao;
import com.bonny.springbootmall.dto.BuyItem;
import com.bonny.springbootmall.dto.CreateOrderRequest;
import com.bonny.springbootmall.model.OrderItem;
import com.bonny.springbootmall.model.Product;
import com.bonny.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bonny.springbootmall.dao.ProductDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    @Autowired
    private  ProductDao productDao;

    // 因為有兩個資料庫同時運作 "createOrder" 、 "createOrderItem"
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 計算總價前
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            // 轉換 BuyItem 變成 OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        // 創建訂單、新增多個品項到單一訂單上
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);




        return orderId;
    }
}
