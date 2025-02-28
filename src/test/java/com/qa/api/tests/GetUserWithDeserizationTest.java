package com.qa.api.tests;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserWithDeserizationTest extends BaseTest{

	
	@Test
	public void getAllUsersTest() {
		
		
		Response response = restClient.get(BASE_URL_GO_REST,GOREST_USERS_ALL_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.getStatusCode(), 200);
	
	}
}
