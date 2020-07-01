# MyBatis
- ORM模型简介
    - ORM：对象关系映射（Object Relation Mapping）
- MyBatis概述
    - MyBatis是支持定制化SQL、存储过程以及高级映射的优秀持久层框架
        - 避免传统JDBC硬编码
        - XML配置或注解
        - POJO对象和数据库记录直接映射
        - 完善的文档支持
- MyBatis使用优势及应用场景
    - 简单易学，快速上手，学习成本低
    - 数据库交互信息配置化
    - 动态SQL处理
    - 更加关注SQL优化的项目
    - 需求频繁更新改动的项目
    
# MyBatis 架构介绍
![image](http://m.qpic.cn/psc?/V12A7VgS03zLND/QNsgOSLzUrTyB8UN2gSlSM6iTnCQxKMuWJg.w7Ma4mIOWGITBisiny8xaOS5NH6RtvAEIIRgmvLohhTwjFEseg!!/b&bo=OAPXAQAAAAADB88!&rf=viewer_4)
1. mybatis配置:mybatis-config.xml，此文件作为mybatis的全局配置文件，配置了mybatis的运行环境等信息。另一个mapper.xml文件即sql映射文件，文件中配置了操作数据库的sql语句。此文件需要在mybatis-config.xml中加载。
2. 通过mybatis环境等配置信息构造SqlSessionFactory即会话工厂
3. 由会话工厂创建sqlSession即会话，操作数据库需要通过sqlSession进行。
4. mybatis底层自定义了Executor执行器接口操作数据库，Executor接口有两个实现，一个是基本执行器、一个是缓存执行器。
5. Mapped Statement也是mybatis一个底层封装对象，它包装了mybatis配置信息及sql映射信息等。mapper.xml文件中一个sql对应一个Mapped Statement对象，sql的id即是Mapped statement的id。
6. Mapped Statement对sql执行输入参数进行定义，包括HashMap、基本类型、pojo，Executor通过Mapped Statement在执行sql前将输入的java对象映射至sql中，输入参数映射就是jdbc编程中对preparedStatement设置参数。
7. Mapped Statement对sql执行输出结果进行定义，包括HashMap、基本类型、pojo，Executor通过Mapped Statement在执行sql后将输出结果映射至java对象中，输出结果映射过程相当于jdbc编程中对结果的解析处理过程。
    
## MyBatis所解决的JDBC中存在的问题
1. 数据库链接创建、释放频繁造成系统资源浪费从而影响系统性能，如果使用数据库链接池可解决此问题。解决：在mybatis-config.xml中配置数据链接池，使用连接池管理数据库链接。
2. Sql语句写在代码中造成代码不易维护，实际应用sql变化的可能较大，sql变动需要改变java代码。解决：将Sql语句配置在XXXXmapper.xml文件中与java代码分离。
3. 向sql语句传参数麻烦，因为sql语句的where条件不一定，可能多也可能少，占位符需要和参数一一对应。解决：Mybatis自动将java对象映射至sql语句，通过statement中的parameterType定义输入参数的类型。
4. 对结果集解析麻烦，sql变化导致解析代码变化，且解析前需要遍历，如果能将数据库记录封装成pojo对象解析比较方便。解决：Mybatis自动将sql执行结果映射至java对象，通过statement中的resultType定义输出结果的类型。

# 实战
## 准备测试数据
见 /sql/init.sql

## pom.xml引入依赖
```xml
<dependencies>

    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.3</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.16</version>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.12</version>
    </dependency>

</dependencies>
``` 

## 创建实体类
User.java
```java
package com.test.entity;

import lombok.Data;

/**
 * @author jinglv
 * @date 2020-04-06 14:19
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String address;
}
```

## 在resources目录下创建mybatis-config.xml文件并配置
mybatis-config.xml
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
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql:///test?serverTimezone=Asia/Shanghai"/>
                <property name="username" value="root"/>
                <property name="password" value="123123"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="UserMapper.xml"/>
    </mappers>
</configuration>
```

在这个配置文件中，我们只需要配置environments和mapper即可，environment就是MyBatis所连接的数据库的环境信息，它放在一个environments节点中，意味着environments中可以有多个environment，为社么需要多个呢？开发、测试、生产，不同环境各一个environment，每一个environment都有一个id，也就是它的名字，然后，在environments 中，通过default属性，指定你需要的environment。每一个environment中，定义一个数据的基本连接信息。

在mappers节点中，定义Mapper，配置好的UserMapper.xml

并在resources目录下创建UserMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
创建一个新的mapper ，需要首先给它取一个namespace，这相当于是一个分隔符，因为我们在项目中，
会存在很多个Mapper，每一个 Mapper中都会定义相应的增删改查方法，为了避免方法冲突，也为了便于管理，
每一个Mapper都有自己的namespace，而且这个namespace不可以重复
-->
<mapper namespace="com.test.userMapper">

    <select id="getUserById" resultType="com.test.entity.User">
        select * from user where id=#{id};
    </select>

    <select id="getUserAll" resultType="com.test.entity.User">
        select * from user;
    </select>

    <insert id="addUser" parameterType="com.test.entity.User"> <!--parameterType 表示传入的参数类型。参数都是通过 # 来引用-->
        insert into user (username,address) values (#{username},#{address});
    </insert>

    <insert id="addUser2" parameterType="com.test.entity.User">
        <selectKey resultType="java.lang.String" keyProperty="id" order="BEFORE">
            select uuid();
        </selectKey>
        insert into user (id,username,address) values (#{id},#{username},#{address});
    </insert>

    <delete id="deleteUserById" parameterType="java.lang.Integer">
        delete from user where id=#{id}
    </delete>

    <update id="updateUser" parameterType="com.test.entity.User">
        update user set username = #{username} where id=#{id};
    </update>
</mapper>
```
在 Mapper 中，首先定义一个select ，id表示查询方法的唯一标识符，resultType定义了返回值的类型。在 select 节点中，定义查询 SQL，#{id}，表示这个位置用来接收外部传进来的参数。

## 运行
上面步骤准备好后，写一个Main执行方法
```java
package com.test;

import com.test.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * @author jinglv
 * @date 2020-04-06 14:20
 */
public class Main {

    public static void main(String[] args) throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        // UserMapper.xml文件中的namespace="com.test.userMapper"
        User user = (User) sqlSession.selectOne("com.test.userMapper.getUserById", 3);
        System.out.println(user);
        sqlSession.close();
    }
}
```
- 加载主配置文件，生成一个SqlSessionFactory，再由SqlSessionFactory生成一个SqlSession，一个SqlSession就相当于是我们的一个会话，类似于JDBC中的一个连接，在SQL操作完成后，这个会话是可以关闭的。
- SqlSessionFactoryBuilder用于创建SqlSessionFacoty，SqlSessionFacoty一旦创建完成就不需要SqlSessionFactoryBuilder了，因为SqlSession是通过SqlSessionFactory生产，所以可以将SqlSessionFactoryBuilder当成一个工具类使用，最佳使用范围是方法范围即方法体内局部变量。
- SqlSessionFactory是一个接口，接口中定义了openSession的不同重载方法，SqlSessionFactory的最佳使用范围是整个应用运行期间，一旦创建后可以重复使用，通常以单例模式管理SqlSessionFactory。
- SqlSession中封装了对数据库的操作，如：查询、插入、更新、删除等。通过SqlSessionFactory创建SqlSession，而SqlSessionFactory是通过SqlSessionFactoryBuilder进行创建。SqlSession是一个面向用户的接口， sqlSession中定义了数据库操作，默认使用DefaultSqlSession实现类。每个线程都应该有它自己的SqlSession 实例。SqlSession的实例不能共享使用，它也是线程不安全的。因此最佳的范围是请求或方法范围。绝对不能将SqlSession实例的引用放在一个类的静态字段或实例字段中。打开一个 SqlSession；使用完毕就要关闭它。通常把这个关闭操作放到finally块中以确保每次都能执行关闭。

## 封装一个工具类
```java
public class SqlSessionFactoryUtils {
    private static SqlSessionFactory SQL_SESSION_FACTORY = null;

    public static SqlSessionFactory getInstance() {
        if (SQL_SESSION_FACTORY == null) {
            try {
                SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SQL_SESSION_FACTORY;
    }
}
```
调用
```java
package com.test;

import com.test.entity.User;
import com.test.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


/**
 * @author jinglv
 * @date 2020-04-06 14:20
 */
public class Main {

    public static void main(String[] args) {
        SqlSessionFactory factory = SqlSessionFactoryUtils.getInstance();
        SqlSession sqlSession = factory.openSession();
        // UserMapper.xml文件中的namespace="com.test.userMapper"
        User user = (User) sqlSession.selectOne("com.test.userMapper.getUserById", 3);
        System.out.println(user);
        sqlSession.close();
    }
}
```