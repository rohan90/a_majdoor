package com.rohan90.majdoor.utils;

import com.rohan90.majdoor.api.ApiConstants;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseAPITest {
    //    @Value("${server.port}")
    @LocalServerPort
    private String port;

    @Value("${server.host}")
    private String baseHost;

    private static String basePath = ApiConstants.ENTRY_POINT;

    @Before
    public void setup() {
        if (port == null) {
            throw new IllegalStateException("server.port not set");
        }

        if (baseHost == null) {
            throw new IllegalStateException("server.host not set");
        }


        RestAssured.port = Integer.valueOf(port);
        RestAssured.basePath = basePath;
        RestAssured.baseURI = baseHost;

    }

    @Test
    public void pseudoTest() {
        //dummy test to prevent
        //initializationError »  No runnable methods
        // todo figure out a way to prevent this or move BaseApi out of TestScope
    }
}
