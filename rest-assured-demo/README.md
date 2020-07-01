# 接口文档

# Rest-Assured
[rest-assured官方文档](https://github.com/rest-assured/rest-assured/wiki/Usage)
## Rest-Assured介绍
Rest-Assured 是一个测试 RESTful Web Services 的 Java 类库。可以使用 Rest-Assured 编写高度自定义化的 HTTP 请求用来测试各种各样 Restful 服务组合的业务实现。

Rest-Assured 同样能够验证从服务器返回的 HTTP 响应报文，例如服务器响应状态码，响应报文内容等，Rest-Assured 可以灵活的用来进行 Restful Webservice 测试。

用 Java 做接口自动化测试首选 REST Assured，具体原因如下：
- 开源
- 简约的接口测试 DSL
- 支持 xml json 的结构化解析
- 支持 xpath jsonpath gpath 等多种解析方式
- 对 spring 的支持比较全面

## Rest-Assured使用

### Maven工程添加依赖
pom.xml添加依赖
```
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>${rest-assured.version}</version>
</dependency>

<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-path</artifactId>
    <version>${rest-assured.version}</version>
</dependency>
```

### 基本三步曲
接口进行测试一般由三步曲
- 传参
- 发请求
- 响应结果断言

REST Assured给我们提供了清晰的三步曲，以**given、when、then**的结构来实现，基本写法如下：
```
//使用参数
given().
    param("key1", "value1").
    param("key2", "value2").
when().
    post("/somewhere").
then().
    body(containsString("OK"))

//使用X-Path (XML only) 
given().
    params("firstName", "John", "lastName", "Doe").
when().
    post("/greetMe").
then().
    body(hasXPath("/greeting/firstName[text()='John']"))
```

### 分步解析
#### 分步拆解一：Given
发送请求经常需要带有参数，使用given()就可以实现，如下常用的传入方法：
- param

通常我们都会使用 given().param 方法来传参，REST Assured 会根据 HTTP 方法自动尝试确定哪种参数类型（即查询或表单参数），如果是 GET，则查询参数将自动使用，如果使用 POST，则将使用表单参数；

- queryParam 和 formParam

有时候在 PUT 或 POST 请求中，需要区分查询参数和表单参数时，就需要使用queryParam 和 formParam 方法了，具体写法如下：
```
given().
       formParam("formParamName", "value1").
       queryParam("queryParamName", "value2").
when().
       post("/something")
```
- pathParam

使用given时指定请求路径的参数，这个方法很少用到，具体写法如下：
```
given().
        pathParam("OAuth", "oauth").
        pathParam("accessToken", "token").
when(). 
        post("/auth/{OAuth}/{accessToken}").
then().
         ..
```
- header/headers

经常还需要在请求头中带入参数，这个时候就可以使用header或headers方法，写法如下：
```
given()
       .header("Authorization","Basic c3lzdGVtOxxxbQ==")
       .header("Host","47.xxx.xxx.133")
```
或者用headers将多个参数写在一起：
```
given()
       .headers("Authorization","Basic c3lzdGVtxxx3RlbQ==","Host","47.xxx.xxx.133")
```
- cookie

有时候需要在请求中带入cookie，restassured提供了cookie方法来实现：
```
given()
      .cookie("c_a","aaaaaa")
      .cookie("c_b","bbbbbb"). ..
```
- body

在POST, PUT 或 DELETE请求中，我们经常还需要带上请求体body，写法如下：
```
given().body("{\n" +
                "\t\"password\": \"elcrD28xxxR0VLs/jERA\\u003d\\u003d\\n\",\n" +
                "\t\"grant_type\": \"password\",\n" +
                "\t\"scope\": \"server\",\n" +
                "\t\"userType\": 1,\n" +
                "\t\"username\": \"xxx\"\n" +
                "}")
```
也可以用request更为明确的指出是请求body：
```
given().request().body("{\n" +
                "\t\"password\": \"elcrD28xxxR0VLs/jERA\\u003d\\u003d\\n\",\n" +
                "\t\"grant_type\": \"password\",\n" +
                "\t\"scope\": \"server\",\n" +
                "\t\"userType\": 1,\n" +
                "\t\"username\": \"xxx\"\n" +
                "}")
```
- 没有参数

如果我们没有参数需要传递，也可以省略掉given()：
```
get("/lotto").then().assertThat().body("lotto.lottoId", equalTo(5));
```
- proxy

有时候我们需要进行接口的调试，抓包是最常用的一种方式，rest-assured 提供了 proxy 方法，可以设置代理，写法如下：
```
given().proxy("127.0.0.1",8888). ..
```
#### 分步拆解二：When
when主要用来触发请求，在when后面接着请求URL：
```
given().when().post("http://47.103.xxx.133/auth/oauth/token"). ..
```
前面在given中我们设置了很多请求参数，在when中也可以设置，只不过要注意的是在请求之前设置；这也比较好理解，如果再请求之后的话，参数都设置怎么发请求呢？
```
given()
    .when()
        .contentType(ContentType.JSON)
        .headers("Authorization","Basic c3lzxxx3RlbQ==","Host","47.xxx.xxx.133")
        .request().body("{\n" +
            "\t\"password\": \"elcrD28ZSLLtR0VLs/jERA\\u003d\\u003d\\n\",\n" +
            "\t\"grant_type\": \"password\",\n" +
            "\t\"scope\": \"server\",\n" +
            "\t\"userType\": 1,\n" +
            "\t\"username\": \"qinzhen\"\n" +
            "}")
        .post("http://47.xxx.xxx.133/auth/oauth/token")
     . ..
```
#### 分步拆解二：Then
then后面可以跟断言，也可以获取响应值

- 断言-then().body()

then().body() 可以对响应结果进行断言，在 body 中写入断言：
```
.. post("http://47.xxx.xxx.133/auth/oauth/token")
   .then().statusCode(200).body("code",equalTo(1));
```
其中statusCode(200)是对状态码的断言，判断状态码是否为200;body("code",equalTo(1))是对返回体中的code进行断言，要求返回code值为1

注意:这里的equalTo使用的是hamcrest断言

- 获取响应-then().extract().body().path("code")

我们可以在then后面利用.extract().body() 来获取我们想要body的返回值，它们也可以直接接在断言后面，写法如下：
```
.. .then()
        .log().all().statusCode(200).body("code",equalTo(1))
        .extract().body().path("code");
```
注意:这里的body() 不要和请求体body()以及断言的body()混淆了

### JSON断言
### XML断言
### JsonSchema断言
[JsonSchema官方文档](https://json-schema.org/understanding-json-schema/)

在实际工作中，对接口返回值进行断言校验，除了常用字段的断言检测以外，还要对其他字段的类型进行检测，原因在于：
- 返回字段较多，无法保证每个字段都写断言
- 防止客户端未做 null 值的校验判断，如果因为版本变更或网络等原因造成某个不能接收 null 值的返回字段为 null，就很有可能造成软件的崩溃
- 某些数值是不能为负的
- 小数点保留位数，对于股票的交易、医疗数据的分析，小数点的精确度都是有其实际价值的

对返回的字段一个个写断言显然是非常耗时的，这个时候就需要一个模板，可以定义好数据类型和匹配条件，除了关键参数外，其余可直接通过此模板来断言（JsonSchema）

#### JsonSchema模板生成
1. 首先要借助于Json schema tool的网站https://www.jsonschema.net/，将返回json字符串复制到页面左边，然后点击INFER SHCEMA,就会自动转换为schema json文件类型,会将每个地段的返回值类型都设置一个默认类型; 在pattern中也可以写正则进行匹配 
2. 点击“设置”按钮会出现各个类型返回值更详细的断言设置，这个就是schema最常用也是最实用的功能，也可以对每种类型的字段最更细化的区间值校验或者断言，例如长度，取值范围等，具体感兴趣的话可以从官网学习深入学习；平常对重要字段的校验我通常会选用其他断言，比如hamcrest断言 
3. 选择复制功能，可以将生成的schema模板保存下来 

#### rest-assured结合使用
添加maven依赖，在rest-assured完成支持
```xml
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>json-schema-validator</artifactId>
    <version>4.0.0</version>
</dependency>
```
使用matchesJsonSchemaInClasspath方法对响应结果进行schema断言

### Response结果处理
#### extract().path()
extract是我们获取返回值的核心，通过它来指明后面需要获取的内容，path()中的语法同断言时的JsonPath一致

#### extract().asString()
利用extract().asString()先将响应结果以json字符串的形式保存下来，再一一根据需要获取

#### extract().response()
利用extract().response()来讲所有的response信息都保存成一个Response对象