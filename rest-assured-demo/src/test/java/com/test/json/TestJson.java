package com.test.json;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * @author jingLv
 * @date 2020/07/28
 */
public class TestJson {

    /**
     * 可以使用根节点.(点)子节点的方式一层层的找下去，例如我们需要对lottoId等于 5 进行断言：
     */
    @Test
    void testGPathForNode01() {
        given().
                when().
                log().all().get("http://127.0.0.1:9090/api/json").
                then().
                log().all().body("lotto.lottoId", equalTo(5));
    }

    /**
     * 如果想要断言winners数组下面的winnerId，检查23和54是否包含其中，可以如下lotto.winners.winnerId写法
     */
    @Test
    void testGPathForNode02() {
        given().
                when().
                log().all().get("http://127.0.0.1:9090/api/json").
                then().
                log().all().body("lotto.winners.winnerId", hasItems(54, 23));
    }

    /**
     * 如果我们想要取某些相同字段中的某一个，可以使用类似索引的方式获取，例如想要断言 winners 数组下面的 winnerId 的第一个值是否为23，可以使用 lotto.winners.winnerId[0]
     */
    @Test
    void testGPathFoIndex01() {
        given().
                when().
                log().all().get("http://127.0.0.1:9090/api/json").
                then().
                log().all().body("lotto.winners.winnerId[0]", equalTo(23));
    }

    /**
     * 如果我们想要取某些相同字段中的最后一个，可以使用 -1 作为索引，例如断言断言 winners 数组下面的 winnerId 的最后一个的值是否为 54
     */
    @Test
    void testGPathFoIndex02() {
        given().
                when().
                log().all().get("http://127.0.0.1:9090/api/json").
                then().
                log().all().body("lotto.winners.winnerId[-1]", equalTo(54));
    }

    /**
     * 可以在 findAll 方法中写筛选条件，例如我们想取 winnerId 的值在大于或等于 30 小于 60 之间的结果进行断言
     */
    @Test
    void testGPathFoFindAll() {
        given().
                when().
                log().all().get("http://127.0.0.1:9090/api/json").
                then().
                log().all().body("lotto.winners.findAll{ winners -> winners.winnerId >= 30 && winners.winnerId < 60}.winnerId[0]", equalTo(54));
    }

    /**
     * find 的用法与 findAll 基本一致，只是 find 默认取匹配到的第一个
     */
    @Test
    void testGPathFoFind() {
        given().
                when().
                log().all().get("http://127.0.0.1:9090/api/json").
                then().
                log().all().body("lotto.winners.find{ winners -> winners.winnerId >= 30 && winners.winnerId < 60}.winnerId", equalTo(54));
    }
}
