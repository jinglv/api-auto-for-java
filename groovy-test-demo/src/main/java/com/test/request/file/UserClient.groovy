package com.test.request.file

import static io.restassured.RestAssured.given

/**
 *
 *
 * @author jingLv* @date 2020/08/06
 *
 */
class UserClient {
    static def addUserWithFile(File file) {
        def res = given().baseUri("http://localhost:9090")
                .header("Content-Type", "application/json;charset=UTF-8")
                .when()
                .body(file) //body参数中传入File对象
                .post("/api/addUserDetails")
                .then().assertThat().statusCode(200)
                .extract().response().getBody().asString(); //获取接口的response body
        res
    }

    static def addUserWithString(String body) {
        def res = given().log().all().baseUri("http://localhost:9090")
                .header("Content-Type", "application/json;charset=UTF-8")
                .when()
                .body(body) //body参数中传入接口的request body字符串
                .post("/api/addUserDetails")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().getBody().asString(); //获取接口的response body
        res
    }
}
