package com.db.demo.mapper;

import com.db.demo.entity.User;

import java.util.List;

/**
 * @author jingLv
 * @date 2020/10/10
 */
public interface UserMapper {

    /**
     * 查询所有用户
     *
     * @return 返回用户列表
     */
    List<User> findAllUser();
}
