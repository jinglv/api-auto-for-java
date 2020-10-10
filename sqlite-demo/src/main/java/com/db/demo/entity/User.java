package com.db.demo.entity;

import lombok.Data;

/**
 * 用户表User实体
 *
 * @author jingLv
 * @date 2020/10/10
 */
@Data
public class User {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 注册时间
     */
    private String registerTime;
    /**
     * 创建时间
     */
    private String createTime;
}
