package com.qa.api.mocking;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class WireMockSetUp {
	
	private static WireMockServer server;
	
	public static void createWireMockServer() {
		server = new WireMockServer(8090);
		WireMock.configureFor("localhost", 8090);
		server.start();
	}
	
	public static void stopWireMockServer() {
		server.stop();
	}
}
