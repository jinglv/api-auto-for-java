package com.test.request.file

import com.test.file.FileService
import org.junit.Assert
import spock.lang.Specification

/**
 *
 *
 * @author jingLv* @date 2020/08/06
 */
class UserClientTest extends Specification {
    FileService fileService
    UserClient userClient

    def setup() {
        fileService = new FileService()
        userClient = new UserClient()
    }

    def "add user with file"() {
        given: "no given"
        when: "call the add user api"
        // 创建addUser.json文件，将前面列出的request body内容放到该文件中。
        def file = fileService.createFile("./src/main/resources/body/addUser.json") //获取文件对象
        then: "get the correct response"
        Assert.assertEquals(userClient.addUserWithFile(file), "add user successfully") //将文件对象传入userClient.addUserWithFile()中，这里可以开启.log().all()查看接口是否返回正确的response body。
    }

    def "add user with string"() {
        given: "no given"
        when: "call the add user api"
        String body = "{\n" +
                "  \"name\": \"TOM\",\n" +
                "  \"age\": 10,\n" +
                "  \"contacts\": [\n" +
                "    {\n" +
                "      \"city\": \"chengdu\",\n" +
                "      \"street\": \"huaxi-Street\",\n" +
                "      \"phone\": \"11122222222\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"city\": \"meijing\",\n" +
                "      \"street\": \"qinghua-street\",\n" +
                "      \"phone\": \"33333444\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"background\": {\n" +
                "    \"degree\": \"doctor\",\n" +
                "    \"educate school\": \"Beijing univercity\",\n" +
                "    \"graduate Date\": \"2019-7\"\n" +
                "  },\n" +
                "  \"otherDescription\": \"any comment\"\n" +
                "}"
        then: "get the correct response"
        Assert.assertEquals(userClient.addUserWithString(body), "add user successfully")
    }
}
