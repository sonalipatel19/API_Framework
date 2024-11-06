package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest{
	
	@Test
	public void getUsersTest() {

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("name", "naveen");
		queryParams.put("status", "active");
		
		Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users", queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(enabled = false)
	public void getSingleUsersTest() {
		
		Response response = restClient.get(BASE_URL_GOREST, "/public/v2/users/7470549", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}

	
	

}
