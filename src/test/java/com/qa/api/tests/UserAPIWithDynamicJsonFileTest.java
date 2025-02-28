package com.qa.api.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.StringUtility;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserAPIWithDynamicJsonFileTest extends BaseTest {

	@Test
	public void createUserWithJsonFileTest() {

		String jsonFilePath = "src/test/resource/jsons/user.json";

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode userNode = mapper.readTree(Files.readAllBytes(Paths.get(jsonFilePath)));
			
			//Creating a new unique email id to update in JSON
			String uniqueEmail = StringUtility.getRandomEmailId();
			
			//Updating the emailId
			ObjectNode obj = ((ObjectNode)userNode);
			obj.put("email", uniqueEmail);
			
			//Converting Json Node to json String
			String updatedJsonString = mapper.writeValueAsString(userNode);
			System.out.println("Updated Json with new Email"+updatedJsonString);
			
			Response response = restClient.post(BASE_URL_GO_REST,GOREST_USERS_ALL_ENDPOINT,  updatedJsonString, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			Assert.assertEquals(response.statusCode(), 201);
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
