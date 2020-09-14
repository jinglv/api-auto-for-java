package com.test.filter;

import cn.hutool.core.codec.Base64;
import com.test.utils.JsonPathUtils;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jingLv
 * @date 2020/08/03
 */
public class TestFilter {
    /**
     * 全局抽出来的filter对象
     */
    //@BeforeAll
    static void before() {
        RestAssured.filters((req, res, ctx) -> {
                    if (req.getURI().contains("/api/book/encryption")) {
                        // 返回的Response不具备set方法，无法修改body
                        Response resOrigin = ctx.next(req, res);
                        // 解密过程
                        String raw = Base64.decodeStr(resOrigin.body().asString());
                        // 响应构造器，ResponseBuilder的作用主要是在Response的基础上建设出来一个新的可以修改的body对象
                        ResponseBuilder resBuilder = new ResponseBuilder().clone(resOrigin);
                        //Response无法直接修改body，所有间接的通过ResponseBuilder构建
                        resBuilder.setBody(raw);
                        //ResponseBuilder在最后通过build方法直接创建一个用于返回的不可修改的Response
                        return resBuilder.build();
                    } else {
                        return ctx.next(req, res);
                    }
                }
        );
    }

    /**
     * 返回body未加密
     */
    @Test
    void testResponse() {
        given().baseUri("http://localhost:9090")
                .log().all()
                .when()
                .get("/api/book/details")
                .then()
                .log().all()
                .body("books.name[0]", equalTo("西游记"))
                .statusCode(200);
    }

    /**
     * 使用filter处理请求返回body加密的情况
     */
    @Test
    void testFilterResponse() {
        given().baseUri("http://localhost:9090")
                .log().all()
//                .filter((req, res, ctx) -> {
//                    // 返回的Response不具备set方法，无法修改body
//                    Response resOrigin = ctx.next(req, res);
//                    // 解密过程
//                    String raw = Base64.decodeStr(resOrigin.body().asString());
//                    // 响应构造器，ResponseBuilder的作用主要是在Response的基础上建设出来一个新的可以修改的body对象
//                    ResponseBuilder resBuilder = new ResponseBuilder().clone(resOrigin);
//                    //Response无法直接修改body，所有间接的通过ResponseBuilder构建
//                    resBuilder.setBody(raw);
//                    //ResponseBuilder在最后通过build方法直接创建一个用于返回的不可修改的Response
//                    return resBuilder.build();
//                })
                .when()
                .get("/api/book/encryption").prettyPeek()
                .then()
                .log().all()
                .body("books.name[0]", equalTo("西游记"))
                .statusCode(200);
    }

    /**
     * 不使用filter的方式处理接口返回加密的情况
     */
    @Test
    void testEncryptionResponse() {
        Response response = given().baseUri("http://localhost:9090")
                .get("/api/book/encryption")
                .then()
                .log().all()
                .extract()
                .response();
        //解密
        String raw = Base64.decodeStr(response.body().asString());
        System.out.println(raw);
        Object expected = new JsonPathUtils(raw, "$.books[0].name").readJson();
        assertEquals(expected, "西游记");
    }
}
