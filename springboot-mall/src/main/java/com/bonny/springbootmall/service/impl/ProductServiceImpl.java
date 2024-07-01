package com.bonny.springbootmall.service.impl;

import com.bonny.springbootmall.dao.ProductDao;
import com.bonny.springbootmall.model.Product;
import com.bonny.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
