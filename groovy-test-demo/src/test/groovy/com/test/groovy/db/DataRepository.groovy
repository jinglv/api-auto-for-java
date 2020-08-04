package com.test.groovy.db

/**
 * 负责执行 SQL
 *
 * @author jingLv* @date 2020-08-04 5:06 下午
 *
 */
class DataRepository extends DataSources {
    def getUserInfo() {
        def userInfo = sql.rows(ConstantSql.getUserInfo)   //查询多行数据
        userInfo ? userInfo : ''  //这里做了空保护
    }

    def getAddressByUserName(userName) {
        def address = sql.firstRow(ConstantSql.getAddressInfoByUserName, [userName])  //只获取第一行数据
        // 上面调用的getAddressInfoByUserName sql语句需要传递一个参数，所以在后面带了userName参数
        address ? address : ''
    }

    def addUser(userName, age) {
        sql.execute(ConstantSql.addUser, [userName, age])   //传递两个参数
        // 非查询类操作使用execute方法
    }

    def getUser(userName) {
        sql.firstRow(ConstantSql.getUser, [userName])
    }

    def updateAddress(userName, age) {
        sql.execute(ConstantSql.updateAge, [age, userName])
    }
}
