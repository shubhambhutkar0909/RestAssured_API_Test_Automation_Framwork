package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.SchemaValidator;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ProductsAPISchemaTest extends BaseTest{
	
	@Test
	public void productsAPISchemaTest() {
		
		Response response =restClient.get(BASE_URL_PRODUCT,FAKESTORE_PRODUCTS_ALL_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		
		Assert.assertEquals(SchemaValidator.validateSchema(response, "schema/product-schema.json"),true);
	}
	
	
}
