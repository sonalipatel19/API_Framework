package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;


import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.FrameworkExceptions;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestClient {

	// define response spec:

	private ResponseSpecification responseSpec200 = expect().statusCode(200);
	private ResponseSpecification responseSpec200or404 = expect().statusCode(anyOf(equalTo(200), equalTo(404)));
	private ResponseSpecification responseSpec201 = expect().statusCode(201);
	private ResponseSpecification responseSpec200or201 = expect().statusCode(anyOf(equalTo(200), equalTo(201)));
	private ResponseSpecification responseSpec204 = expect().statusCode(204);
	private ResponseSpecification responseSpec400 = expect().statusCode(400);
	private ResponseSpecification responseSpec401 = expect().statusCode(401);
	private ResponseSpecification responseSpec404 = expect().statusCode(404);
	private ResponseSpecification responseSpec422 = expect().statusCode(422);
	private ResponseSpecification responseSpec500 = expect().statusCode(500);

	//private String baseUrl = ConfigManager.get("baseUrl");

	private RequestSpecification setUpRequest(String baseUrl, AuthType authType, ContentType contentType) {

		RequestSpecification request = RestAssured.given().log().all().baseUri(baseUrl).contentType(contentType)
				.accept(contentType);

		switch (authType) {
		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("bearerToken"));
			break;
		case CONTACTS_BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("contactsBearerToken"));
			break;
		case OAUTH2:
			request.header("Authorization", "Bearer " + generateOAuth2Token());
			break;
		case BASIC_AUTH:
			request.header("Authorization", "Basic " + generateBasicAuthToken());
			break;
		case API_KEY:
			request.header("api_key", ConfigManager.get("apiKey"));
			break;
		case NO_AUTH:
			System.out.println("Auth is not required....");
			break;

		default:
			System.out.println("This Auth is not supported...please pass the right AuthType....");
			throw new FrameworkExceptions("NO AUTH SUPPORTED...");
		}

		return request;
	}

	private String generateBasicAuthToken() {
		String credientails = ConfigManager.get("basicUsername")+ ":" + ConfigManager.get("basicPassword");
		return Base64.getEncoder().encodeToString(credientails.getBytes());
	}
	
	private String generateOAuth2Token() {
		return RestAssured.given().formParam("client_id", ConfigManager.get("clientId"))
				.formParam("client_secret", ConfigManager.get("clientSecret"))
				.formParam("grant_type", ConfigManager.get("grantType"))
				.post(ConfigManager.get("tokenUrl"))
				.then()
				.extract().path("access_token");

	}

	// ********************************** CRUD Methods***********************//

//	public Response get(String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
//			AuthType authType, ContentType contentType) {
//
//		RequestSpecification request = setUpAuthAndContentType(authType, contentType);
//
//		applyParams(request, queryParams, pathParams);
//
//		Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();
//		response.prettyPrint();
//		return response;
//	}

	public Response get(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

		RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.get(endPoint).then().spec(responseSpec200or404).extract().response();
		response.prettyPrint();
		return response;
	}

	public <T> Response post(String baseUrl, String endPoint, T body, Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

		RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.body(body).post(endPoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;

	}

	public <T> Response post(String baseUrl, String endPoint, File file, Map<String, String> queryParams,
			Map<String, String> pathParams, AuthType authType, ContentType contentType) {

		RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.body(file).post(endPoint).then().spec(responseSpec201).extract().response();
		response.prettyPrint();
		return response;

	}

	public <T> Response put(String baseUrl, String endPoint, T body, Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

		RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.body(body).put(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;

	}

	public <T> Response patch(String baseUrl, String endPoint, T body, Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

		RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.body(body).patch(endPoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;

	}

	public <T> Response delete(String baseUrl, String endPoint, Map<String, String> queryParams, Map<String, String> pathParams,
			AuthType authType, ContentType contentType) {

		RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);

		applyParams(request, queryParams, pathParams);

		Response response = request.delete(endPoint).then().spec(responseSpec204).extract().response();
		response.prettyPrint();
		return response;

	}

	private RequestSpecification setUpAuthAndContentType(String baseUrl, AuthType authType, ContentType contentType) {
		return setUpRequest(baseUrl, authType, contentType);
	}

	private void applyParams(RequestSpecification request, Map<String, String> queryParams,
			Map<String, String> pathParams) {

		if (queryParams != null) {
			request.queryParams(queryParams);
		}
		if (pathParams != null) {
			request.pathParams(pathParams);
		}
	}

}
