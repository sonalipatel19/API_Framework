package com.qa.api.tests;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest{
	
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
		AssertJUnit.assertEquals(response.getStatusCode(), 201);
		
		//fetch userId:
		String userId = response.jsonPath().getString("id");
		System.out.println("user id ===>" + userId);
		
		//2. GET:
		Response responseGet = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		AssertJUnit.assertEquals(responseGet.getStatusCode(), 200);
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		
		
		//3. Delete: update the same user with same user id
		Response responseDelete = restClient.delete(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		AssertJUnit.assertEquals(responseDelete.getStatusCode(), 204);
		
		//4. GET: recheck the user with the same user id
		Response responseGetAfterDelete = restClient.get(BASE_URL_GOREST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		AssertJUnit.assertEquals(responseGetAfterDelete.getStatusCode(), 404);
		Assert.assertEquals(responseGetAfterDelete.jsonPath().getString("message"), "Resource not found");
		
	}


}
