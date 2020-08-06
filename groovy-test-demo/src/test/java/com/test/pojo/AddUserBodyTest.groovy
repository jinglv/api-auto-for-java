package com.test.pojo

import com.test.request.file.UserClient
import org.junit.Assert
import spock.lang.Specification

/**
 *
 *
 * @author jingLv* @date 2020/08/06
 */
class AddUserBodyTest extends Specification {
    UserClient userClient
    AddUserBody addUserBody

    def setup() {
        userClient = new UserClient()
        addUserBody = new AddUserBody()
    }

    def "should add user with only inputting main contact successfully"() {
        given: "generate request body"
        def body = addUserBody.addMainContact(city, street, phone).generateBody()
        when: "call add user api"
        def response = userClient.addUserWithString(body)
        then: "should get correct reponse"
        Assert.assertEquals("assert add user api response correct", response, "add user successfully")
        where:
        city    | street         | phone
        "lixin" | "beijing-soho" | 13566667777
    }

    def "should add user with inputting main and backup contact successfully"() {
        given: "generate request body"
        def body = addUserBody.addMainContact(mainCity, mainStreet, mainPhone) //添加mianContact信息
                .addBackupContact(backupCity, backupStreet, backupPhone)  //添加BackupContact信息
                .generateBody()
        when: "call add user api"
        def response = userClient.addUserWithString(body)
        then: "should get correct reponse"
        Assert.assertEquals("assert add user api response correct", response, "add user successfully")
        where:
        mainCity | mainStreet    | mainPhone   | backupCity | backupStreet  | backupPhone
        "lixin"  | "huilongguan" | 18611112222 | "beijing"  | "chaoyangmen" | 00112233445
    }

    def "should add user with inputting contacts and background successfully"() {
        given: "generate request body"
        def body = new AddUserBody()
                .addMainContact(mainCity, mainStreet, mainPhone)           //添加mianContact信息
                .addBackupContact(backupCity, backupStreet, backupPhone)   //添加BackupContact信息
                .addBackGround(degree, school, date)                       //添加BackGround信息
                .generateBody()
        when: "call add user api"
        def response = userClient.addUserWithString(body)
        then: "should get correct response"
        Assert.assertEquals("assert add user api response correct", response, "add user successfully")
        where:
        mainCity  | mainStreet   | mainPhone   | backupCity | backupStreet | backupPhone | degree   | school    | date
        "chengdu" | "one-street" | 11223344556 | "beijing"  | "two-street" | 00112233445 | "doctor" | "qinghua" | "2019-07"
    }
}
