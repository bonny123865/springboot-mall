package com.bonny.springbootmall.dao;

import com.bonny.springbootmall.dto.ProductQueryParams;
import com.bonny.springbootmall.dto.ProductRequest;
import com.bonny.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    Integer countProduct(ProductQueryParams productQueryParams);
    List<Product> getProducts(ProductQueryParams productQueryParams);
    //根據 id 找尋商品
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
