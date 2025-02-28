package com.qa.api.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest extends BaseTest {
	
	
	@DataProvider
	public Object[][] updateUserData() {
		return new Object[][] {
			{"Shubham","Male","inactive", "active"},
			{"Varada","Female","inactive","active"},
			{"Anuja","Female","active","inactive"}
		};
	}

	@Test(dataProvider = "updateUserData")
	public void updateUsersBuilderTest(String name, String gender, String status, String UpdatedStatus) {
		
		User user = User.builder()
						.name(name)
						.email(StringUtility.getRandomEmailId())
						.status(status)
						.gender(gender)
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
		user.setStatus(UpdatedStatus);
		System.out.println("Updated Name is::==>"+user.getName());
		
		//PUT CALL : Update the same newly create user using put call
		Response responsePut = restClient.put(BASE_URL_GO_REST,"/public/v2/users/"+userId, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePut.getStatusCode(), 200);
		Assert.assertEquals(responsePut.jsonPath().getString("id"), userId);
		Assert.assertEquals(responsePut.jsonPath().getString("email"), user.getEmail());
		Assert.assertEquals(responsePut.jsonPath().getString("status"), user.getStatus());
		
	}
	
}
