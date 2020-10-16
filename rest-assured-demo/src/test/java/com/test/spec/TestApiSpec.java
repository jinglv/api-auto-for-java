package com.test.spec;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.preemptive;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author jingLv
 * @date 2020/10/14
 */
public class TestApiSpec {

    static RequestSpecification requestSpecification;
    static ResponseSpecification responseSpecification;

    /**
     * 设置RestAssured全局配置
     */
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.authentication = preemptive().basic("lvjing0705@126.com", "lvjing5284");
        RestAssured.basePath = "/repos";

        // 请求参数构造模板
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addParam("", "");
        requestSpecBuilder.addParam("", "");
        requestSpecification = requestSpecBuilder.build();

        // 响应构造模板
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);
        responseSpecBuilder.expectBody("owner.login", equalTo("jinglv"));
        responseSpecification = responseSpecBuilder.build();
    }

    /**
     * 使用模板功能来简化测试用例编写
     */
    @Test
    void testSpecification() {
        given()
                .log().all()
                .spec(requestSpecification)
                .when()
                .get("/user/repos")
                .then()
                .log().all()
                .spec(responseSpecification);
    }

    /**
     * 利用Filter过滤器实现request请求的修改
     */
    @Test
    void testRequestFilter() {
        given()
                .filter(new Filter() {
                    @Override
                    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
                        requestSpec.pathParam("user", "mimi");
                        return ctx.next(requestSpec, responseSpec);
                    }
                })
                .log().all()
                .spec(requestSpecification)
                .when()
                .get("/user/repos")
                .then()
                .log().all()
                .spec(responseSpecification);
    }

    @Test
    void testResponseFilter() {
        Response response = given()
                .log().all()
                .spec(requestSpecification)
                .when()
                .get("/user/repos");

        Response newResponse = new ResponseBuilder().clone(response)
                .setBody("这是一个修改Response的测试demo").build();

        newResponse.then().log().all().spec(responseSpecification);
    }
}
