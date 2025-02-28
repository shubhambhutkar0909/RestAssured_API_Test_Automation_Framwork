package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PatchUserTest extends BaseTest{
	
	@Test
	public void patchBuilderTest() {
		
		User user = User.builder()
						.name("vsb")
						.email(StringUtility.getRandomEmailId())
						.status("active")
						.gender("female")
						.build();
				
		
		Response response = restClient.post(BASE_URL_GO_REST,GOREST_USERS_ALL_ENDPOINT,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	
		Assert.assertEquals(response.getStatusCode(), 201);
		
		//Fetching newly created userId
		String userId = response.jsonPath().getString("id");
		System.out.println("User ID is:==>"+userId);
	
		//GET CALL: Fetching newly created user to validate
		Response responseGet = restClient.get(BASE_URL_GO_REST,"/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		Assert.assertEquals(responseGet.jsonPath().getString("name"), user.getName());
		Assert.assertEquals(responseGet.jsonPath().getString("email"), user.getEmail());
		
		//Adding details to update with put call
		user.setEmail(StringUtility.getRandomEmailId());
		
		//PATCH CALL : Update the same newly create user using patch call
		Response responsePatch = restClient.patch(BASE_URL_GO_REST,"/public/v2/users/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePatch.getStatusCode(), 200);
		Assert.assertEquals(responsePatch.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePatch.jsonPath().getString("email"), user.getEmail());
		
	}
	


}
