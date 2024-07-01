package com.bonny.springbootmall.service;

import com.bonny.springbootmall.model.Product;

public interface ProductService {
    //根據 id 找尋商品
    Product getProductById(Integer productId);
}
