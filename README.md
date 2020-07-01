# 接口自动化
## 为什么要做接口测试
- 更容易实现持续集成
- 自动化测试落地性价比更高，比UI更稳定
- 大型系统更多更复杂，系统间模块越来越多
- BUG更容易定位（分模块进行测试）
- 降低研发成本，提高效率
- 接口协议相关基础知识（HTTP协议）

## 接口自动化测试开发技能树
- 开发语言（Java、Python、JavaScript...）
- 测试框架（TestNG、HttpClient、Rest_Assured...）
- Mock技术（WireMock、Moco...）
- 数据持久层框架（MyBatis...）
- 持续集成工具（Jenkins...）

## 接口自动化测试落地过程
**需求阶段**：    项目立项    产品设计    需求文档

**研发阶段**：    UI设计    前端开发    后端开发    测试设计    测试开发

**测试阶段**：    环境搭建    多项测试执行  BUG修复   测试报告

**项目上线**：    线上回归测试   上线报告    添加监控

# 接口自动化的用例设计
## 接口测试的范围

- 功能测试
  - 等价类划分法
  - 边界值分析法
  - 错误推断法
  - 因果图法
  - 判定表驱动法
  - 正价试验法
  - 功能图法
  - 场景法
- 异常测试
  - 数据异常
    - null
    - ""
    - 数据类型
  - 环境异常
    - 负载均衡架构
    - 冷热备份
- 性能测试(狭义)
  - 负载测试
  - 压力测试或强度测试
  - 并发测试
  - 稳定性测试或可靠性测试

## 自动化接口测试的范围

- 功能测试
- 数据异常测试

## 接口测试用例设计原则

- 初始化前值条件（Setup）: 创建对象、启动前置服务、初始化数据等。
- 测试步骤（Execution）：API 测试步骤调用或者 API 场景调用，记录测试日志等。
- 验证（Verification）: 把执行结果和预期结果进行验证。
- 报告结果（Reporting）: 报告测试结果是通过（Pass）、失败（Failed）, 甚至或者测试异常受阻。
- 清理（Clean up）: 清理初始化时和测试执行中产生的脏数据，保持测试环境数据的有效性。

## 环境异常改怎么测试

web应用

- 前端发送数据 —> Nginx转发请求 —> 多个tomcat
  - 例如：
    - 原则：根据userid来进行区分(取模)
      - 当余数为0时，分发到tomcat1
      - 当余数为1时，分发到tomcat2
      - 当余数为2时，分发到tomcat3
      - ……
      - 如果tomcat1挂掉，对应访问该服务器的用户，会转到tomcat2
    - 场景：用户在请求时，突然有一台tomcat挂了
      - 正确的结果是：用户可正常访问下一个tomcat
      - 挂掉tomcat又正常启动了，用户又可以正常的tomcat
    - 如何查看访问的服务器：通过日志来进行确认

## 自动化框架的设计

- 设计分层
  - 显示层
    - 测试报告
  - 控制层
    - 逻辑验证
  - 持久层
    - 测试用例存储(数据驱动)

# 代码管理工具git的应用
## GitHub的特点
- 开源的分布式版本管理系统
- 开源项目集中的代码库
- 所有略有规模的公司都在使用

## GitHub的功能介绍
- 记录多个版本
- 查看历史操作，可以进行版本回退和前进的控制
- 多端共享代码，自动合并

## Github与SVN
- SVN版本集中管理，所有的代码都在中央服务器上
- Git去中心化，每个服务器上都有一个完整的代码库

## Github的使用
### 安装Git客户端
- Linux: yum install -y git
- Windows: [Git客户端安装](https://git-scm.com/downloads)
- Mac: brew install git

### 检查安装是否成功
- git --version

### 注册Git账号
- 主页：[Git主页](https://github.com)
- 推荐是用命令行进行操作

### 配置sshkey
- 生成key：ssh-keygen -t rsa -C "邮箱地址"
- cd ~/.ssh(用户目录下的.ssh文件夹)
- 复制id_rsa.pub的公钥内容到github网站中

### 配置多个sshkey
- cd ~/.ssh(用户目录下的.ssh文件夹)
- vim config
- 添加四项
    - Host github.com
    - HostName github.com
    - User
    - IdentityFile
    
## Git命令的使用
- 在Github中创建项目

### 将本地工程推送远程端 
- git init
- git add *
- git commit -m "first commit"
- git remote add origin https://github.com/jinglv/ApiAutoForJava.git
- git push -u origin master

### 分支操作
- git branch — 查看本地分支
- git branch -a — 查看所有远端分支
- git checkout -b 分支名 — 创建本地分支
- git checkout  分支名 — 切换分支
- git push —set-upstream origin 分支名 — 提交分支到远端
- git branch -d 分支名 — 删除本地分支，首先要切换到其他分支
- git branch -r -d origin/分支名 — 删除远端分支
- git push origin :分支名 — 推送已删除的分支到远端(注意：空格)
- git merge 分支名 — 将某个分支合并到当前分支上


# http协议

查看http协议信息头：浏览器开发小工具、Fiddler

## 常用请求头

- Accept：浏览器告诉服务器它所支持的数据类型
- Accept-Charset：浏览器告诉服务器它采用的字符集
- Accept-Encoding：浏览器告诉服务器它所支持的压缩格式
- Accept-Language：浏览器告诉服务器它所采用的语言
- Connection：浏览器的连接方式
- Host：浏览器告诉服务器我想访问服务器哪台主机
- If-Modified-Since：浏览器告诉服务器它缓存数据时间是多少
- Referer：浏览器告诉服务器我是从哪个网页点过来的（防盗链）
- userEntity-Agent： 浏览器告诉服务器我所使用的浏览器类型、版本等信息
- Date：浏览器告诉服务器我什么时间访问的

## 常用响应头

- Location: 这个头告诉浏览器你去找谁，配合302状态码使用
- Server: 告诉浏览器服务器的类型
- Content-Encoding：告诉浏览器回送的数据采用的压缩格式
- Content-Type：告诉浏览器回送的数据类型
- Last-Modified：告诉浏览器数据的最后修改时间
- Refresh： 用于控制浏览器定时刷新
- Content-Disposition: 告诉浏览器需要以下载方式打开回送的数据
- Transfer-Encoding：告诉浏览器数据是以分块形式回送的
- cookie与session的区别特点

## 区别

- cookie在客户端的头信息中
- session在服务端存储， 文件、数据库等都可以
  - 一般来说session的验证需要cookie带一个字段来表示这个用户是哪一个session
  - 一般来书session的验证需要cookie带一个字段来表示这个用户哪一个session，所以当客户端禁用cookie时，session将失效

- Cookie的总结

  - cookie就是小一段文本信息
  - cookie的格式为 key:value;key:value...
  - cookie的值由服务端生成，客户端保存