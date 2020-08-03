# JSONPath

[JSONPath官方地址](https://goessner.net/articles/JsonPath/)

[JSONPath在线测试1](http://jsonpath.com)

[JSONPath在线测试2](http://www.e123456.com/aaaphp/online/jsonpath/)



## 介绍

类似于XPath在xml文档中的定位，JsonPath表达式通常是用来路径检索或设置Json的。其表达式可以接受

"dot–notation"

```
$.store.book[0].title
```

"bracket–notation"格式

```
$['store']['book'][0]['title']
```

## JsonPath的来源

看它的名字你就能知道，这家伙和JSON文档有关系，正如XPath之于XML文档一样，JsonPath为Json文档提供了解析能力，通过使用JsonPath，你可以方便的查找节点、获取想要的数据，JsonPath是Json版的XPath。

## JsonPath语法

JsonPath的语法相对简单，它采用开发语言友好的表达式形式，如果你了解类C语言，对JsonPath就不会感到不适应。

JsonPath语法要点：

- `$` 表示文档的根元素
- `@` 表示文档的当前元素
- `.node_name` 或 `['node_name']` 匹配下级节点
- `[index]` 检索数组中的元素
- `[start:end:step]` 支持数组切片语法
- `*` 作为通配符，匹配所有成员
- `..` 子递归通配符，匹配成员的所有子元素
- `()` 使用表达式
- `?()`进行数据筛选



## XPath与JSONPath

- XPath是一种XML遍历的语法，可以从XML文档中提取特定的元素、属性、数据。
- JSONPath是类似xPath的查询语法，可以从Json文档中提取特定的元素、属性、数据。

| XPath | JSONPath           | 描述                                                         |
| ----- | ------------------ | ------------------------------------------------------------ |
| /     | $                  | 根节点                                                       |
| .     | @                  | 当前元素                                                     |
| /     | . or []            | 匹配下级元素                                                 |
| ..    | N/A                | 匹配上级元素，JsonPath不支持此操作符                         |
| //    | ..                 | 递归匹配所有子元素                                           |
| *     | *                  | 通配符，匹配下级元素                                         |
| @     | N/A                | 匹配属性，JsonPath不支持此操作符                             |
| []    | []                 | 下标运算符，根据索引获取元素，**XPath索引从1开始，JSONPath索引从0开始** |
| \|    | [,]                | 连接操作符，将多个结果拼接成数组返回，可以使用索引或别名     |
| N/A   | `[start:end:step]` | 数据切片操作，XPath不支持                                    |
| []    | ?()                | 过滤表达式                                                   |
| N/A   | ()                 | 脚本表达式，使用底层脚本引擎，XPath不支持                    |
| ()    | N/A                | 分组，JsonPath不支持                                         |

注意：

- JsonPath的索引从0开始计数
- JsonPath中字符串使用单引号表示，例如:`$.store.book[?(@.category=='reference')]`中的`'reference'`

## 示例

### JSON文档如下

```json
{
	"store": {
		"book": [{
			"category": "reference",
			"author": "Nigel Rees",
			"title": "Sayings of the Century",
			"price": 8.95
		}, {
			"category": "fiction",
			"author": "Evelyn Waugh",
			"title": "Sword of Honour",
			"price": 12.99
		}, {
			"category": "fiction",
			"author": "Herman Melville",
			"title": "Moby Dick",
			"isbn": "0-553-21311-3",
			"price": 8.99
		}, {
			"category": "fiction",
			"author": "J. R. R. Tolkien",
			"title": "The Lord of the Rings",
			"isbn": "0-395-19395-8",
			"price": 22.99
		}],
		"bicycle": {
			"color": "red",
			"price": 19.95
		}
	}
}
```

### 文档解析

| XPath                | JSONPath                                 | Result                                   |
| -------------------- | ---------------------------------------- | ---------------------------------------- |
| /store/book/author   | $.store.book[*].author                   | 所有book的author节点                     |
| //author             | $..author                                | 所有author节点                           |
| /store/*             | $.store.*                                | store下的所有节点，book数组和bicycle节点 |
| /store//price        | $.store..price                           | store下的所有price节点                   |
| //book[3]            | $..book[2]                               | 匹配第3个book节点                        |
| //book[last()]       | `$..book[(@.length-1)]`或 `$..book[-1:]` | 匹配倒数第1个book节点                    |
| //book[position()<3] | `$..book[0,1]`或 `$..book[:2]`           | 匹配前两个book节点                       |
| //book[isbn]         | $..book[?(@.isbn)]                       | 过滤含isbn字段的节点                     |
| //book[price<10]     | $..book[?(@.price<10)]                   | 过滤`price<10`的节点                     |
| //*                  | $..*                                     | 递归匹配所有子节点                       |

## Java中JSONPath使用

### 项目搭建

- 搭建maven项目，并在pom.xml文件引入如下依赖

  ```xml
  <dependency>
      <groupId>com.jayway.jsonpath</groupId>
      <artifactId>json-path</artifactId>
      <version>2.4.0</version>
  </dependency>
  ```

### json-path

[json-path github](https://github.com/json-path/JsonPath)

#### 操作符

| 符号                     | 描述                                                         |
| ------------------------ | ------------------------------------------------------------ |
| $                        | 查询的根节点对象，用于表示一个json数据，可以是数组或对象     |
| @                        | 过滤器断言（filter predicate）处理的当前节点对象，类似于java中的this字段 |
| *                        | 通配符，可以表示一个名字或数字                               |
| ..                       | 可以理解为递归搜索                                           |
| `.<name>`                | 表示一个子节点                                               |
| `['<name>' (,'<name>')]` | 表示一个或多个子节点                                         |
| `[<number> (,<number>)]` | 表示一个或多个数组下标                                       |
| `[start:end]`            | 数组片段，区间为[start,end),不包含end                        |
| `[?(<expression>)]`      | 过滤器表达式，表达式结果必须是boolean                        |

#### 函数

可以在JsonPath表达式执行后进行调用，其输入值为表达式的结果。

| 名称     | 描述                     | 输出    |
| -------- | ------------------------ | ------- |
| min()    | 获取数值类型数组的最小值 | Double  |
| max()    | 获取数值类型数组的最大值 | Double  |
| avg()    | 获取数值类型数组的平均值 | Double  |
| stddev() | 获取数值类型数组的标准差 | Double  |
| length() | 获取数值类型数组的长度   | Integer |

####  过滤器

过滤器是用于过滤数组的逻辑表达式，一个通常的表达式形如：[?(@.age > 18)]，可以通过逻辑表达式&&或||组合多个过滤器表达式，

例如

注意：字符串必须用单引号或双引号包围

```
[?(@.price < 10 && @.category == 'fiction')]
[?(@.color == 'blue')]
[?(@.color == "blue")]
```

| 操作符 | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| ==     | 等于符号，但数字1不等于字符1(note that 1 is not equal to '1') |
| !=     | 不等于符号                                                   |
| <      | 小于符号                                                     |
| <=     | 小于等于符号                                                 |
| \>     | 大于符号                                                     |
| \>=    | 大于等于符号                                                 |
| =~     | 判断是否符合正则表达式，例如[?(@.name =~ /foo.*?/i)]         |
| in     | 所属符号，例如[?(@.size in ['S', 'M'])]                      |
| nin    | 排除符号                                                     |
| size   | size of left (array or string) should match right            |
| empty  | 判空符号                                                     |

### 演示示例
|JsonPath |结果|
|--------|---|
|$.store.book[*].author|获取json中store下book下的所有author值|
|$..author|获取所有json中所有author的值|
|$.store.*|所有的东西，书籍和自行车|
|$.store..price|获取json中store下所有price的值|
|$..book[2]|获取json中book数组的第3个值|
|$..book[-2]|倒数的第二本书|
|$..book[0,1]|前两本书|
|$..book[:2]|从索引0（包括）到索引2（排除）的所有图书|
|$..book[1:2]|从索引1（包括）到索引2（排除）的所有图书|
|$..book[-2:]|获取json中book数组的最后两个值|
|$..book[2:]|获取json中book数组的第3个到最后一个的区间值|
|$..book[?(@.isbn)]|获取json中book数组中包含isbn的所有值|
|$.store.book[?(!@.isbn)]|获取json中book数组中不包含isbn的所有值|
|$.store.book[?(@.price < 10)]|获取json中book数组中price<10的所有值|
|$..book[?(@.price <= $['expensive'])]|获取json中book数组中price<=expensive的所有值|
|$..book[?(@.author =~ /.*REES/i)]|获取json中book数组中的作者以REES结尾的所有值（REES不区分大小写）|
|$..*|逐层列出json中的所有值，层级由外到内|
|$..book.length()|获取json中book数组的长度|