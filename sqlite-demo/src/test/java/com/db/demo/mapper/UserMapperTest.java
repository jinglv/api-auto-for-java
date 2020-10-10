package com.db.demo.mapper;

import com.db.demo.entity.User;
import com.db.demo.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author jingLv
 * @date 2020/10/10
 */
class UserMapperTest {

    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

    @Test
    void findAllUser() {
        List<User> allUser = userMapper.findAllUser();
        allUser.forEach(System.out::println);
    }
}