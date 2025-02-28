package com.qa.contacts.api.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.ContactsCredentials;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContactsAPITest extends BaseTest{
	
	private String tokenId;
	
	@BeforeMethod
	public void getToken(){
		ContactsCredentials contactCreds= ContactsCredentials.builder()
				.email("xifakak300@envoes.com")
				.password("Automation@05")
				.build();

		Response response = restClient.post(BASE_URL_CONTACTS,CONTACTS_USER_LOGIN_ENDPOINT, contactCreds, null, null, AuthType.NO_AUTH, ContentType.JSON);
		tokenId = response.jsonPath().getString("token");
		System.out.println("NEW TOKEN ID::==> "+ tokenId);
		ConfigManager.set("contacts_Bearer_Token", tokenId);
	}
	
	@Test
	public void getContactsTest() {
		restClient.get(BASE_URL_CONTACTS,CONTACTS_ALL_ENDPOINT, null, null, AuthType.CONTACTS_BEARER_TOKEN, ContentType.JSON);
	}
	
}
