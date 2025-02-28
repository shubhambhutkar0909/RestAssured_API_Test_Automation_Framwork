package com.qa.products.api.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.utils.JsonPathValidator;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductAPIWithJsonPathValidatorTest extends BaseTest {

	@Test
	public void geAllProductsForJsonPathAPITest() {
		Response response = restClient.get(BASE_URL_PRODUCT,FAKESTORE_PRODUCTS_ALL_ENDPOINT, null, null, AuthType.NO_AUTH, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);

		List<Number> prices = JsonPathValidator.readList(response, "$[?(@.price > 50)].price");
		System.out.println("Prices::=>" + prices);

		List<Number> ids = JsonPathValidator.readList(response, "$[?(@.price > 50)].id");
		System.out.println("Ids::=>" + ids);

		List<Double> rates = JsonPathValidator.readList(response, "$[?(@.price > 50)].rating.rate");
		System.out.println("Rates::=>" + rates);

		List<Integer> ratesCount = JsonPathValidator.readList(response, "$[?(@.price > 50)].rating.rate");
		System.out.println("Rate Counts::=>" + ratesCount);

		List<Map<String, Object>> jeweleryList = JsonPathValidator.readListOfMaps(response,
				"$[?(@.category == 'jewelery')].['title','price']");
		System.out.println(jeweleryList.size());

		for (Map<String, Object> product : jeweleryList) {
			String title = (String) product.get("title");
			Number price = (Number) product.get("price");
			System.out.println("Title::" + title);
			System.out.println("Price::" + price);
			System.out.println("=========================");
		}

		System.out.println("=========================");

		Double minPrice = JsonPathValidator.read(response, "min($[*].price)");
		System.out.println("Min Price::=>" + minPrice);

	}

}
