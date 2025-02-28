package com.qa.api.mocking.tests;

import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.mocking.APIMocks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

import java.util.Map;

public class MockGetUserAPITest extends BaseTest{
	
	
	@Test
	public void getDummyUserTest() {
		
		APIMocks.getDummuyUser();
		
		Response response = restClient.get(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
		response.then()
				.assertThat()
					.statusCode(200)
					.body("name", equalTo("Tom"));
				
		
	}
	
	
	@Test
	public void getDummyUserWithQueryParamTest() {
		
		APIMocks.getDummuyUserWithQueryParam();
		Map<String,String> queryParam= Map.of("name","VshubhamB");
		Response response = restClient.get(BASE_URL_LOCALHOST_PORT, "/api/users", queryParam, null, AuthType.NO_AUTH, ContentType.ANY);
		response.then()
				.assertThat()
					.statusCode(200)
					.body("name", equalTo("VshubhamB"));
				
		
	}
	
	@Test
	public void getDummyUserWithJsonFileTest() {
		
		APIMocks.getDummuyUserWithJsonFile();
		
		Response response = restClient.get(BASE_URL_LOCALHOST_PORT, "/api/users", null, null, AuthType.NO_AUTH, ContentType.ANY);
		response.then()
				.assertThat()
					.statusCode(200)
					.body("name", equalTo("VshubhamB"));
				
		
	}
	
	
}
