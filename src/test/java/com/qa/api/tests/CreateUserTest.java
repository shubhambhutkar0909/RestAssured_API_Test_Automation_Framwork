package com.qa.api.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTest extends BaseTest {
	
	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{"Shubham","Male","active"},
			{"Varada","Female","active"},
			{"Anuja","Female","active"}
		};
	}
	
	
	@Test(dataProvider = "getUserData")
	public void createUsersTest(String name, String gender, String status) {
		
		User  user = new User(null,name,StringUtility.getRandomEmailId(),gender,status);
		Response response = restClient.post(BASE_URL_GO_REST,"/public/v2/users",user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	
		Assert.assertEquals(response.getStatusCode(), 201);
	
	}
	
	@Test(dataProvider = "getUserData")
	public void createUsersBuilderTest(String name, String gender, String status) {
		
		User user = User.builder()
						.name(name)
						.email(StringUtility.getRandomEmailId())
						.status(status)
						.gender(gender)
						.build();
				
		
		Response response = restClient.post(BASE_URL_GO_REST, GOREST_USERS_ALL_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	
		Assert.assertEquals(response.getStatusCode(), 201);
		
		//Fetching newly created userId
		String userId = response.jsonPath().getString("id");
		System.out.println("User ID is:==>"+userId);
	
		//GET CALL
		Response responseGet = restClient.get(BASE_URL_GO_REST,"/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responseGet.getStatusCode(), 200);
		Assert.assertEquals(responseGet.jsonPath().getString("id"), userId);
		Assert.assertEquals(responseGet.jsonPath().getString("name"), user.getName());
		Assert.assertEquals(responseGet.jsonPath().getString("email"), user.getEmail());
		
	}
	
	@Test(enabled=false)
	public void createUsersUsingJsonFileTest() {
		
		File userJsonFile = new File(System.getProperty("user.dir")+ "/src/test/resources/jsons/user.json");
		Response response = restClient.post(BASE_URL_GO_REST,"/public/v2/users",userJsonFile, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	
		Assert.assertEquals(response.getStatusCode(), 201);
	
	}
	
	

}
