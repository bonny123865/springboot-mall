package com.bonny.springbootmall.controller;

import com.bonny.springbootmall.constant.ProductCategory;
import com.bonny.springbootmall.dto.ProductQueryParams;
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

    // 可以使用 enum 去時做，已經會被轉換完成，需要將category從Service層 拉到Dao層裡面去
    // "(required = false)" 代表前端不一定藥袋上這一個參數，特好用，讚
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(

            // 查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // 排序 Sorting
            // "orderBy" : 商品類型或是什麼類別來做排序，預設使用 "defaultValue"
            // "sort" : 升冪是降冪排列，預設使用 "desc"，這種排序只限定於單排序，只能用一個條件進行排序
            // "http://localhost:8080/products" : 會使用預設的 "創建時間" 來做排序
            @RequestParam(defaultValue = "created_date")  String orderBy,
            @RequestParam(defaultValue = "desc")  String sort
            ){

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);

        //參數傳遞
//        List<Product> productList = productService.getProducts(category,search);
        List<Product> productList = productService.getProducts(productQueryParams);

        // 就算資源不存在，但是URL存在，所以都需要固定回傳 200 OK 給前端
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }




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




}
