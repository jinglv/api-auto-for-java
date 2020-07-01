# Mustache 模板教程
[mustache官方文档](http://mustache.github.io)

学习博客：
[mustache学习博客](https://blog.csdn.net/neweastsun/article/details/102881478)


## 概述
Mustache属于无逻辑模板引擎，因为其不支持if-else和for语句，主要是有{{}}括起来的模板变量及包含模板数据的模型对象组成，因为双括号看起来像胡子，因此得名mustache。

可根据模板自动生成对应的数据

# 实战
示例需求如下：
1. 写个简单模板
2. 使用Java Api编译模板
3. 提供必要的数据生成动态内容

## 简单的模板
在resources文件下，创建文件todo.mustache，内容如下：
```
<h2>{{title}}</h2>
<small>Created on {{createdOn}}</small>
<p>{{text}}</p>
```
在{{}}中的模板变量可以是Java类的方法和属性，也是Map对象的key。

## 编译模板
编译模板代码：
```
MustacheFactory mf = new DefaultMustacheFactory();
Mustache m = mf.compile("todo.mustache");
```
MustacheFactory 在类路径下搜索模板文件，我们的模板文件在src/main/resources路径下。

## 执行模板
提供模板数据是Todo类的实例：
```
package com.mustache.demo.entity;

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
```
执行模板生成HTML内容的代码为：
```
package com.mustache.demo;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.mustache.demo.entity.Todo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

/**
 * @author jingLv
 * @date 2020-04-14 10:52 PM
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // 实例赋值
        Todo todo = new Todo();
        todo.setTitle("Todo1");
        todo.setText("第一个模板");
        todo.setCreatedOn(new Date());
        //编译
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("todo.mustache");

        StringWriter writer = new StringWriter();
        m.execute(writer, todo).flush();
        String html = writer.toString();
        System.out.println(html);
    }
}
```
输出的结果：
```
<h2>Todo1</h2>
<small>Created on Tue Apr 14 23:01:03 CST 2020</small>
<p>第一个模板</p>
```

## Mustache的节(Section)和迭代
要使用Mustache的节(Section)，节是根据上下文中key的值决定重复一次或多次的代码块。

节以`#`号开头，`/`结尾，其中的变量会被解析用于渲染实际内容

