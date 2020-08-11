package com.test.response.client

import static io.restassured.RestAssured.given

/**
 *
 *
 * @author jingLv* @date 2020/08/10
 *
 */
class ResumeClient {

    static def getResumeDetails() {
        def res = given().baseUri("http://localhost:9090")
                .when()
                .get("/api/getResume")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response().getBody().asString()
        res
    }

    static def getResumeDetailHeader() {
        given().baseUri("")
                .when()
                .get("")
                .then().assertThat().statusCode(200)
                .extract().response().getHeader("content-type") //获取header中content-type值
    }
}
