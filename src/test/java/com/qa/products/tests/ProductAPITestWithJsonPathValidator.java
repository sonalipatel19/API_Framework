package com.qa.products.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonPathUtil;
import com.qa.api.utils.JsonPathValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithJsonPathValidator extends BaseTest{
	
	@Test
	public void getProductsTest() {
		Response response = restClient.get(BASE_URL_PRODUCTS, "/products", null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		
		List<Number> prices = JsonPathValidator.readList(response, "$[?(@.price > 50)].price");
		System.out.println(prices);
	}
}
