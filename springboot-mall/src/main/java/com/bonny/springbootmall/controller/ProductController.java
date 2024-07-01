package com.bonny.springbootmall.controller;

import com.bonny.springbootmall.dto.ProductRequest;
import com.bonny.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bonny.springbootmall.model.Product;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    //取得商品數據
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){

        // 如果查詢出來不是NULL，會回傳 "NOT_FOUND"
        Product product = productService.getProductById(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //如果有加上 "Not Null"，要記得加上 "@Valid"
    @PostMapping("/products")
    public  ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    //可以有效確定、界定前端只能去修改 "productRequest" 這個變數值，而不是去修改整個 "Product" 物件
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        // 可以時做嚴謹一點 : 檢查是否存在
        Product product = productService.getProductById(productId);

        if (product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 修改商品數據
        productService.updateProduct(productId,productRequest);
        Product updateProduct = productService.getProductById(productId);

        return  ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    // 商品存在，成功刪除。商品本來就不存在
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 返回商品的所有數據 ，實作 "/products"
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> productList = productService.getProducts();

        // 就算資源不存在，但是URL存在，所以都需要固定回傳 200 OK 給前端
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }


}
