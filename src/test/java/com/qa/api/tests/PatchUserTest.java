package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PatchUserTest extends BaseTest{
	
	@Test
	public void patchUsersWithBuilderTest() {
		
		//1. POST:
		User user = User.builder()
				.name("Payal")
				.email(StringUtility.getRandomEmailId())
				.status("active")
				.gender("female")
				.build();
		
		Response response = restClient.post(BASE_URL_GOREST, "/public/v2/users/",user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 201);
		
		//fetch userId:
		String userId = response.jsonPath().getString("id");
		System.out.println("user id ===>" + userId);
		
		//2. GET:
		Response responseGet = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		
		//update the user details using the setter:
		user.setEmail(StringUtility.getRandomEmailId());
		
		//3. PUT: update the same user with same user id
		Response responsePut = restClient.patch(BASE_URL_GOREST, "/public/v2/users/"+userId,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePut.getStatusCode(), 200);
		Assert.assertEquals(responsePut.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePut.jsonPath().getString("email"), user.getEmail());
	}


}
