package com.myapp.spring.web.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;


@SpringBootTest

@AutoConfigureMockMvc
public class ProductAPITest {
	
	@MockBean
	private  ProductRepository repository;
		
		@Autowired
		private MockMvc mockMvc;
		
		@Test
		@DisplayName("Test Product Id - GET /api/v1/products/")
		public void testGetProductId() throws Exception {
			
			Product product1 = new Product("Oneplus","OnePlus9Pro", 70000.00,4.5);
			product1.setProductId(35);
			
			Product product2 = new Product("Oneplus","OnePlus8Pro", 60000.00,4.5);
			product2.setProductId(36);
			
			List<Product> products = new ArrayList<>();
			products.add(product1);
			products.add(product2);
			
			
			doReturn(Optional.of(products)).when(repository).findByAll();
		
			
			mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}",35))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].productId",is(35)))
            .andExpect(jsonPath("$[0].productName",is("oneplus")))
            .andExpect(jsonPath("$[0].description",is("oneplus9pro")))
            .andExpect(jsonPath("$[0].price",is(70000.00)))
            .andExpect(jsonPath("$[0].startRating",is(4.5)));
			
			.andExpect(jsonPath("$[1].productId",is(36)))
            .andExpect(jsonPath("$[1].productName",is("oneplus")))
            .andExpect(jsonPath("$[1].description",is("oneplus8pro")))
            .andExpect(jsonPath("$[1].price",is(60000.00)))
            .andExpect(jsonPath("$[1].startRating",is(4.5)));
			
           }
		@Test
		@DisplayName("Test Product Id - GET /api/v1/products/")
		public void testAddNewProduct() throws Exception {
			
			Product newproduct = new Product("Oneplus","OnePlus9Pro", 70000.00,4.5);
			
			
			Product mockproduct= new Product("Oneplus","OnePlus8Pro", 60000.00,4.5);
			mockproduct.setProductId(50);
			
			doReturn(mockproduct).when(repository).save(ArgumentMachers.any());
		
			
			mockMvc.perform(post("/api/v1/products")
					
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(new ObjectMapper().writeValueAsString(newproduct)))
			
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.productId",is(50)))
            .andExpect(jsonPath("$.productName",is("oneplus")))
            .andExpect(jsonPath("$.description",is("oneplus9pro")))
            .andExpect(jsonPath("$.price",is(70000.00)))
            .andExpect(jsonPath("$.startRating",is(4.5)));
			
			
	}
		@Test
		@DisplayName("Test Product Price - GET /api/v1/products/")
		public void testGetAllProductsByPrice() throws Exception {
			
			Product product1 = new Product("Oneplus","OnePlus9Pro", 70000.00,4.5);
			product1.setProductId(35);
			
			Product product2 = new Product("Oneplus","OnePlus8Pro", 60000.00,4.5);
			product2.setProductId(36);
			
			Product product3= new Product("Iphone","Iphone12", 80000.00,4.5);
			product3.setProductId(36);
			
			List<Product> products = new ArrayList<>();
			products.add(product1);
			products.add(product2);
			products.add(product3);
			
			//Prepare Mock Service Method
			double price = 50000.0;
			String productName= "Oneplus";
			
			
			doReturn(Optional.of(products)).when(repository).findByPriceGreaterthanEqual(price);
		
			
			mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/find/{price}",price))
			.andExpect(status().isOk())
			
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].productId",is(35)))
            .andExpect(jsonPath("$[0].productName",is("oneplus")))
            .andExpect(jsonPath("$[0].description",is("oneplus9pro")))
            .andExpect(jsonPath("$[0].price",is(60000.00)))
            .andExpect(jsonPath("$[0].startRating",is(4.5)));
			
            .andExpect(jsonPath("$[0].productId",is(36)))
            .andExpect(jsonPath("$[0].productName",is("oneplus")))
            .andExpect(jsonPath("$[0].description",is("oneplus9pro")))
            .andExpect(jsonPath("$[0].price",is(70000.00)))
            .andExpect(jsonPath("$[0].startRating",is(4.5)));
            
            .andExpect(jsonPath("$[0].productId",is(37)))
            .andExpect(jsonPath("$[0].productName",is("oneplus")))
            .andExpect(jsonPath("$[0].description",is("oneplus9pro")))
            .andExpect(jsonPath("$[0].price",is(80000.00)))
            .andExpect(jsonPath("$[0].startRating",is(4.5)))
		}
}

			
			
		




