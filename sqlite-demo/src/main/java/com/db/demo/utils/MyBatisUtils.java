package com.db.demo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jingLv
 * @date 2020/09/21
 */
public class MyBatisUtils {
    /**
     * 重量级资源且线程安全
     */
    private static SqlSessionFactory sqlSessionFactory;
    /**
     * 绑定ThreadLocal
     */
    private static final ThreadLocal<SqlSession> T = new ThreadLocal<SqlSession>();

    static {
        try {
            InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取getSqlSession
     *
     * @return 返回SqlSession
     */
    public static SqlSession getSqlSession() {
        //获取线程连接
        SqlSession sqlSession = T.get();
        if (sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
            T.set(sqlSession);
        }
        return sqlSession;
    }


    /**
     * 释放资源
     */
    public static void close() {
        SqlSession sqlSession = T.get();
        if (sqlSession != null) {
            sqlSession.close();
            T.remove();
        }
    }
}
