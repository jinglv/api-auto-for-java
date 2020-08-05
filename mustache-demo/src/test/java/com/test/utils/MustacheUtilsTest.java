package com.test.utils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.test.pojo.Todo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author jingLv
 * @date 2020-08-05 3:34 下午
 */
class MustacheUtilsTest {

    @Test
    void execute() throws IOException {
        // 实例赋值
        Todo todo = new Todo();
        todo.setTitle("Todo1");
        todo.setText("第一个模板");
        todo.setCreatedOn(new Date());
        //编译
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("mustache/todo.mustache");

        StringWriter writer = new StringWriter();
        m.execute(writer, todo).flush();
        String html = writer.toString();
        System.out.println(html);
    }

    @Test
    void testUtils() {
        // 实例赋值
        Todo todo2 = new Todo();
        todo2.setTitle("Todo2");
        todo2.setText("第二个模板");
        todo2.setCreatedOn(new Date());
        //使用工具类
        String body = new MustacheUtils("mustache/todo.mustache", todo2).execute();
        System.out.println(body);
    }
}