package com.json.example.bean;

import lombok.Data;

import java.util.List;

/**
 * @author jingLv
 * @date 2020/08/21
 */
@Data
public class User {

    private String name;
    private Integer age;
    private String birthday;
    private String school;
    private List<String> major;
    private Boolean hasBoyfriend;
    private String car;
    private String house;
    private String comment;
}
