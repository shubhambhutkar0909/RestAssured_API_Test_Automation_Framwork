package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtility;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class UserAPISchemaTest extends BaseTest{
	
	@Test
	public void userAPISchemaTest() {
		
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

		Response responseGet = restClient.get(BASE_URL_GO_REST, "/public/v2/users/"+userId, null, null, AuthType.BEARER_TOKEN,ContentType.JSON);

		Assert.assertEquals(SchemaValidator.validateSchema(response, "schema/user-schema.json"), true);
						
						
	}

}
