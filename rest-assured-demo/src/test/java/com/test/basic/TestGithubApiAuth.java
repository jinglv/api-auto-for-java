package com.test.basic;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Rest-Assured认证处理
 * - preemptive() 显示将鉴权信息在header中携带
 * - 直接指定header参数进行鉴权 header("Authorization", "token xxxxxx")
 *
 * @author jingLv
 * @date 2020/10/13
 */
class TestGithubApiAuth {

    /**
     * 根据username进行信息查询
     */
    @Test
    void queryUser() {
        given()
                .pathParam("username", "jinglv")
                .when()
                .get("https://api.github.com/users/{username}")
                .then()
                .log().body();
    }

    /**
     * 查询GitHub的repo信息--oauth2
     * auth().oauth2() 该方式是将token信息隐式在请求体重
     * auth().preemptive().oauth2() 显示将鉴权信息在header中携带
     * 直接指定header参数进行鉴权 header("Authorization", "token xxxxxx")
     */
    @Test
    void queryRepoOauth() {
        given().log().all()
                //.auth().oauth2("token")
                //.auth().preemptive().oauth2("token")
                //.header("Authorization", "token token")
                .when()
                .get("https://api.github.com/user/repos")
                .then()
                .log().all();
    }

    /**
     * 查询GitHub的repo信息--basic
     * basic("username", "password") 中的username和password替换为自己的GitHub的用户名和密码即可
     */
    @Test
    void queryRepoBasic() {
        given().log().all()
                .auth().preemptive().basic("username", "password")
                .when()
                .get("https://api.github.com/user/repos")
                .then()
                .log().all();
    }
}
