package com.mybatis.demo.dao;

import com.mybatis.demo.entity.User;
import com.mybatis.demo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author jingLv
 * @date 2020/09/17
 */
public class UserDao {
    private final SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getInstance();

    public User getUserById(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = (User) sqlSession.selectOne("com.mybatis.demo.dao.UserDao.getUserByUid", id);
        sqlSession.close();
        return user;
    }

    public Integer addUser(User user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int insert = sqlSession.insert("com.mybatis.demo.dao.UserDao.UserDao.addUser", user);
        sqlSession.commit();
        sqlSession.close();
        return insert;
    }

    public Integer addUser2(User user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int insert = sqlSession.insert("com.mybatis.demo.dao.UserDao.UserDao.addUser2", user);
        sqlSession.commit();
        sqlSession.close();
        return insert;
    }

    public Integer deleteUserById(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int delete = sqlSession.delete("com.mybatis.demo.dao.UserDao.deleteUserByUid", id);
        sqlSession.commit();
        sqlSession.close();
        return delete;
    }

    public Integer updateUser(User user) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int delete = sqlSession.delete("com.mybatis.demo.dao.UserDao.updateUser", user);
        sqlSession.commit();
        sqlSession.close();
        return delete;
    }

    public List<User> getAllUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("com.mybatis.demo.dao.UserDao.getUserAll");
        sqlSession.close();
        return users;
    }
}
