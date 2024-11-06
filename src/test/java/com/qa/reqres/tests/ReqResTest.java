package com.qa.reqres.tests;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ReqResTest extends BaseTest{
	
	@Test
	public void getUserTest() {
		Response response = restClient.get(BASE_URL_REQRES, "/api/users?page=2", null, null, AuthType.NO_AUTH, ContentType.JSON);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
	}

}
