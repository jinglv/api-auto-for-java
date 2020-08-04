package com.test.groovy.db

import groovy.sql.Sql

/**
 * 负责数据库的连接
 * 实际项目中如果需要连接多个数据库或者多种类型数据库，例如mongoDB,oracle等都可以放到该Class中
 *
 * @author jingLv* @date 2020-08-04 4:58 下午
 *
 */
class DataSources {
    Sql sql

    Sql getSql() {
        if (!sql) {
            def mysqlDB = [
                    driver  : 'com.mysql.jdbc.Driver',
                    url     : 'jdbc:mysql://127.0.0.1:3306/test_groovy',
                    user    : 'root',//这里写入安装mysql是设置的用
                    password: '123123'
            ]
            sql = Sql.newInstance(mysqlDB.url, mysqlDB.user, mysqlDB.password, mysqlDB.driver)
        }
        sql
    }
}
