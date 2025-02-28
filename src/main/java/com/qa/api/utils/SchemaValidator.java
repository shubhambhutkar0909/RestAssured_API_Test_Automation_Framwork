package com.qa.api.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class SchemaValidator {

	public static boolean validateSchema(Response response, String scehmaFileName) {
		try {
			response.then().assertThat()
					.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(scehmaFileName));
			System.out.println("Schema Validation is Passed!!");
			return true;
		} catch (Exception e) {
			System.err.println("Scehma validation is failed" + e.getMessage());
			return false;
		}
	}
}
