# 什么是JSON
JSON是一种与开发语言无关的额、轻量级的数据格式。全程JavaScript Object Notation。

优点：
- 易于人的**阅读和编写**，易于程序**解析与生产**

## 标准的JSON数据表示
- 数据**结构**
    - Object
    - Array
- 基本**类型**
    - string
    - number
    - true
    - false
    - null
    
### 数据结构-Object
使用花括号{}包含的键值对结构，Key**必须是string类型**，value为任何**基本类型**或**数据结构**
![image](http://m.qpic.cn/psc?/V12A7VgS03zLND/6RAq0V9V8Td2AB7JS6C71IKGIC3wbVjzUVD0Q8rjnmuXxz8iHh7xySvQ8CLysOqmiMptnym1Rq84BkUviTgL8ASCE79PH8nlWevMpSTB5S8!/b&bo=mATYAAAAAAADF3Y!&rf=viewer_4)

### 数据结构-Array
使用**中括号[]**开起始，并用**逗号**，开分隔元素
![image](http://m.qpic.cn/psc?/V12A7VgS03zLND/6RAq0V9V8Td2AB7JS6C71Iw0XcQqHifUxWQspoOQC6eUUFKd31XeYT6qvjdFZ.x9tRDm9f7QcBwCNfvHj*SRDbwyU6Edqil7ZFYl8kR7HmA!/b&bo=0gQGAQAAAAADF.M!&rf=viewer_4)

### 基本类型
string、number（无浮点型）、true、false、null

## JSON数据演示
```json
{
  "name": "刘滋滋",
  "age": 18,
  "birthday": "1990-01-01",
  "school": "蓝翔",
  "major": [
      "理发",
      "挖掘机"
  ],
  "has_boyfriend": false,
  "car": null,
  "house": null,
  "comment": "这是一个注释"
}
```

## JSON使用
[JSON官方网站](http://json.org/json-zh.html)

## GSON使用
