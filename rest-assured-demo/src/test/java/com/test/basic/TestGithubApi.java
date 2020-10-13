package com.test.basic;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;

/**
 * Rest-Assured请求处理，发送不同的Request
 *
 * @author jingLv
 * @date 2020/10/13
 */
class TestGithubApi {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.authentication = oauth2("c37acfc546c8be44948e702171d657ad39681795");
    }

    /**
     * 发送get请求
     */
    @Test
    void testGetRequest() {
        given()
                .log().all()
                .when()
                .get("/user/repos")
                .then()
                .log().status()
                .statusCode(200);
    }

    /**
     * 发送post请求 -- 创建Hello-Word的repos
     */
    @Test
    void testPostRequest() {
        String postBody = "{\n" +
                "  \"name\": \"Hello-World\",\n" +
                "  \"description\": \"This is your hello repository\",\n" +
                "  \"homepage\": \"https://github.com\",\n" +
                "  \"private\": false,\n" +
                "  \"has_issues\": true,\n" +
                "  \"has_projects\": true,\n" +
                "  \"has_wiki\": true\n" +
                "}";
        given()
                .log().all()
                .body(postBody)
                .when()
                .post("/user/repos")
                .then()
                .log().status()
                .statusCode(201);
    }

    /**
     * 发送Patch请求 -- 修改repos
     */
    @Test
    void testPatchRequest() {
        String editBody = "{\n" +
                "  \"name\": \"Hello-Edit\",\n" +
                "  \"description\": \"This is your edit repository\",\n" +
                "  \"homepage\": \"https://github.com\",\n" +
                "  \"private\": false,\n" +
                "  \"has_issues\": false,\n" +
                "  \"has_projects\": false,\n" +
                "  \"has_wiki\": false\n" +
                "}";
        given()
                .log().all()
                .pathParam("owner", "jinglv")
                .pathParam("repo", "Hello-World")
                .body(editBody)
                .when()
                .patch("/repos/{owner}/{repo}")
                .then()
                .log().status()
                .statusCode(200);
    }

    /**
     * 发送Put请求 -- 修改topic
     * 根据接口文档说明要指定媒体类型
     */
    @Test
    void testPutRequest() {
        String putBody = "{\n" +
                "  \"names\": [\n" +
                "    \"rest-assured\"\n" +
                "  ]\n" +
                "}";
        given()
                .log().all()
                .pathParam("owner", "jinglv")
                .pathParam("repo", "Hello-Edit")
                .header("Accept", "application/vnd.github.mercy-preview+json")
                .body(putBody)
                .when()
                .put("/repos/{owner}/{repo}/topics")
                .then()
                .log().status()
                .statusCode(200);
    }

    /**
     * 发送delete请求 -- 删除repos
     * 根据接口文档说明要指定媒体类型
     */
    @Test
    void testDeleteRequest() {
        given()
                .log().all()
                .pathParam("owner", "jinglv")
                .pathParam("repo", "Hello-Edit")
                .when()
                .delete("/repos/{owner}/{repo}")
                .then()
                .log().status()
                .statusCode(204);
    }
}
