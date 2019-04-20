package com.rohan90.majdoor.api.utils.rest;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RestAssuredWrapper {
	public static RequestSpecification given() {
		RequestSpecification client = RestAssured.given();
		client.contentType("application/json").log().all();
		return client;
	}
}
