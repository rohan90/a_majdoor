package com.rohan90.majdoor.it.ping;

import com.rohan90.majdoor.api.ApiConstants;
import com.rohan90.majdoor.api.utils.rest.RestAssuredWrapper;
import com.rohan90.majdoor.utils.BaseAPITest;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;

public class PingTest extends BaseAPITest {
    @Test
    public void shouldShowSuccsfullStatusAndMessageOnPing() {
        RestAssuredWrapper
                .given()
                .when()
                .get(ApiConstants.Ping.BASE+ApiConstants.Ping.INDEX)
                .then()
                .statusCode(200)
                .body("data", containsString("name=majdoor")); //possibly extract project name out to constants
    }
}
