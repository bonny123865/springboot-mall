package com.bonny.springbootmall.controller;



import com.bonny.springbootmall.SpringbootMallApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bonny.springbootmall.constant.ProductCategory;
import com.bonny.springbootmall.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(classes = SpringbootMallApplication.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	// 要知道覆蓋的情境有多少，而不是功能的設計，列舉情境
	// 如果忘記單元測試會返回甚麼數據回來，可以使用 : " .andDo(print()) " ，會輸出在終端機上
	// 測試上的 CUD 可以加上 @Transactional
	//


	// 查詢商品
	@Test
	public void getProduct_success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products/{productId}", 1);

		mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productName", equalTo("蘋果（澳洲）")))
				.andExpect(jsonPath("$.category", equalTo("FOOD")))
				.andExpect(jsonPath("$.imageUrl", notNullValue()))
				.andExpect(jsonPath("$.price", notNullValue()))
				.andExpect(jsonPath("$.stock", notNullValue()))
				.andExpect(jsonPath("$.description", notNullValue()))
				.andExpect(jsonPath("$.createdDate", notNullValue()))
				.andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
	}

	@Test
	public void getProduct_notFound() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products/{productId}", 20000);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	// 創建商品
	@Transactional
	@Test
	public void createProduct_success() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");
		productRequest.setCategory(ProductCategory.FOOD);
		productRequest.setImageUrl("http://test.com");
		productRequest.setPrice(100);
		productRequest.setStock(2);

		String json = objectMapper.writeValueAsString(productRequest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(201))
				.andExpect(jsonPath("$.productName", equalTo("test food product")))
				.andExpect(jsonPath("$.category", equalTo("FOOD")))
				.andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
				.andExpect(jsonPath("$.price", equalTo(100)))
				.andExpect(jsonPath("$.stock", equalTo(2)))
				.andExpect(jsonPath("$.description", nullValue()))
				.andExpect(jsonPath("$.createdDate", notNullValue()))
				.andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
	}

	@Transactional
	@Test
	public void createProduct_illegalArgument() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");

		String json = objectMapper.writeValueAsString(productRequest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));
	}

	// 更新商品
	@Transactional
	@Test
	public void updateProduct_success() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");
		productRequest.setCategory(ProductCategory.FOOD);
		productRequest.setImageUrl("http://test.com");
		productRequest.setPrice(100);
		productRequest.setStock(2);

		String json = objectMapper.writeValueAsString(productRequest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/products/{productId}", 3)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.productName", equalTo("test food product")))
				.andExpect(jsonPath("$.category", equalTo("FOOD")))
				.andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
				.andExpect(jsonPath("$.price", equalTo(100)))
				.andExpect(jsonPath("$.stock", equalTo(2)))
				.andExpect(jsonPath("$.description", nullValue()))
				.andExpect(jsonPath("$.createdDate", notNullValue()))
				.andExpect(jsonPath("$.lastModifiedDate", notNullValue()));
	}

	// "illegalArgument" : 測試看看是否能成功擋下來錯誤
	@Transactional
	@Test
	public void updateProduct_illegalArgument() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");

		String json = objectMapper.writeValueAsString(productRequest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/products/{productId}", 3)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(400));

	}

	@Transactional
	@Test
	public void updateProduct_productNotFound() throws Exception {
		ProductRequest productRequest = new ProductRequest();
		productRequest.setProductName("test food product");
		productRequest.setCategory(ProductCategory.FOOD);
		productRequest.setImageUrl("http://test.com");
		productRequest.setPrice(100);
		productRequest.setStock(2);

		String json = objectMapper.writeValueAsString(productRequest);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/products/{productId}", 20000)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(404));
	}

	// 刪除商品
	@Transactional
	@Test
	public void deleteProduct_success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/products/{productId}", 5);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(204));
	}

	// 當刪除一個不存在的商品時
	@Transactional
	@Test
	public void deleteProduct_deleteNonExistingProduct() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/products/{productId}", 20000);

		mockMvc.perform(requestBuilder)
				.andExpect(status().is(204));
	}

	// 查詢商品列表
	@Test
	public void getProducts() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products");

		mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.limit", notNullValue()))
				.andExpect(jsonPath("$.offset", notNullValue()))
				.andExpect(jsonPath("$.total", notNullValue()))
				.andExpect(jsonPath("$.results", hasSize(5)));
	}

	@Test
	public void getProducts_filtering() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products")
				.param("search", "B")
				.param("category", "CAR");

		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.limit", notNullValue()))
				.andExpect(jsonPath("$.offset", notNullValue()))
				.andExpect(jsonPath("$.total", notNullValue()))
				.andExpect(jsonPath("$.results", hasSize(2)));
	}

	@Test
	public void getProducts_sorting() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products")
				.param("orderBy", "price")
				.param("sort", "desc");

		mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.limit", notNullValue()))
				.andExpect(jsonPath("$.offset", notNullValue()))
				.andExpect(jsonPath("$.total", notNullValue()))
				.andExpect(jsonPath("$.results", hasSize(5)))
				.andExpect(jsonPath("$.results[0].productId", equalTo(6)))
				.andExpect(jsonPath("$.results[1].productId", equalTo(5)))
				.andExpect(jsonPath("$.results[2].productId", equalTo(7)))
				.andExpect(jsonPath("$.results[3].productId", equalTo(4)))
				.andExpect(jsonPath("$.results[4].productId", equalTo(2)));
	}

	@Test
	public void getProducts_pagination() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/products")
				.param("limit", "2")
				.param("offset", "2");

		mockMvc.perform(requestBuilder)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.limit", notNullValue()))
				.andExpect(jsonPath("$.offset", notNullValue()))
				.andExpect(jsonPath("$.total", notNullValue()))
				.andExpect(jsonPath("$.results", hasSize(2)))
				.andExpect(jsonPath("$.results[0].productId", equalTo(5)))
				.andExpect(jsonPath("$.results[1].productId", equalTo(4)));
	}
}