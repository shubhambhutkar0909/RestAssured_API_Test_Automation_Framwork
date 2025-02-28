package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest extends BaseTest{
	
	@Test
	public void deleteBuilderTest() {
		
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
		
	
		//Delete CALL : Delete the newly create user
		Response responseDelete = restClient.delete(BASE_URL_GO_REST,"/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseDelete.getStatusCode(), 204);
		
		//GET CALL: Verifying after deleting the user is actually deleted
		Response afterDeleteResponseGet = restClient.get(BASE_URL_GO_REST,"/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(afterDeleteResponseGet.getStatusCode(), 404);
		Assert.assertEquals(afterDeleteResponseGet.jsonPath().getString("message"), "Resource not found");
		
	}
	


	
	
}
