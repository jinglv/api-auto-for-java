package com.test.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author jingLv
 * @date 2020-04-14 10:52 PM
 */
@Data
public class Todo {
    private String title;
    private String text;
    private boolean done;
    private Date createdOn;
    private Date completedOn;
}
