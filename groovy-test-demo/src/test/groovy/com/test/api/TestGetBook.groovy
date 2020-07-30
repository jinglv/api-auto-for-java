package com.test.api

import spock.lang.Specification

import static io.restassured.RestAssured.given

/**
 *
 *
 * @author jingLv* @date 2020-07-30 3:54 下午
 *
 */

class TestGetBook extends Specification {

    def "should call mock api successfully"() { //spock框架（BDD框架）语法，所有case都是def开头，def后面是该case的描述信息
        given: "no given" //spock框架语法，given-when-then三段式写法，given/when/then后是描述信息
        when: "call mock api api"
        given().baseUri("http://localhost:9090")    //这里输入接口的baseUri
                .when()
                .get("api/getBook/abc")          //输入接口的地址
                .then()
                .assertThat().statusCode(200)      //这里校验调用接口后返回的状态码是200，如果不是200，调用会失败
        then: "no then"
    }

    def "should get book detail by book name successfully"() {
        given: "no given"
        when: "call get book by book name api"
        given().baseUri("http://localhost:9090")
                .when()
                .pathParam("bookName", bookName)
                .get("api/getBook/{bookName}")
                .then().log().all()
                .assertThat().statusCode(200)
        then: "no then"
        where: //固定写法，where：后面跟测试用例需要的测试数据
        bookName | placeHolder //固定写法，多个参数之间用“|”隔开，且至少要有一个“|”，所以如果只有一个输入参数“|”后面可以写个placeHolder
        "tome"   | ""
        "dave"   | ""
    }

}
