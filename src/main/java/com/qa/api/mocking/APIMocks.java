package com.qa.api.mocking;

import com.github.tomakehurst.wiremock.client.WireMock;


import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;

import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;



public class APIMocks {
	
	//*******************************************Creating mock for GET CALLS*******************************************
	public static void getDummuyUser() {
		
		stubFor(get(urlEqualTo("/api/users"))
					.willReturn(aResponse()
					.withStatus(200)
					.withHeader("Content-Type", "application/jSON")
					.withBody("{\r\n"
							+ "    \"name\":\"Tom\"\r\n"
							+ "}")
				));
	}
	
	//Creating mock for get products with JSON File as Input
	public static void getDummuyUserWithQueryParam() {
						
					stubFor(get(urlPathEqualTo("/api/users"))
							.withQueryParam("name", equalTo("VshubhamB"))
							.willReturn(aResponse()
							.withStatus(200)
							.withHeader("Content-Type", "application/jSON")
							.withBodyFile("user.json")

						));	
				}		
	
	
	
	
	
	//Creating mock for get user with JSON File as Input
	public static void getDummuyUserWithJsonFile() {
			
			stubFor(get(urlEqualTo("/api/users"))
						.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/jSON")
						.withBodyFile("user.json")

					));	
	}
	
	//Creating mock for get products with JSON File as Input
	public static void getDummuyProductsWithJsonFile() {
				
				stubFor(get(urlEqualTo("/api/products"))
							.willReturn(aResponse()
							.withStatus(200)
							.withHeader("Content-Type", "application/jSON")
							.withBodyFile("product.json")

						));	
		}
		
	//*******************************************Creating mock for POST CALLS*******************************************

	public static void createDummayUser() {
		stubFor(post(urlEqualTo("/api/users"))
				.withHeader("Content-Type", equalTo("application/json"))
				.willReturn(aResponse()
						.withStatus(201)
						.withHeader("Content-Type", "application/json")
						.withStatusMessage("User is created")
						.withBody("{\"id\":1,\"name\":\"Tom\"}")
				));
				
		
				
	}
	
	public static void createDummayUserWithJsonFile() {
		stubFor(post(urlEqualTo("/api/users"))
				.withHeader("Content-Type", equalTo("application/json"))
				.willReturn(aResponse()
						.withStatus(201)
						.withHeader("Content-Type", "application/jSON")
						.withStatusMessage("User is created")
						.withBodyFile("user.json")
				));
				
		
				
	}

	//*******************************************Creating mock for DELETE CALLS*******************************************
	
	public static void deletDummyUser() {
		stubFor(delete(urlEqualTo("/api/users/1"))
				.willReturn(aResponse()
				.withStatus(204)
				.withStatusMessage("User is deleted")
				));
	}
	
	
	
}
