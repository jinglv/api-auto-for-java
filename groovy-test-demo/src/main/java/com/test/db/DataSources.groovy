package com.test.db

import com.test.file.FileService
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
    FileService fileService
    def configs

    DataSources() {
        fileService = new FileService()
        configs = fileService.getConfigs('./src/main/resources/config.yml')
    }

    Sql getSql() {
        if (!sql) {
            def mysqlDB = [
                    driver  : 'com.mysql.jdbc.Driver',
                    url     : configs.dev.db.url,
                    user    : configs.dev.db.user,
                    password: configs.dev.db.password
            ]
            sql = Sql.newInstance(mysqlDB.url, mysqlDB.user, (mysqlDB.password).toString(), mysqlDB.driver)
        }
        sql
    }
}
