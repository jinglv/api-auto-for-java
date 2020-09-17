package com.mybatis.curd;

import com.mybatis.demo.entity.User;
import com.mybatis.demo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;

/**
 * 对User表的CURD的操作
 *
 * @author jingLv
 * @date 2020/09/17
 */
public class UserOperation {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getInstance();
    SqlSession sqlSession = factory.openSession();

    @Test
    void UserInsert() {
        User user = new User(6, "林朝英", "台湾省台北市");
        // UserMapper.xml文件中的namespace="com.demo.userMapper"
        int insert = sqlSession.insert("com.demo.userMapper.addUser", user);
        System.out.println(insert);
        sqlSession.commit();
        sqlSession.close();
    }
}
