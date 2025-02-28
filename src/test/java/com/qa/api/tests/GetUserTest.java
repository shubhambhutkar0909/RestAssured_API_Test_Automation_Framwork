package com.qa.api.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserTest extends BaseTest{

	@Test
	public void getUsersTest() {
		
		Map<String,String> queryParams = new HashMap<String,String>();
		queryParams.put("name", "naveen");
		queryParams.put("status", "active");
		
		Response response = restClient.get(BASE_URL_GO_REST,GOREST_USERS_ALL_ENDPOINT, queryParams, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	
		Assert.assertEquals(response.getStatusCode(), 200);
	
	}
	
	@Test(enabled=false)
	public void getSingleUserTest() {
		Response response = restClient.get(BASE_URL_GO_REST,"/public/v2/users/7712697", null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
}
