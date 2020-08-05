package com.test.groovy

import com.test.db.DataRepository
import org.junit.Assert
import spock.lang.Specification

/**
 *
 *
 * @author jingLv* @date 2020-08-04 5:08 下午
 *
 */
class DBOptionsDemo extends Specification {
    DataRepository dataRepository

    void setup() {
        dataRepository = new DataRepository()
    }

    def "should get user info successfully"() {
        given: "no given"
        when: "query user table to get info"
        def userInfo = dataRepository.getUserInfo()
        then: "should get user info"
        userInfo.each { it -> println it.username + ":" + it.age + ":" + it.create_date }   //打印从数据库获取到的所有user信息
    }

    def "should get user address successfully"() {
        given: "no given"
        when: "query user and address table"
        def addressInfo = dataRepository.getAddressByUserName(userName)
        then: "should get correct user address info"
        Assert.assertEquals(addressInfo.address, address)  //校验从数据库获取的数据是否正确
        where:
        userName | address
        "TOM"    | "chengdu"
        "DONE"   | "beijing"
        "ECHO"   | "shanghai"
        "MARY"   | "hangzhou"
    }

    def "should add user successfully"() {
        given: "no given"
        when: "add user"
        dataRepository.addUser(userName, age)    //添加信息到user表
        then: "should get added user successfully"
        Assert.assertEquals(dataRepository.getUser(userName).username, userName)  //只有成功添加了“Dave”这个user，校验才会成功
        where:
        userName | age
        "Dave"   | 88
    }

    def "should update address successfully"() {
        given: "no given"
        when: "update user's address"
        dataRepository.updateAddress(userName, age)  //修改user表信息
        then: "should update address successfully"
        Assert.assertEquals(dataRepository.getUser(userName).age, age)   //校验修改后的user的age是否正确
        where:
        userName | age
        "MARY"   | 55
    }
}
