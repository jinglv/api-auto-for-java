package com.mybatis.demo;

import com.mybatis.demo.entity.User;
import com.mybatis.demo.mapper.UserMapper;
import com.mybatis.demo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author jingLv
 * @date 2020/09/17
 */
public class Main {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory factory = SqlSessionFactoryUtils.getInstance();
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> allUser = mapper.getUserAll();
        System.out.println(allUser);
    }
}
