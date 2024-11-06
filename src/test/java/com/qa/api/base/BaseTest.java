package com.qa.api.base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.qa.api.client.RestClient;
import com.qa.api.mocking.WireMockSetUp;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;


public class BaseTest {
	
	protected final static String BASE_URL_LOCALHOST_PORT = "http://localhost:8090";
	protected final static String BASE_URL_REQRES = "https://reqres.in";
	protected final static String BASE_URL_GOREST = "https://gorest.co.in";
	protected final static String BASE_URL_CONTACTS = "https://thinking-tester-contact-list.herokuapp.com";
	protected final static String BASE_URL_PRODUCTS = "https://fakestoreapi.com";
	protected final static String BASE_URL_BASIC_AUTH = "https://the-internet.herokuapp.com";
	protected final static String BASE_URL_AMADEUS = "https://test.api.amadeus.com";
	
	protected RestClient restClient;
	
	
	@BeforeTest
	public void setUp() {
		
		RestAssured.filters(new AllureRestAssured());
		restClient = new RestClient();
		WireMockSetUp.createWireMockServer();
	}
	
	@AfterTest
	public void stopMockServer() {
		WireMockSetUp.stopWireMockServer();
	}
}
