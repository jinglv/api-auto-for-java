# 引入Mapper
经过之前的实例，可以看到目前存在的问题

主要问题就是冗余代码过多，模板化代码过多。例如，我想开发一个 StudentDao，可能是下面这样：
```java
package com.test.dao;

import com.test.entity.Student;
import com.test.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @author jingLv
 * @date 2020-04-13 4:27 PM
 */
public class StudentDao {
    private SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getInstance();

    public Student getStudentById(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Student student = (Student) sqlSession.selectOne("com.test.com.test.studentMapper.getStudentById", id);
        sqlSession.close();
        return student;
    }

    public Integer addStudent(Student student) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int insert = sqlSession.insert("com.test.studentMapper.addStudent", student);
        sqlSession.commit();
        sqlSession.close();
        return insert;
    }

    public Integer deleteStudentById(Integer id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int delete = sqlSession.delete("com.test.studentMapper.deleteStudentById", id);
        sqlSession.commit();
        sqlSession.close();
        return delete;
    }

    public Integer updateStudent(Student student) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int delete = sqlSession.delete("com.test.studentMapper.updateStudent", student);
        sqlSession.commit();
        sqlSession.close();
        return delete;
    }

    public List<Student> getAllStudent() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Student> students = sqlSession.selectList("com.test.studentMapper.getStudentAll");
        sqlSession.close();
        return students;
    }
}
```
StudentDao.java对应的StudentMapper.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.test.studentMapper">
    <select id="getStudentById" resultType="com.test.entity.Student">
        select * from t_student where Sid=#{Sid};
    </select>

    <select id="getStudentAll" resultType="com.test.entity.Student">
        select * from t_student;
    </select>

    <insert id="addStudent" parameterType="com.test.entity.Student"> <!--parameterType 表示传入的参数类型。参数都是通过 # 来引用-->
        insert into t_student (Sid,Sname,Sage) values (#{Sid},#{Sname},#{Sage});
    </insert>

    <delete id="deleteStudentById" parameterType="java.lang.Integer">
        delete from t_student where id=#{id}
    </delete>

    <update id="updateStudent" parameterType="com.test.entity.Student">
        update t_student set Sname = #{Sname} where id=#{id};
    </update>
</mapper>
```
此时分析这个StudentDao,每个方法中都要获取SqlSession，涉及到增删改的方法，还需要commit，SqlSession用完之后，还需要关闭，sqlSession执行时需要的参数就是方法的参数，sqlSession要执行的SQL ，和XML中的定义是一一对应的。这是一个模板化程度很高的代码。

既然模板化程度很高，就要去解决它，原理很简单，就是前面Spring中所说的动态代理。此时引入Mapper，简化成一个Mapper接口:StudentMapper.java
```java
package com.test.mapper;

import com.test.entity.Student;

import java.util.List;

/**
 * @author jingLv
 * @date 2020-04-13 5:01 PM
 */
public interface StudentMapper {
    /**
     * 根据学生Id查询学生信息
     *
     * @return 返回学生信息
     */
    Student getStudentById();

    /**
     * 查询所有学生信息
     *
     * @return 返回所有学生信息列表
     */
    List<Student> getStudentAll();

    /**
     * 添加学生信息
     *
     * @return
     */
    Integer addStudent();

    /**
     * 根据学生id删除学生信息
     *
     * @return
     */
    Integer deleteStudentById();

    /**
     * 根据学生id更新学生信息
     *
     * @return
     */
    Integer updateStudent();
}
```
这个接口对应的 Mapper 文件如下
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.test.mapper.StudentMapper">
    <select id="getStudentById" resultType="com.test.entity.Student">
        select * from t_student where Sid=#{Sid};
    </select>

    <select id="getStudentAll" resultType="com.test.entity.Student">
        select * from t_student;
    </select>

    <insert id="addStudent" parameterType="com.test.entity.Student"> <!--parameterType 表示传入的参数类型。参数都是通过 # 来引用-->
        insert into t_student (Sid,Sname,Sage) values (#{Sid},#{Sname},#{Sage});
    </insert>

    <delete id="deleteStudentById" parameterType="java.lang.Integer">
        delete from t_student where Sid=#{Sid}
    </delete>

    <update id="updateStudent" parameterType="com.test.entity.Student">
        update t_student set Sname = #{Sname} where Sid=#{Sid};
    </update>
</mapper>
```
使用这个接口，完全可以代替上面的StudentDao，为什么呢？因为这个接口提供了StudentDao所需要的最核心的东西，根据这个接口，就可以自动生成StudentDao：

- 首先，StudentDao中定义了 SqlSessionFactory，这是一套固定的代码
- StudentMapper所在的包+StudentMapper 类名+StudentMapper中定义好的方法名，就可以定位到要调用的SQL
- 要调用SqlSession中的哪个方法，根据定位到的SQL节点就能确定
因此，我们在 MyBatis 开发中，实际上不需要自己提供StudentDao的实现，我们只需要提供一个StudentMapper即可。

然后，我们在 MyBatis 的全局配置中，配置一下StudentMapper：/resources/mapper/StudentMapper.xml
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
        <package name="com.test.mapper" />
    </mappers>
</configuration>
```
注意：上面的写法需要将StudentMapper.java和StudentMapper.xml放在一个包下

注意，在Maven中，默认情况下，Maven要求我们将XML配置、properties配置等，都放在resources目录下，如果我们强行放在java目录下，默认情况下，打包的时候这个配置文件会被自动忽略掉。对于这两个问题，我们有两种解决办法：
- 不要忽略 XML 配置：
在 pom.xml 中，添加如下配置，让Maven不要忽略我在java目录下的XML配置：
```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
            </includes>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
        </resource>
    </resources>
</build>
```
- 按照Maven的要求来(不知道哪里错误，此方法尝试不可行)
按照Maven的要求来，将xml文件放到resources目录下，但是，MyBatis中默认情况下要求，**Mapper.xml和**Mapper接口，必须放在一起，所以，我们需要手动在resources目录下，创建一个和**Mapper接口相同的目录。