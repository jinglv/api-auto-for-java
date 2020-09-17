package com.mybatis.demo.mapper;

import com.mybatis.demo.entity.User;

import java.util.List;

/**
 * @author jingLv
 * @date 2020/09/17
 */
public interface UserMapper {
    /**
     * 根据用户id查询用户信息
     *
     * @param uid 用户id
     * @return 返回用户信息
     */
    User getUserByUid(String uid);

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 返回添加影响行数
     */
    Integer addUser(User user);

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 返回添加影响行数
     */
    Integer addUser2(User user);

    /**
     * 根据用户id删除用户信息
     *
     * @param uid 用户id
     * @return 返回删除后影响的行数
     */
    Integer deleteUserByUid(String uid);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 返回添加影响行数
     */
    Integer updateUser(User user);

    /**
     * 查询所有用户信息
     *
     * @return 返回用户信息列表
     */
    List<User> getUserAll();
}
