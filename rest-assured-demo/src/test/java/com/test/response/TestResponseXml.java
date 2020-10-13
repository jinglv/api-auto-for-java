package com.test.response;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * 测试接口，返回为响应体为xml进行断言处理
 *
 * @author jingLv
 * @date 2020/07/28
 */
class TestResponseXml {

    @BeforeAll
    static void before() {
        RestAssured.baseURI = "http://127.0.0.1:9090/api";
    }

    /**
     * 对第二个name的值Coffee进行断言
     */
    @Test
    void testXMLForIndex() {
        given().
                when().
                get("/xml").
                then().
                log().all().
                body("shopping.category[0].item[1].name", equalTo("Coffee"));
    }

    /**
     * 可以利用size()方法来获取对应节点的数量，例如这里要断言category的数量
     */
    @Test
    void testXMLForSize() {
        given().
                when().
                get("/xml").
                then().
                log().all().
                body("shopping.category.size()", equalTo(3));
    }

    /**
     * it.@type、it.price
     * 在 xml中 断言中，可以利用 it. 属性或节点的值来作为筛选条件；
     * 例如这里要获取 type 为 supplies 的 category 下的第一个 item 的 name，以及获取 price 为 10 的商品名 name
     */
    @Test
    void testXMLForIt() {
        given().
                when().
                get("/xml").
                then().
                log().all().
                body("shopping.category.findAll{ it.@type == 'supplies' }.item[0].name", equalTo("Paper")).
                body("shopping.category.item.findAll{ it.price == 10 }.name", equalTo("Chocolate"));
    }

    /**
     * 对于xml中有一个特别的语法，**.findAll，可以直接忽略前面的节点，直接对筛选条件进行匹配，依然获取price为10的商品名name
     */
    @Test
    void testXMLForFindAll() {
        given().
                when().
                get("/xml").
                then().
                log().all().
                body("**.findAll{ it.price == 10 }.name", equalTo("Chocolate"));
    }
}
