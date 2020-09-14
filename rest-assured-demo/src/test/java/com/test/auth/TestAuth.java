package com.test.auth;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * auth().preemptive().oauth2("") 显示的将鉴权token信息在header中携带 / 直接指定header("Authentication","")
 * auth().preemptive().basic("user","password") 直接指定用户名和密码
 *
 * @author jingLv
 * @date 2020/09/09
 */
public class TestAuth {

    @Test
    void testAuthOauth2() {
        given().baseUri("http://localhost:9090")
                .auth().preemptive().oauth2("123456")
                .when()
                .get("/api/oauth/auth")
                .then()
                .log().body()
                .extract().response();
    }

    @Test
    void testAuthBasic() {
        given().baseUri("http://localhost:9090")
                .auth().preemptive().basic("admin", "123456")
                .when()
                .get("/api/basic/auth")
                .then()
                .log().body();
    }
}
