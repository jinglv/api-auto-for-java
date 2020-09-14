# WireMock
WireMock是一款开源的Mock框架，可以Mock基于HTTP和HTTPS的API。官网提供了多种搭建WireMock的方式，以下讲解的是通过引入Jar包方式搭建WireMock服务。

WireMock 有以下优点：
- 支持本地单独部署
- 丰富的匹配策略
- 支持接口录制
- 支持场景定义
- 本身支持 API 调用
- 提供 Java 库，易于在代码中使用

[WireMock官网](http://wiremock.org)

## WireMock的安装
官网下载的jar包或者Maven或Gradle项目中添加依赖（具体使用参照官网）

## WireMock命令行用法
WireMock的独立版本是一个Jar包，所以运行Wiremock自然是需要Java基础环境，命令如下
```shell
jingdeMacBook-Pro:2.27.0 apple$ java -jar wiremock-jre8-standalone-2.27.0.jar
```
命令行支持的一些主要参数及其作用说明如下：
- `--port`设置Mock服务的http端口，默认启动在8080端口。如果设置为0，则自动确定端口。
- `--https-port`设置https的端口。
- `--verbose`在终端显示详细的日志信息。
- `--root-dir`设置 mappings 和 __files 目录的工作路径
- `--enable-browser-proxying`以浏览器代理的方式运行。

简单来说，WireMock会在本地启动一个侦听指定端口的web服务，这里指定的端口可以用--port指定http协议或--https-port指定https协议端口。启动后我们发到指定端口的请求，就会由WireMock来完成响应，达到接口Mock的目的。

命令行启动后如图：
```shell script
jingdeMacBook-Pro:2.27.0 apple$ java -jar wiremock-jre8-standalone-2.27.0.jar
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
 /$$      /$$ /$$                     /$$      /$$                     /$$
| $$  /$ | $$|__/                    | $$$    /$$$                    | $$
| $$ /$$$| $$ /$$  /$$$$$$   /$$$$$$ | $$$$  /$$$$  /$$$$$$   /$$$$$$$| $$   /$$
| $$/$$ $$ $$| $$ /$$__  $$ /$$__  $$| $$ $$/$$ $$ /$$__  $$ /$$_____/| $$  /$$/
| $$$$_  $$$$| $$| $$  \__/| $$$$$$$$| $$  $$$| $$| $$  \ $$| $$      | $$$$$$/
| $$$/ \  $$$| $$| $$      | $$_____/| $$\  $ | $$| $$  | $$| $$      | $$_  $$
| $$/   \  $$| $$| $$      |  $$$$$$$| $$ \/  | $$|  $$$$$$/|  $$$$$$$| $$ \  $$
|__/     \__/|__/|__/       \_______/|__/     |__/ \______/  \_______/|__/  \__/

port:                         8080
enable-browser-proxying:      false
disable-banner:               false
no-request-journal:           false
verbose:                      false
```
启动后，我们在本地运行目录下会看到WireMock会自动生成`__files`和`mappings`两个目录。这两个目录中存放的就是 Mock 模拟的接口匹配内容了。
- `__files`存放接口响应中会用到的一些文件资源
- `mappings`存放接口响应匹配规则

## 模拟 JSON 格式接口
### 模拟GET请求
模拟一个接口，总的来说就是配置 mapping 文件，mapping 文件中又分为 Request 的配置和 Response 的配置，我们先从 Request 配置进行讲解。Request 配置我们主要介绍method，urlpath， QueryParameters ，BodyPatterns 的配置。method作用是配置接口的请求方法，值包含 GET、POST、PUT、DELETE 等。

**urlPath**作用是配置接口的路径参数，这里可以有两种方式进行控制:
- urlPattern 路径中无查询参数的接口
- urlPathPattern 路径中有查询参数的接口

#### 配置路径中无请求参数
在mapping文件配置了一个**GET**请求的接口文档getBook.json，路径参数使用**urlPattern**方式：
```json
{
  "request": {
    "method" : "GET",
    "urlPattern": "/api/book/([a-z]*)"
  },
  "response": {
    "status": 200,
    "body": "get book with url pattern successfully"
  }
}
```
从mapping文件看，我们模拟了一个 GET 请求的接口，接口的地址是/api/getBook/([a-z]* ),这里[a-z]*是正则表达式，表示可以输入多个a-z的任意字符。配置好 mapping 文件后，启动 WireMock 服务（如果服务已经启动，修改 mapping 文件后需要重启 WireMock 服务，新配置的 mapping 文件才会生效）。

配置完成后，启动Application.java后，可以访问接口 http://localhost:9090/api/getBook/abc 查看结果

#### 配置路径中有请求参数
在mapping文件配置了一个**GET**请求的接口文档getBookByPathPatter.json，路径参数使用**urlPathPattern**方式：
```json
{
  "request": {
    "method" : "GET",
    "urlPathPattern": "/api/book/path/([a-z]*)*)" 
  },
  "response": {
    "status": 200,
    "body": "get book with url path pattern successfully"
  }
}
```
配置完成后，启动Application.java后，可以访问接口http://localhost:9090/api/getBookByPathPatter/abc?name=mimi查看结果，或者多加参数http://localhost:9090/api/getBookByPathPatter/abc?name=mimi&age=25都可执行成功

#### 如果期望模拟的接口的查询参数只能是固定的名称应该如何配置呢
例如：GET请求接口中只能输入name和Price两个查询参数，对于这样的接口可以采用**QueryParameters**进行配置。

在mapping文件配置了一个**GET**请求的接口文档getBookByQueryParam.json，路径参数使用**urlPathPattern**方式：
```json
{
  "request": {
    "method": "GET",
    "urlPathPattern": "/api/book/param/([a-z]*)",
    "QueryParameters": {
      "name": {
        "matches": "[a-z]*"
      },
      "price": {
        "matches": "[0-5]*"
      }
    }
  },
  "response": {
    "status": 200,
    "body": "get book with url path pattern and  QueryParameters  successfully"
  }
}
```
配置完成后，启动Application.java后，可以访问接口http://localhost:9090/api/getBookByQueryParam/abc?name=mimi&price=5查看结果

如果price的值是8时调用接口失败，因为mapping文件中设置了price只能是0-5的数字。当添加age这个查询参数时，调用接口也失败，因为mapping文件中设置了只接收name和price两个查询参数。

以上就是**模拟GET请求**接口的几种方式，我们总结一下。
- 如果模拟的接口无查询参数，那么使用 urlPattern 即可。
- 如果模拟的接口有查询参数，但对查询参数的名字和个数无任何限制，那么使用urlPathPattern即可。
- 如果模拟的接口有查询参数，且对查询参数的名称和值都有限制，那么需要在mapping文件中添加queryParameters进行控制。

### 模拟POST请求
模拟一个POST请求的接口，接口的Request Body体如下，price的价格小于或等于200
```json
{
  "books": [{
       "name":"三国演义",
       "price":100,
       "author":"罗贯中"
      },
      {
        "name":"红楼梦",
        "price":120,
        "author":"曹雪芹"
      }
  ],
  "comment":"test post request body"
}
```
可以看到上面的Body体是JSON格式，一级属性中有books和comment，books的值是一个数组对象，数组对象里面每个值又是一个JSON对象。对于该类接口，可以使用**bodyPatterns**进行模拟

在mapping文件配置了一个**POST**请求的接口文档postAddBookWithBodyPatter.json：
```json
{
  "request": {
    "method": "POST",
    "urlPathPattern": "/api/book/body/pattern/([a-z]*)",
    "bodyPatterns":[{
      "matchesJsonPath": "$.books",
      "matchesJsonPath": "$.comment",
      "matchesJsonPath": "$..name",
      "matchesJsonPath": "$..price",
      "matchesJsonPath": "$..author",
      "matchesJsonPath": "$..[?(@.price<200)]"
    }]
  },
  "response": {
    "status": 200,
    "body": "add book with bodyPatterns successfully"
  }
}
```
"matchesJsonPath": \(.books中\)表示 JSON 对象根目录，\(.books表示根目录中存在名称为 books 的一级属性，\)..[?(@.price<200)]表示存在名称为 price 的属性，且该属性的值小于200.

配置完成后，启动Application.java后，可以访问接口http://localhost:9090/api/addBookWithBodyPatter/test, 请求主体Json body，查看结果

除配置Request Body外还可以配置request中的headers和接口的访问权限。如下配置了Request 的 headers 和接口访问权限，header中Content-Type是application/json，接口权限是 basicAuth

在mapping文件配置了一个**POST**请求的接口文档postAddBookWithBasicAuth.json：
```json
{
  "request": {
    "method": "POST",
    "urlPathPattern": "/api/book/auth/([a-z]*)",
    "bodyPatterns":[{
      "matchesJsonPath": "$.books",
      "matchesJsonPath": "$.comment",
      "matchesJsonPath": "$..name",
      "matchesJsonPath": "$..price",
      "matchesJsonPath": "$..author",
      "matchesJsonPath": "$..[?(@.price<200)]"
    }],
    "headers": {
      "Content-Type": {"equalTo":"application/json"}
    },
    "basicAuth": {
      "username": "apiUsername",
      "password": "apiPassword"
    }
  },
  "response": {
    "status": 200,
    "body": "add book with bodyPatterns successfully"
  }
}
```
配置完成后，启动Application.java后，可以访问接口http://localhost:9090/api/addBookWithBasicAuth/test, 请求主体Json body，添加auth，请求查看结果

#### 配置接口的Response，相比Request，Response的配置简单很多，对于Response的Body有两种写法
- Body后面直接写内容
```json
"response": {
    "status": 200,
    "body": "{\"code\": 0, \"message\": \"successfully\"}"
  }
```
-  bodyFileName 的方式
fileName 指在__files录下的文件名称。如下是采用bodyFileName的方式定义Response，表示Response Body内容为WireMock服务的src/resources/mock/__files目录中bookDetails.json文件内容
```json
{
  "code": 0,
  "message": "successfully"
}
```
在mapping文件配置了一个**POST**请求的接口文档postAddBookWithBasicAuthByFiles.json：
```json
{
  "request": {
    "method": "POST",
    "urlPathPattern": "/api/book/auth/([a-z]*)",
    "bodyPatterns": [
      {
        "matchesJsonPath": "$.books",
        "matchesJsonPath": "$.comment",
        "matchesJsonPath": "$..name",
        "matchesJsonPath": "$..price",
        "matchesJsonPath": "$..author",
        "matchesJsonPath": "$..[?(@.price<200)]"
      }
    ],
    "headers": {
      "Content-Type": {
        "equalTo": "application/json"
      }
    },
    "basicAuth": {
      "username": "apiUsername",
      "password": "apiPassword"
    }
  },
  "response": {
    "status": 200,
    "bodyFileName": "bookDetails.json",
    "headers": {
      "Content-Type": "application/json"
    }
  }
}
```
配置完成后，启动Application.java后，可以访问接口http://localhost:9090/api/addBookWithBasicAuth/test, 请求主体Json body，添加auth，请求查看结果

## 模拟 XML 格式接口
与json区别的地方是 BodyPatterns的配置，XML格式的接口在mapping文件中配置bodyPatterns时有两种方式：
- 第一种是使用equalToxml方式
假如我们需要Mock一个Post请求接口，接口的Body体如下，且Body中每个字段以及字段的值都是固定的。
```xml
<person>
    <firstName>Done</firstName>
    <lastName>Jone</lastName>
</person>
```
针对这样的接口我们可以采用equalToxml的方式，mapping文件内容如下，可以看到和Mock JSON格式的接口相比，只是把header中Content-Type定义为application/xml，BodyPattern中使用equalToXml方式定义 Request Body内容。

在mapping文件配置了一个**POST**请求的接口文档postAddPersonByXml.json：
```json
{
  "request": {
    "method": "POST",
    "urlPathPattern": "/api/user/xml/([a-z]*)",
    "headers": {
      "Content-Type": {
        "equalTo": "application/xml"
      }
    },
    "bodyPatterns": [{
      "equalToXml": "<person><firstName>Done</firstName><lastName>Jone</lastName></person>"
    }]
  },
  "response": {
    "status": 200,
    "body": "<code>0</code><message>add person with equalToXml successfully</message>",
    "headers": {
      "Content-Type": "application/xml"
    }
  }
}
```
配置完成后，启动Application.java后，可以访问接口http://localhost:9090/api/addPersonByXml/test, 请求主体xml body，请求查看结果

- 第二种是使用matchesXmlPath方式。
例如如下接口，我们只想控制 Request Body中price字段的值包含数字1，Body体内容如下
```xml
<book>
    <bookName>test</bookName>
    <price>12</price>
</book>
```
对于这样的接口使用matchesXmlPath配置mapping文件，在mapping文件配置了一个**POST**请求的接口文档postAddXmlUser.json：
```json
{
  "request": {
    "method": "POST",
    "urlPathPattern": "/api/user/add",
    "headers": {
      "Content-Type": {
        "equalTo": "application/xml"
      }
    },
    "bodyPatterns": [
      {
        "matchesXPath": {
          "expression": "//price/text()",
          "contains": "1"
        }
      }
    ]
  },
  "response": {
    "status": 200,
    "body": "add user with xpath bodyPatterns successfully"
  }
}
```
上面的mapping文件我们定义了content-type必须是application/xml，其次我们定义了Request Body中存在节点price，且price节点的值需要包含数字1.

配置完成后，启动Application.java后，可以访问接口http://localhost:9090/api/addXmlUser/test, 请求主体xml body，请求查看结果

### 总结
- 如果对接口的Body体内容有严格要求（字段名称，字段值都有限制）那么可以采用equalToXml方式定义Request Body 。
- 如果只对接口Body体局部字段值有控制，那么可以采用matchesXpath的方式进行定义。
- 如果对Request Body无任何要求，那么不配置bodyPatterns，只需把Header 中content-Type设置为application/xml即可。