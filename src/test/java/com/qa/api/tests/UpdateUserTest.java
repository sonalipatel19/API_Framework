package com.qa.api.tests;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest{
	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{"Naveen", "male", "active", "inactive", "Sunil"},
			{"Abhishek", "male", "inactive", "active", "Abhi"},
			{"Pooja", "female", "active", "inactive", "Puja"}
		};
	}
	
	@Test(dataProvider = "getUserData")
	public void UpdateUsersWithBuilderTest(String name, String gender, String status, String updateStatus, String updateName) {
		
		//1. POST:
		User user = User.builder()
				.name(name)
				.email(StringUtility.getRandomEmailId())
				.status(status)
				.gender(gender)
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
		
		//update the user details using the setter:
		user.setStatus(updateStatus);
		user.setName(updateName);
		
		//3. PUT: update the same user with same user id
		Response responsePut = restClient.put(BASE_URL_GOREST, "/public/v2/users/"+userId,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		AssertJUnit.assertEquals(responsePut.getStatusCode(), 200);
		Assert.assertEquals(responsePut.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePut.jsonPath().getString("status"), user.getStatus());
		Assert.assertEquals(responsePut.jsonPath().getString("gender"), user.getGender());
	}

}
