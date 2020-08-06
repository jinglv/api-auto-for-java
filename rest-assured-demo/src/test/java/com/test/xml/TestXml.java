package com.test.xml;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author jingLv
 * @date 2020/07/28
 */
public class TestXml {

    /**
     * 对第二个 name 的值 Coffee 进行断言
     */
    @Test
    void testXMLForIndex() {
        when().
                get("http://127.0.0.1:9090/api/xml").
                then().
                log().all().
                body("shopping.category[0].item[1].name", equalTo("Coffee"));
    }

    /**
     * 可以利用 size() 方法来获取对应节点的数量，例如这里要断言 category 的数量
     */
    @Test
    void testXMLForSize() {
        when().
                get("http://127.0.0.1:9090/api/xml").
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
        when().
                get("http://127.0.0.1:9090/api/xml").
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
        when().
                get("http://127.0.0.1:9090/api/xml").
                then().
                log().all().
                body("**.findAll{ it.price == 10 }.name", equalTo("Chocolate"));
    }
}
