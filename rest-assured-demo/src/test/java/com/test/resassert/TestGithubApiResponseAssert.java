package com.test.resassert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jingLv
 * @date 2020/10/13
 */
class TestGithubApiResponseAssert {

    static Response response;

    /**
     * 设置RestAssured全局配置
     */
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.authentication = oauth2("c37acfc546c8be44948e702171d657ad39681795");

        // 获取接口的response
        response = given().
                pathParam("owner", "jinglv").
                pathParam("repo", "api-auto-for-java").
                when().
                get("/repos/{owner}/{repo}");
    }

    @Test
    void getParseResponse() {
        // Rest-Assured jsonPath的使用
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        System.out.println("repo ID:" + jsonPath.get("id"));
        // 设置根节点为owner
        jsonPath.setRoot("owner");
        System.out.println("owner ID:" + jsonPath.get("id"));

        // Junit是的断言方式，存在多个断言，其中一个断言失败，则断言失败后续的代码都不会执行
        // 使用Junit自带断言完成响应校验
        assertTrue(response.getHeader("status").contains("OK"));
        assertEquals(jsonPath.getInt("id"), 12013318);

        // 把多个断言方法放在assertAll中，对多个断言的批量校验
        assertAll("this is a group assert:",
                () -> assertTrue(response.getHeader("status").contains("OK")),
                () -> assertEquals(jsonPath.getInt("id"), 12013318));
    }

    /**
     * 通过Rest-Assured的内建校验方法实现验证
     */
    @Test
    void validateStatus() {
        response.then().statusCode(200);
    }

    /**
     * 断言响应header
     */
    @Test
    void validateHeader() {
        response.then().header("Content-Type", containsString("json"));
    }

    /**
     * 断言响应时间
     */
    @Test
    void validateResponseTime() {
        response.then().time(lessThan(3000L));
    }

    /**
     * 断言响应body
     */
    @Test
    void validateBody() {
        response.then().body("owner.login", equalTo("jinglv"));
    }
}
