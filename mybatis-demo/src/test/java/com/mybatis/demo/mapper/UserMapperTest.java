package com.mybatis.demo.mapper;

import com.mybatis.demo.entity.User;
import com.mybatis.demo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author jingLv
 * @date 2020/09/17
 */
class UserMapperTest {
    SqlSessionFactory instance = SqlSessionFactoryUtils.getInstance();
    SqlSession sqlSession = instance.openSession();

    @Test
    void getUserAll() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUserAll();
        users.forEach(System.out::println);
    }
}