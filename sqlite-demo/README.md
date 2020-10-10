# SQLite学习
[SQLite官网](https://www.sqlite.org/index.html)
[SQL教程](https://www.runoob.com/sqlite/sqlite-tutorial.html)

## 简介
SQLite是一个进程内的库，实现了自给自足的、无服务器的、零配置的、事务性的 SQL 数据库引擎。它是一个零配置的数据库，这意味着与其他数据库不一样，您不需要在系统中配置。

就像其他数据库，SQLite引擎不是一个独立的进程，可以按应用程序需求进行静态或动态连接。SQLite 直接访问其存储文件。

## 为什么要用SQLite？
- 不需要一个单独的服务器进程或操作的系统（无服务器的）。
- SQLite 不需要配置，这意味着不需要安装或管理。
- 一个完整的 SQLite 数据库是存储在一个单一的跨平台的磁盘文件。
- SQLite 是非常小的，是轻量级的，完全配置时小于 400KiB，省略可选功能配置时小于250KiB。
- SQLite 是自给自足的，这意味着不需要任何外部的依赖。
- SQLite 事务是完全兼容 ACID 的，允许从多个进程或线程安全访问。
- SQLite 支持 SQL92（SQL2）标准的大多数查询语言的功能。
- SQLite 使用 ANSI-C 编写的，并提供了简单和易于使用的 API。
- SQLite 可在 UNIX（Linux, Mac OS-X, Android, iOS）和 Windows（Win32, WinCE, WinRT）中运行。

## Java操作SQLite
1. Maven项目引入依赖
    ```xml
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.32.3.2</version>
    </dependency>
    ```
2. 编写SQLiteUtils.java的工具类，进行数据库的操作
3. 编写User实体类
4. 测试SQLite数据库的增加和查询

## MyBaits整合SQLite操作
1. Maven引入MyBatis依赖
   ```xml
   <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis</artifactId>
       <version>3.5.5</version>
   </dependency>
   ```
2. src/main/resources下新建mybatis-config.xml，并编写相关配置
    ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <environments default="development">
           <environment id="development">
               <transactionManager type="JDBC"/>
               <dataSource type="POOLED">
                   <!--SQLite驱动-->
                   <property name="driver" value="org.sqlite.JDBC"/>
                   <!--SQLite数据库地址-->
                   <property name="url" value="jdbc:sqlite:capital_test.db"/>
                   <!--SQLite没有用户名和密码，为空即可-->
                   <property name="username" value=""/>
                   <property name="password" value=""/>
               </dataSource>
           </environment>
       </environments>
       <mappers>
           <mapper resource="mapper/UserMapper.xml"/>
       </mappers>
   </configuration>
    ```
3. 创建Mapper包，编写UserMapper接口类，并在src/main/resources下创建mapper目录，编写对应的UserMapper.xml
4. 编写MyBatisUtils工具类，进行数据库的操作
5. 编写UserMapper的测试类