package com.test.response;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Response消息的获取和解析
 *
 * @author jingLv
 * @date 2020/10/13
 */
class TestGithubApiResponse {

    /**
     * 设置RestAssured全局配置
     */
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.authentication = oauth2("c37acfc546c8be44948e702171d657ad39681795");
    }

    @Test
    void getParseResponse() {
        // 获取接口的response
        Response response = given().
                pathParam("owner", "jinglv").
                pathParam("repo", "api-auto-for-java").
                when().
                get("/repos/{owner}/{repo}");
        String resBody = response.getBody().asString();
        String resBodyInfo = response.getBody().toString();
        System.out.println("消息体：" + resBody);
        System.out.println("消息体对象：" + resBodyInfo);

        // 响应消息体json格式化输出
        response.getBody().prettyPrint();

        System.out.println("响应的头信息" + response.getHeaders());
        System.out.println("响应状态：" + response.getHeader("status"));
        System.out.println("cookie信息：" + response.getCookies());
        System.out.println("响应值：" + response.getStatusLine());
        System.out.println("响应码：" + response.getStatusCode());
        System.out.println("响应的内容类型：" + response.getContentType());
        System.out.println("接口响应时间(ms)：" + response.getTime());
        System.out.println("接口响应时间(s)：" + response.getTimeIn(TimeUnit.SECONDS));

        // Rest-Assured jsonPath的使用
        JsonPath jsonPath = new JsonPath(resBody);
        System.out.println("repo ID:" + jsonPath.get("id"));
        // 设置根节点为owner
        jsonPath.setRoot("owner");
        System.out.println("owner ID:" + jsonPath.get("id"));

        // Junit5的断言方式，存在多个断言，其中一个断言失败，则断言失败后续的代码都不会执行
        assertTrue(response.getHeader("status").contains("OK"));
        assertEquals(jsonPath.getInt("id"), 12013318);

        // 把多个断言方法放在assertAll中，对多个断言的批量校验
        assertAll("this is a group assert:",
                () -> assertTrue(response.getHeader("status").contains("OK")),
                () -> assertEquals(jsonPath.getInt("id"), 12013318));
    }
}
