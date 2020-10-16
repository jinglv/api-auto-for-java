# 测试报告 -- ReportNG
- 作为一个简单的测试报告插件，它是创造来替代TestNG原有的HTML报告
- ReportNG提供了一个简易、色彩分明的测试结果报告

## 使用ReportNG
1. 引入ReportNG的依赖
2. 修改监听
3. 执行test case
    - mvn clean test
    - 报错：`org.apache.maven.surefire.booter.SurefireBooterForkException: There was an error in the forked process
          com/google/inject/Injector`
    - 解决报错：引入对应的依赖
    ```xml
   <!-- https://mvnrepository.com/artifact/com.google.inject/guice -->
   <dependency>
       <groupId>com.google.inject</groupId>
       <artifactId>guice</artifactId>
       <version>4.2.3</version>
   </dependency> 
   ```
    
