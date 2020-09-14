package com.test;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * @author jingLv
 * @date 2020-07-01 2:38 下午
 */
public class TestApi {

    @Test
    public void testGetBook() {
        given().
                when().
                get("http://localhost:9090/api/getBook/abc").
                then()
                .log().toString();
    }
}
