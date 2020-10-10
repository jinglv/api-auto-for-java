package com.db.demo.utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SQLite数据库工具类
 *
 * @author jingLv
 * @date 2020/09/29
 */
public class SqliteUtils {

    private Connection connection;
    private final String dbFilePath;
    private Statement statement;
    private ResultSet resultSet;

    /**
     * 构造函数
     *
     * @param dbFilePath SQLite DB文件路径
     * @throws ClassNotFoundException 文件未找到异常
     * @throws SQLException           SQL异常
     */
    public SqliteUtils(String dbFilePath) throws ClassNotFoundException, SQLException {
        this.dbFilePath = dbFilePath;
        connection = getConnection(dbFilePath);
    }

    /**
     * 获取数据库连接
     *
     * @param dbFilePath SQLite DB文件路径
     * @return 返回数据库连接
     * @throws ClassNotFoundException 文件未找到异常
     * @throws SQLException           SQL异常
     */
    private Connection getConnection(String dbFilePath) throws ClassNotFoundException, SQLException {
        Connection conn;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
        return conn;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        if (null == connection) {
            connection = getConnection(dbFilePath);
        }
        return connection;
    }

    private Statement getStatement() throws SQLException, ClassNotFoundException {
        if (null == statement) {
            statement = getConnection().createStatement();
        }
        return statement;
    }

    /**
     * 执行select查询，返回结果列表
     *
     * @param sql   sql select 语句
     * @param clazz 实体泛型
     * @return 实体集合
     * @throws SQLException           异常信息
     * @throws ClassNotFoundException 异常信息
     */
    public <T> List<T> executeQueryList(String sql, Class<T> clazz) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<T> rsList = new ArrayList<T>();
        try {
            resultSet = getStatement().executeQuery(sql);
            while (resultSet.next()) {
                T t = clazz.newInstance();
                for (Field field : t.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(t, resultSet.getObject(field.getName()));
                }
                rsList.add(t);
            }
        } finally {
            destroyed();
        }
        return rsList;
    }

    /**
     * 执行sql查询,适用单条结果集
     *
     * @param sql   sql select 语句
     * @param clazz 结果集处理类对象
     * @return 查询结果
     * @throws SQLException           SQL异常
     * @throws ClassNotFoundException 文件未找到异常
     */
    public <T> T executeQuery(String sql, Class<T> clazz) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            resultSet = getStatement().executeQuery(sql);
            T t = clazz.newInstance();
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(t, resultSet.getObject(field.getName()));
            }
            return t;
        } finally {
            destroyed();
        }
    }

    /**
     * 执行数据库更新sql语句
     *
     * @param tableName 表名
     * @param param     key-value键值对,key:表中字段名,value:值
     * @throws SQLException           SQL异常
     * @throws ClassNotFoundException 文件未找到异常
     */
    public void executeInsert(String tableName, Map<String, Object> param) throws SQLException, ClassNotFoundException {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ");
            sql.append(tableName);
            sql.append(" ( ");
            for (String key : param.keySet()) {
                sql.append(key);
                sql.append(",");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(")  VALUES ( ");
            for (String key : param.keySet()) {
                sql.append("'");
                sql.append(param.get(key));
                sql.append("',");
            }
            sql.delete(sql.length() - 1, sql.length());
            sql.append(");");
            getStatement().executeUpdate(sql.toString());
        } finally {
            destroyed();
        }
    }

    /**
     * 执行数据库更新sql语句
     *
     * @param sql sql语句
     * @return 更新行数
     * @throws SQLException           SQL异常
     * @throws ClassNotFoundException 文件未找到异常
     */
    public int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
        int c;
        try {
            c = getStatement().executeUpdate(sql);
        } finally {
            destroyed();
        }
        return c;

    }

    /**
     * 执行多个sql更新语句
     *
     * @param sqls sql列表
     * @throws SQLException           SQL异常
     * @throws ClassNotFoundException 文件未找到异常
     */
    public void executeUpdate(String... sqls) throws SQLException, ClassNotFoundException {
        try {
            for (String sql : sqls) {
                getStatement().executeUpdate(sql);
            }
        } finally {
            destroyed();
        }
    }

    /**
     * 执行数据库更新 sql List
     *
     * @param sqls sql列表
     * @throws SQLException           SQL异常
     * @throws ClassNotFoundException 文件未找到异常
     */
    public void executeUpdate(List<String> sqls) throws SQLException, ClassNotFoundException {
        try {
            for (String sql : sqls) {
                getStatement().executeUpdate(sql);
            }
        } finally {
            destroyed();
        }
    }

    /**
     * 数据库资源关闭和释放
     */
    public void destroyed() {
        try {
            if (null != resultSet) {
                resultSet.close();
                resultSet = null;
            }

            if (null != statement) {
                statement.close();
                statement = null;
            }

            if (null != connection) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLite数据库关闭异常:" + e);
        }
    }
}

