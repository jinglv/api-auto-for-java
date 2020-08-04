# Groovy学习
[Groovy官方文档](http://groovy-lang.org)

## Groovy基础知识
- Groovy 语法
- Groovy 闭包
- Groovy 集合处理
- Groovy 操作数据库
- Groovy 文件操作
- Groovy 脚本文件

### Groovy 语法
列举了 9 个 Groovy语法上的特点

1. 代码结尾处无需使用";"
2. 可以不用显示定义数据类型，所有数据类型都可以用 def 定义
3. 方法返回值前无需添加 return 关键字，如果方法不用 void 修饰，方法内的最后一行返回值即函数的返回值
4. 可以指定方法中参数默认值，方法中的参数可以不指定数据类型
5. 所有方法默认都是 public，无需添加 public 关键字
6. 方法的()可以取消
7. Gstring：字符串中支持变量解析和换行处理
8 任何对象都可以被强制转换为布尔值，任何为 null、void 的对象，等同于 0 或空值都会解析为 false，反之则为 true
9. Groovy 中的“ == ”是 Java 中的 equal，如果需要判断两个对象值是否相等使用“==”

### Groovy闭包
Groovy 官方对闭包的定义是“闭包是一个匿名代码块，可接受参数，返回值并可分配给变量”，闭包使用{},{clouserParameter->statement}，clouserParameter指闭包接受的参数，多个参数用逗号隔开，statement指闭包中的代码。

数据集处理就用到了闭包，例如 Groovy 语言自带的 find，findAll 等方法就是闭包

### 数据集处理
接口测试中有大量对JSON对象解析的场景，要掌握好这个首先得学习Groovy中数据集的处理

### Groovy操作数据库
- DataSources 负责数据库的连接
- ConstantSql 存放 SQL 常量
- DataRepository 存放操作数据库的方法

### Groovy操作文件
- 读取写入 TXT 文件
- 读取 yml 文件
- 读取 CSV 文件
- 读取 JSON 文件
- 读取 XML 文件