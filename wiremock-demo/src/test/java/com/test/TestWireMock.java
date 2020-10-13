package com.test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

/**
 * 在Junit5中WireMock的使用方法
 *
 * @author jingLv
 * @date 2020/09/09
 */
public class TestWireMock {
    // 创建WireMockServer的实例
    static WireMockServer wireMockServer = new WireMockServer(8088, 8089);

    @BeforeAll
    static void setUp() {
        // 启动WireMockServer
        wireMockServer.start();
        // 设置RestAssured
        RestAssured.baseURI = "http://localhost:8088";
    }

    @AfterEach
    public void teardown(TestInfo info) {
        System.out.println("------------" + info.getDisplayName() + " finished!-------");
    }

    @Test
    @DisplayName("设置基本监听规则")
    void testMonitor01() {
        // 设置Mock响应规则
        wireMockServer.stubFor(get(urlEqualTo("/api/book/abc"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("test", "api")
                        .withBody("successful"))
        );

        // 请求已Mock响应规则接口
        given()
                .when()
                .get("/api/book/abc")
                .then()
                .log().all();
    }

    /**
     * 这是个一个简化写法
     */
    @Test
    @DisplayName("设置响应简化规则")
    void testMonitor02() {
        // 设置Mock响应规则
        wireMockServer.stubFor(get(urlEqualTo("/api/book/abc"))
                .willReturn(ok("successful"))
        );

        // 请求已Mock响应规则接口
        given()
                .when()
                .get("/api/book/abc")
                .then()
                .log().all();
    }

    @Test
    @DisplayName("重定向")
    void testRedirect() {
        wireMockServer.stubFor(post(urlEqualTo("/api/book/redirect"))
                .willReturn(temporaryRedirect("/api/book/newPlace"))
        );

        given()
                .when()
                .post("/api/book/redirect")
                .then()
                .log().all();
    }

    @Test
    @DisplayName("优先级规则的应用")
    void testPriority() {
        // 匹配所有的请求
        wireMockServer.stubFor(any(anyUrl())
                .atPriority(10)
                .willReturn(notFound())
        );

        // 匹配符合路径的请求
        wireMockServer.stubFor(get(urlMatching("/api/book/.*"))
                .atPriority(5)
                .willReturn(aResponse()
                        .withStatus(402)
                        .withBody("没有访问权限"))
        );

        // 匹配符合路径的请求
        wireMockServer.stubFor(get(urlEqualTo("/api/book/test"))
                .atPriority(1)
                .willReturn(ok("测试地址"))
        );

        given()
                .when()
                .get("/test/none")
                .then()
                .log().status();

        given()
                .when()
                .get("/api/book/none")
                .then()
                .log().all();

        given()
                .when()
                .get("/api/book/test")
                .then()
                .log().all();
    }

    @Test
    @DisplayName("服务端错误500")
    void testServerError() {
        wireMockServer.stubFor(post(urlEqualTo("/api/book/err"))
                .willReturn(serverError())
        );

        given()
                .when()
                .post("/api/book/err")
                .then()
                .log().all();
    }

    @Test
    @DisplayName("场景的使用方法")
    void testScene() {
        wireMockServer.stubFor(post(urlEqualTo("/rest/user"))
                .willReturn(ok("{\"user\":\"admin\"}"))
        );

        wireMockServer.stubFor(post(urlEqualTo("/rest/user"))
                .inScenario("get user")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse().withStatus(204))
                .willSetStateTo("deleted")
        );

        wireMockServer.stubFor(post(urlEqualTo("/rest/user"))
                .inScenario("get user")
                .whenScenarioStateIs("deleted")
                .willReturn(notFound())
        );

        given()
                .when()
                .post("/rest/user")
                .then()
                .log().all();

        given()
                .when()
                .delete("/rest/user")
                .then()
                .log().all();

        given()
                .when()
                .post("/rest/user")
                .then()
                .log().all();
    }

    @AfterAll
    static void stopAll() {
        // 关闭WireMockServer
        wireMockServer.stop();
    }
}
