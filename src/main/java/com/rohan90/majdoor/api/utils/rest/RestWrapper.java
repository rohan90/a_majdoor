package com.rohan90.majdoor.api.utils.rest;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestWrapper {

    public static Response doPost(String url, Object payload) {
        return RestAssuredWrapper.given()
                .body(payload)
                .post(url);
    }

    public static Response doGet(String url) {
        return RestAssuredWrapper.given()
                .get(url);
    }

    public static RequestSpecification withHeaders(Map<String, String> headers) {
        return RestAssuredWrapper.given().headers(headers);
    }

    public static RequestSpecification given(){
        return RestAssuredWrapper.given();
    }
}
