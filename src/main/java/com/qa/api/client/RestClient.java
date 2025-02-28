package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.FrameworkExceptions;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestClient {
	
	
	private ResponseSpecification respSpec200 =  expect().statusCode(200);
	private ResponseSpecification respSpec200or404 =  expect().statusCode(anyOf(equalTo(200), equalTo(404)));
	private ResponseSpecification respSpec201 =  expect().statusCode(201);
	private ResponseSpecification respSpec200or201 =  expect().statusCode(anyOf(equalTo(200), equalTo(201)));
	private ResponseSpecification respSpec200or201or404 =  expect().statusCode(anyOf(equalTo(200),equalTo(201), equalTo(404)));

	private ResponseSpecification respSpec204 =  expect().statusCode(204);
	private ResponseSpecification respSpec400 =  expect().statusCode(400);
	private ResponseSpecification respSpec401 =  expect().statusCode(401);
	private ResponseSpecification respSpec404 =  expect().statusCode(404);
	private ResponseSpecification respSpec422 =  expect().statusCode(422);	
	private ResponseSpecification respSpec500 =  expect().statusCode(500);

	

	private RequestSpecification setUpRequest(String baseUrl,AuthType authType, ContentType contentType) {

		RequestSpecification request = RestAssured.given().log().all()
												.baseUri(baseUrl)
												.contentType(contentType)
												.accept(contentType);

		switch (authType) {

		case BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("bearerToken"));
			break;
		
		case CONTACTS_BEARER_TOKEN:
			request.header("Authorization", "Bearer " + ConfigManager.get("contacts_Bearer_Token"));
			break;
			
		case OAUTH2:
			request.header("Authorization", "Bearer " + genrateOAuth2Token());
			break;

		case BASIC_AUTH:
			request.header("Authorization", "Basic " + generateBasicAuthToken());
			break;

		case API_KEY:
			request.header("x-api-key", ConfigManager.get("apiKey"));
			break;
			
		case NO_AUTH:
			System.out.println("Auth is not required");
			break;
			
		default:
			System.out.println("This Auth is not supported..Please add correct AuthType..");
			throw new FrameworkExceptions("NO AUTH SUPPORTED");
			
		}
		return request;

	}

	public String genrateOAuth2Token() {
		return RestAssured.given()
								.formParam("client_id", ConfigManager.get("clientId"))
								.formParam("client_secret", ConfigManager.get("clientSecret"))
								.formParam("grant_type", ConfigManager.get("grantType"))
								.post(ConfigManager.get("tokenUrl"))
								 .then()
								.extract().path("access_token");
	}
	
	/**
	 * This method can be used to get Base64 encoded string for BASIC_AUTH
	 * @return
	 */
	private String generateBasicAuthToken() {
	
		String credentials = ConfigManager.get("basicUserName") + ":" +ConfigManager.get("basicPassword");
		return Base64.getEncoder().encodeToString(credentials.getBytes());
		
	}
	
	
	//******************************* CRUD OPRATION METHODS *******************************//
	
	/**
	 * This Method will be used for calling Get Method
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return : It Returns the Response
	 */
	public Response get(String baseUrl, String endPoint, Map<String,String> queryParams,Map<String,String> pathParams, 
					AuthType authType, ContentType contentType) {
		
		
		RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);	
		 
		 applyParams(request,queryParams,pathParams);
		 
		 Response response = request.get(endPoint)
							 		.then()
							 		.spec(respSpec200or201or404)
							 		.extract()
							 		.response();
		 response.prettyPrint();
		 return response;
		 
	}
	
	/**
	 * This Method will be used for Post API's call
	 * @param <T>
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return : It Returns the Post API Response
	 */
	public <T>Response post(String baseUrl,String endPoint, T body, Map<String,String> queryParams,Map<String,String> pathParams, 
			AuthType authType, ContentType contentType) {
		
		 RequestSpecification request = setUpAuthAndContentType(baseUrl,authType, contentType);	
		 
		 applyParams(request,queryParams,pathParams);
		 
		 Response response = request.body(body)
				 					.post(endPoint)
				 					.then()
				 					.spec(respSpec200or201)
				 					.extract()
				 					.response();
		 
		 response.prettyPrint();
		 return response;
		 
	 }
	
	/**
	 * This Method will be used to call Post APIs with File Body type
	 * @param endPoint
	 * @param file
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the post api response
	 */
	public Response post(String baseUrl, String endPoint, File file, Map<String,String> queryParams,Map<String,String> pathParams, 
			AuthType authType, ContentType contentType) {
		
		 RequestSpecification request = setUpAuthAndContentType(baseUrl,authType, contentType);	
		 
		 applyParams(request,queryParams,pathParams);
		 
		 Response response = request.body(file)
				 					.post(endPoint)
				 					.then()
				 					.spec(respSpec201)
				 					.extract()
				 					.response();
		 
		 response.prettyPrint();
		 return response;
		 
	 }
	
	/**
	 * This will be used for Put APIs.
	 * @param <T>
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T>Response put(String baseUrl, String endPoint, T body, Map<String,String> queryParams,Map<String,String> pathParams, 
			AuthType authType, ContentType contentType) {
		
		 RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);	
		 
		 applyParams(request,queryParams,pathParams);
		 
		 Response response = request.body(body)
				 					.put(endPoint)
				 					.then()
				 					.spec(respSpec200)
				 					.extract()
				 					.response();
		 
		 response.prettyPrint();
		 return response;
		 
	 }
	
	/**
	 * This method will be used for Patch APIs
	 * @param <T>
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T>Response patch(String baseUrl,String endPoint, T body, Map<String,String> queryParams,Map<String,String> pathParams, 
			AuthType authType, ContentType contentType) {
		
		 RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);	
		 
		 applyParams(request,queryParams,pathParams);
		 
		 Response response = request.body(body)
				 					.patch(endPoint)
				 					.then()
				 					.spec(respSpec200)
				 					.extract()
				 					.response();
		 
		 response.prettyPrint();
		 return response;
		 
	 }
	/**
	 * This Method will be used for Delete APIs.
	 * @param <T>
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
	public <T>Response delete(String baseUrl, String endPoint, Map<String,String> queryParams,Map<String,String> pathParams, 
			AuthType authType, ContentType contentType) {
		
		 RequestSpecification request = setUpAuthAndContentType(baseUrl, authType, contentType);	
		 
		 applyParams(request,queryParams,pathParams);
		 
		 Response response = request.delete(endPoint)
				 					.then()
				 					.spec(respSpec204)
				 					.extract()
				 					.response();
		 
		 response.prettyPrint();
		 return response;
		 
	 }
	
	
	
	
	//*****INTERANL METHODS*****I//
	private RequestSpecification setUpAuthAndContentType(String baseUrl,AuthType authType, ContentType contentType) {
		return  setUpRequest(baseUrl, authType, contentType);
	}
		 
	private void applyParams( RequestSpecification request,Map<String,String> queryParams,Map<String,String> pathParams) {
	 
		 if(queryParams != null) {
			 request.queryParams(queryParams);
		 }
		 if(pathParams != null) {
			 request.pathParams(pathParams);
		 }
		
	}
	
	

}
