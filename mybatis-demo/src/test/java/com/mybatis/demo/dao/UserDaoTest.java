package com.mybatis.demo.dao;

import com.mybatis.demo.entity.User;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author jingLv
 * @date 2020/09/17
 */
class UserDaoTest {

    UserDao userDao = new UserDao();

    @Test
    void getAllUser() {
        List<User> userList = userDao.getAllUser();
        userList.forEach(System.out::println);
    }
}