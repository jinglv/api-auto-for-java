# 增删改查(CURD)
## 增
添加记录，id有两种不同的处理方式，
- 自增长，
- Java代码传一个ID进来，这个ID可以是一个UUID，也可以是其他的自定义的ID。

在MyBatis中，对这两种方式都提供了相应的支持

- **主键自增长**
在UserMapper.xml，添加SQL插入语句
```xml
<insert id="addUser" parameterType="com.mybatis.demo.entity.User"> <!--parameterType 表示传入的参数类型。参数都是通过 # 来引用-->
        insert into user (uid, username,address) values (#{uid},#{username},#{address});
</insert>
```
注意，SQL插入完成后，一定要提交，即sqlSession.commit()

- 使用 UUID 做主键
也可以使用UUID做主键，使用UUID做主键，又有两种方式，
    - 第一种，就是在Java代码中生成 UUID，直接作为参数传入到SQL中，这种方式就和传递普通参数一样
    - 第二种，就是使用MySQL自带的UUID函数来生成 UUID
在UserMapper.xml，添加SQL插入语句
```xml
<insert id="addUser2" parameterType="com.mybatis.demo.entity.User">
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

注意：UUID是个String类型，数据类型的匹配，所以在数据的字段uid类型为varchar

## 删
在UserMapper.xml，添加SQL删除语句
```xml
<delete id="deleteUserByUid" parameterType="java.lang.Integer">
    delete
    from user
    where uid = #{uid}
</delete>
```

## 改
在UserMapper.xml，添加SQL修改语句
```xml
<update id="updateUser" parameterType="com.mybatis.demo.entity.User">
    update user
    set username = #{username}
    where uid = #{uid};
</update>
```

## 查
查询一组数据，在UserMapper.xml，添加SQL查询语句
```xml
<select id="getUserAll" resultType="com.mybatis.demo.entity.User">
    select *
    from user;
</select>
```

注意：增，删，改的执行受影响的行数
