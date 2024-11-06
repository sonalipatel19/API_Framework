package com.qa.mocking.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.mocking.APIMocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class MockGetUserAPITest extends BaseTest{
	
	@Test
	public void getDummyUserTest() {
	
		APIMocks.getDummyUser();
		Response response = restClient.get(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		response.then()
		.assertThat()
		.statusCode(200)
		.body("name", Matchers.equalTo("Tom"));
		
	}
	
	@Test
	public void getDummyUserWithQueryParamsTest() {
	
		APIMocks.getDummyUser();
		Response response = restClient.get(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		response.then()
		.assertThat()
		.statusCode(200)
		.body("name", Matchers.equalTo("api"));
		
	}
	
	@Test
	public void getDummyUserWithJsonFileTest() {
	
		APIMocks.getDummyUser();
		Response response = restClient.get(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
		
		response.then()
		.assertThat()
		.statusCode(200)
		.body("name", Matchers.equalTo("Hiten"));
		
	}
}
