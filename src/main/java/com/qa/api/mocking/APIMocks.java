package com.qa.api.mocking;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import static org.hamcrest.Matchers.equalTo;

import org.apache.http.impl.conn.Wire;
import org.hamcrest.Matchers;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;

public class APIMocks {
	
	public static void getDummyUser() {
		stubFor(get(urlEqualTo("/api/users"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\r\n"
								+ "    \"name\" : \"Tom\"\r\n"
								+ "}")));
	}
	
	public static void getDummyUserWithJsonFile() {
		stubFor(get(urlEqualTo("/api/users"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBodyFile("user.json")));
	}
	
	public static void getDummyUserWithQueryParam() {
		stubFor(get(urlEqualTo("/api/users"))
				.withQueryParam("name", (StringValuePattern) equalTo("api"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBodyFile("user.json")));
	}

}
