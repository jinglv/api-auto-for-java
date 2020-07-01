# 增删改查(CURD)
## 增
添加记录，id有两种不同的处理方式，
- 自增长，
- Java代码传一个ID进来，这个ID可以是一个UUID，也可以是其他的自定义的ID。
在MyBatis中，对这两种方式都提供了相应的支持
- **主键自增长**
在UserMapper.xml，添加 SQL 插入语句
```xml
<insert id="addUser" parameterType="com.test.entity.User"> <!--parameterType 表示传入的参数类型。参数都是通过 # 来引用-->
    insert into user (uid,username,address) values (#{uid},#{username},#{address});
</insert>
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
        User user = new User();
        user.setUid("6");
        user.setUsername("林青霞");
        user.setAddress("台湾");
        SqlSessionFactory factory = SqlSessionFactoryUtils.getInstance();
        SqlSession sqlSession = factory.openSession();
        // UserMapper.xml文件中的namespace="com.test.userMapper"
        int insert = sqlSession.insert("com.test.userMapper.addUser", user);
        System.out.println(insert);
        sqlSession.commit();
        sqlSession.close();
    }
}
```
- 使用 UUID 做主键
也可以使用UUID做主键，使用UUID做主键，又有两种方式，
    - 第一种，就是在Java代码中生成 UUID，直接作为参数传入到SQL中，这种方式就和传递普通参数一样
    - 第二种，就是使用MySQL自带的UUID函数来生成 UUID
在UserMapper.xml，添加SQL插入语句
```xml
<insert id="addUser2" parameterType="com.test.entity.User">
    <selectKey resultType="java.lang.String" keyProperty="id" order="BEFORE">
        select uuid();
    </selectKey>
    insert into user (uid,username,address) values (#{uid},#{username},#{address});
</insert>
```
- selectKey表示查询key
- keyProperty属性表示将查询的结果赋值给传递进来的User对象的id属性
- resultType表示查询结果的返回类型
- order表示这个查询操作的执行时机，BEFORE表示这个查询操作在insert之前执行
- 在selectKey节点的外面定义insert操作

注意：UUID是个String类型，数据类型的匹配

## 删
在UserMapper.xml，添加SQL删除语句
```xml
<delete id="deleteUserById" parameterType="java.lang.Integer">
    delete from user where id=#{id}
</delete>
```
调用
```java
package com.test;

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
        int delete = sqlSession.delete("com.test.userMapper.deleteUserById", 2);
        System.out.println(delete);
        sqlSession.commit();
        sqlSession.close();
    }
}
```
这里的返回值为该 SQL 执行后，数据库受影响的行数。

## 改
在UserMapper.xml，添加SQL修改语句
```xml
<update id="updateUser" parameterType="com.test.entity.User">
    update user set username = #{username} where id=#{id};
</update>
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
        User user = new User();
        user.setId(1);
        user.setUsername("碧昂丝");
        SqlSessionFactory factory = SqlSessionFactoryUtils.getInstance();
        SqlSession sqlSession = factory.openSession();
        // UserMapper.xml文件中的namespace="com.test.userMapper"
        int update = sqlSession.update("com.test.userMapper.updateUser", user);
        System.out.println(update);
        sqlSession.commit();
        sqlSession.close();
    }
}
```
调用的返回值，也是执行 SQL 受影响的行数。

## 查
查询一组数据，在UserMapper.xml，添加SQL查询语句
```xml
<select id="getUserAll" resultType="com.test.entity.User">
    select * from user;
</select>
```
调用
```java
package com.test;

import com.test.entity.User;
import com.test.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author jinglv
 * @date 2020-04-06 14:20
 */
public class Main {

    public static void main(String[] args) {
        SqlSessionFactory factory = SqlSessionFactoryUtils.getInstance();
        SqlSession sqlSession = factory.openSession();
        // UserMapper.xml文件中的namespace="com.test.userMapper"
        List<User> users = sqlSession.selectList("com.test.userMapper.getUserAll");
        System.out.println(users);
        sqlSession.commit();
        sqlSession.close();
    }
}
```
