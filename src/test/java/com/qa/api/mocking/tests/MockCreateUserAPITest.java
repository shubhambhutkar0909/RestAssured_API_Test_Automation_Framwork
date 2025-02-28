package com.qa.api.mocking.tests;

import org.testng.annotations.Test;import com.github.tomakehurst.wiremock.matching.EqualToJsonPattern;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class MockCreateUserAPITest extends BaseTest {
	
	@Test
	public void createMockUserTest() {
		APIMocks.createDummayUser();
		
		String dummyJson = "{\"name\":\"Tom\"}";
		Response response = restClient.post(BASE_URL_LOCALHOST_PORT, "/api/users", dummyJson, null, null, AuthType.NO_AUTH, ContentType.JSON);
		response.then()
				.assertThat()
					.statusCode(201)
						.statusLine(equalTo("HTTP/1.1 201 User is created"))
						.body("id", equalTo(1));
	}
	
	
	@Test
	public void createMockUserWithJsonFileTest() {
		APIMocks.createDummayUserWithJsonFile();
		
		String dummyJson = "{\"name\":\"VshubhamB\"}";
		Response response = restClient.post(BASE_URL_LOCALHOST_PORT, "/api/users", dummyJson, null, null, AuthType.NO_AUTH, ContentType.JSON);
		response.then()
				.assertThat()
					.statusCode(201)
						.statusLine(equalTo("HTTP/1.1 201 User is created"))
						.body("id", equalTo(101));
	}

}
