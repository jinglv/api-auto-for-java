package com.test.schema;

import io.restassured.RestAssured;
import io.restassured.config.HeaderConfig;
import io.restassured.config.LogConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * JsonSchema进行响应断言
 *
 * @author jingLv
 * @date 2020/10/14
 */
public class TestJsonSchema {
    /**
     * 设置RestAssured全局配置
     */
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.authentication = preemptive().basic("lvjing0705@126.com", "lvjing5284");
        RestAssured.basePath = "/repos";
    }

    @Test
    void validateJsonSchema() {
        String schemaString = "";
        given()
                .log().all()
                .when()
                .get("/user/repos")
                .then()
                .log().all()
                .assertThat().body(matchesJsonSchemaInClasspath("repo-schema.json"));
        //.assertThat().body(matchesJsonSchema(schemaString));
    }

    /**
     * header设置，有两个同名的，在请求的时候，需要将后一个header替换为前一个header
     */
    @Test
    void headerConfig() {
        RestAssured.config = RestAssured.config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("test"));
        given()
                .log().all()
                .pathParam("owner", "jinglv")
                .pathParam("repo", "Hello-Edit")
                .header("test", "aaaa")
                .header("test", "bbb")
                .when()
                .delete("{owner}/{repo}")
                .then()
                .log().status()
                .statusCode(204);
    }

    /**
     * Log配置实例
     */
    @Test
    void logConfig() {
        // 配置管理响应消息的格式化输出，false是关闭
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(false));
        given()
                .log().all()
                .pathParam("owner", "jinglv")
                .pathParam("repo", "Hello-Edit")
                .when()
                .delete("{owner}/{repo}")
                .then()
                .log().status()
                .statusCode(204);
    }
}
