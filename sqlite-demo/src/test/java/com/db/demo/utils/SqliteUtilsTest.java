package com.db.demo.utils;

import cn.hutool.core.date.DateUtil;
import com.db.demo.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jingLv
 * @date 2020/10/10
 */
class SqliteUtilsTest {

    private static SqliteUtils sqliteUtils;

    static {
        try {
            sqliteUtils = new SqliteUtils("sqlite_db.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有User数据
     * 注意：数据表的列名与实体名不一致，在编写SQL语句中的列名使用别名与实体的列名一致
     */
    @Test
    void testAllQuery() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String userSql = "select id, username, password, register_time as registerTime, create_time as createTime from user";
        List<User> users = sqliteUtils.executeQueryList(userSql, User.class);
        users.forEach(System.out::println);
    }

    /**
     * 插入数据
     * 注意：key是数据表的列名
     */
    @Test
    void testInsert() throws SQLException, ClassNotFoundException {
        Map<String, Object> insertUser = new HashMap<>();
        insertUser.put("username", "manager");
        insertUser.put("password", "123456");
        insertUser.put("register_time", DateUtil.now());
        insertUser.put("create_time", DateUtil.now());
        sqliteUtils.executeInsert("user", insertUser);
    }
}