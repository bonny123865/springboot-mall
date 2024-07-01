package com.bonny.springbootmall.dao.impl;

import com.bonny.springbootmall.constant.ProductCategory;
import com.bonny.springbootmall.dao.ProductDao;
import com.bonny.springbootmall.dto.ProductQueryParams;
import com.bonny.springbootmall.dto.ProductRequest;
import com.bonny.springbootmall.model.Product;
import com.bonny.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Override
//    public List<Product> getProducts() {
//        String sql = "SELECT product_id,product_name, category, image_url, " +
//                "price, stock, description, created_date, last_modified_date " +
//                "FROM product";
//
//        Map<String, Object> map = new HashMap<>();
//        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
//
//        return productList;
//    }


    // 利用 "WHERE 1=1" ，可以讓 "category != null" 去拼接查詢條件
    // 要記得在 "AND" 之前留一個空白建 ，要變成" AND"
    // "LIKE" : 可以適用於模糊查詢， 【LIKE '蘋果%'】 (有包含蘋果，後方有字)
    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id,product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date " +
                "FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        if(productQueryParams.getCategory() != null){
            sql = sql +" AND category = :category";
            map.put("category", productQueryParams.getCategory().name());
        }

        if(productQueryParams.getSearch() != null){
            sql = sql +" AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%");
        }

        // 排序 : 一定不會是 null所以可以直接加上去語句，因為有預設值
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();


        // 分頁 : 一定不會是 null所以可以直接加上去語句，，因為有預設值
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        return productList;
    }


    @Override
    public Product getProductById(Integer productId){

        String sql = "SELECT product_id,product_name, category, image_url, " +
                "price, stock, description, created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);  // Corrected key to match the SQL parameter

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        if(productList.size() > 0){
            return productList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql ="INSERT INTO product(product_name, category, image_url, price, stock," +
                " description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock," +
                " :description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate .update(sql, new MapSqlParameterSource(map), keyHolder);
        int productId = keyHolder.getKey().intValue();


        return productId;
    }

    // 要記得更新後修改的時間
    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        String sql ="UPDATE product SET product_name = :productName, category =:category, image_url =:imageUrl," +
                " price =:price, stock = :stock, description =:description ,last_modified_date =:lastModifiedDate " +
                "WHERE product_id =:productId\n";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);


        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql,map);
    }


    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id =:productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql,map);
    }
}
