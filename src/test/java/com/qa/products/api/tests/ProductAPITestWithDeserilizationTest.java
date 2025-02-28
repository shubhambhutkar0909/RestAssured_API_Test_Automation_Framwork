package com.qa.products.api.tests;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.Product;
import com.qa.api.utils.JsonUtil;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPITestWithDeserilizationTest extends BaseTest{
	
	@Test
	public void getAllProductsAPITest() {
		Response response = restClient.get(BASE_URL_PRODUCT,FAKESTORE_PRODUCTS_ALL_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		Product[] product =JsonUtil.deseralize(response, Product[].class);
		
		System.out.println(Arrays.toString(product));
		
		for(Product p: product) {
			System.out.println("ID::" +p.getId());
			System.out.println("Title::" +p.getTitle());
			System.out.println("Price::" +p.getPrice());
			System.out.println("Rate::" +p.getRating().getRate());
			System.out.println("Count::" +p.getRating().getCount());
			
			System.out.println("===================================");

		}
	}
	
}
