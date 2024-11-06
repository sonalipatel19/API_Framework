package com.qa.products.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonPathUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPIWithDeserializationTest extends BaseTest{
	
	@Test
	public void getProductsTest() {
		Response response = restClient.get(BASE_URL_PRODUCTS, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		
		Product[] product = JsonPathUtil.deserialize(response, Product[].class);
		
		System.out.println(product);
		
		for(Product p : product) {
			
		}
		
	}

}
