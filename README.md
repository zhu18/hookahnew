## 大数据交易平台
### 项目介绍
  hookah - /ˈhʊkə/ 水烟袋 <br>
  山东青岛大数据交易平台，交易离线数据、API等。<br>
  2月27号开始，4月19日上线。<br>
### 项目技术栈
1.ibeetl 后端模板引擎
 http://www.ibeetl.com/ 
 <br>
2.Spring boot 后端MVC框架
 <br>
3.Motan 后端服务治理框架
  Motan:https://github.com/weibocom/motan
  <br>
  
4.前端框架
  AngularJS、JQuery等。
### 项目人员
  黄磊，邵建双，赵博，吴冰冰，王佳玲，吴冰冰，孔洋洋，路珊珊，王彩琴，邓许，陈涛平，曹荣山，赵帅，路通
### 代码说明
 * hookah-console 后台管理，也是服务发布端
 * hookah-website 前端网站，消费端，通过rpc调用console发布的服务
 * hookah-core 基础共享，包括实体类和一些工具类等。
 * hookah-rpc-api 接口，服务端实现此接口，消费端通过rpc调用。
 * doc 文档目录，原型太大，不上传了。
 
### 开发准备
 * 发现服务使用Consul，类似zookeeper，多了个图形界面方便查看用之。<br>
 网址：https://www.consul.io/<br>
 按照网站上下载安装说明简单明了.<br>
 启动命令:<br>
 ```consul agent -ui -data-dir '/Users/elvis/Data/consul' -dev -bind=127.0.0.1```
 替换上面命令中的/Users/elvis/Data/consul为你自己的本地目录<br>
 * 创建本地数据库:
  数据库使用Mysql5.7以上，数据库名：hookah<br>
 * 数据库初始化脚本：<br>
  未完<br>
 * 配置项目开发环境
   复制console与website项目中的application-dev-temp.yml改名为application-dev.yml
 * 配置nginx
  ！！Windows操作系统下，将自己的nginx目录的权限设置为 Users用户组完全控制
  把doc/nginx目录下的servers拷贝到自己的nginx目录中
  在nginx.conf文件http配置部分最后加入```include servers/*;```
  前台访问地址:www.qddata.com.cn <br>
  后台访问地址:console.qddata.com.cn/home  待改 <br>
  添加以下代码到本机的hosts文件中
  ```$xslt
  127.0.0.1 www.qddata.com.cn qddata.com.cn
  127.0.0.1 console.qddata.com.cn
  127.0.0.1 auth.qddata.com.cn
  127.0.0.1 admin.hokah.app
```

  * mongoDB，作为MySQL 数据库扩展，版本选用3.X，最新稳定版为3.4.2
    下载地址:https://www.mongodb.com/download-center<br>
    配置：（1）下载后解压，主要是bin目录和几个协议文件等。<br>
          （2）和bin同级添加data目录。<br>
           (3) 启动：命令行到 bin目录下，执行 mongod --dbpath <data目录><br>

  * RabbitMQ 作为消息中间件，主要应用于高并发下订单处理（如秒杀）
    需要 具备Erlang  OTP,按系统下载安装。<br>
    下载并安装 RabbitMQ,  http://www.rabbitmq.com/download.html,当前最新版本为 3.6.6<br>
    RabbitMQ会自动注册为服务且自动启动。<br>
    设置图形化管理界面。进入安装目录下sbin目录，使用命令："rabbitmq-plugins.bat" enable rabbitmq_management<br>
    重启服务后生效。net stop RabbitMQ && net start RabbitMQ<br>
    图形化访问地址为：http://localhost:15672/<br>
    默认用户名和密码都是guest。可以通过命令添加用户并设置权限。具体操作自行百度。<br>

### 时间线

#### 2017-3-1
 * 项目结构调整拆分为商品、订单、货架、其他4大部分
 * 后台管理界面单独拆分为hookah-console-angular
 * 配置nginx环境与开发说明

#### 2017-2-28
* 技术栈选型
* 项目结构搭建
* 基础模块搭建
* 服务治理基础框架搭建


### 项目打包发布
* 在hookah项目根目录下执行 ``` mvn clean ```
* hookah-console-angular
  * 模块根目录下执行 ```npm install```
  * 修改webpack.config.js文件81行
    * 替换 http://localhost:9500/ 为 http://admin.qddata.com.cn/
    * 注意尾部/符号
  * 模块根目录执行``` ./node_modules/webpack/bin/webpack.js ```
     !!window下执行:```.\node_modules\.bin\webpack.cmd
  * 模块根目录下会生成dist目录及打包好的程序
* hookah-oauth2server打包
  * 修改pom.xml文件，把下面所示的resource注释掉
    ```
    <!-- 打包时注释下面的resource -->
        <resource>
        <targetPath>${project.build.directory}/classes</targetPath>
        <directory>src/main/resources</directory>
        <filtering>false</filtering>
        </resource>
     ```
  * 在模块根目录执行 ```mvn clean package```
  * 会在target文件夹中生成打包好的hookah-oauth2server-0.0.1-package.tar.gz程序包
* hookah-console-server打包过程同上
* hookah-website打包过程同上
* 把打好的包上传到192.168.15.90机器上的/www目录中，此目录中有之前上传的软件包可删除
* hookah-console-angular/dist目录上传覆盖服务器上的/www/dist即可
* 在服务器上执行```ps -ef | grep java```查看
  ```aidl
  root       3399      1  0 11:57 ?        00:00:47 java -jar hookah-static-server-0.0.1.jar
  root      31514  31471  0 19:05 pts/0    00:00:00 grep --color=auto java
  root      44704      1  0 May04 ?        00:04:23 java -jar hookah-oauth2server-0.0.1.jar
  root      44798      1  0 May04 ?        00:15:07 java -jar hookah-console-server-0.0.1.jar
  root      45060      1  0 May04 ?        00:10:00 java -jar hookah-website-0.0.1.jar
  ```
  * kill -9 oauth2server console-server website 3个jar的进程，注意static-server的进程不要杀掉
* 依次解压 oauth2server console-server website 3个压缩包，例如下：
  ```aidl
    tar zxvf hookah-oauth2server-0.0.1.jar
  ```
  * 进入解压目录执行 ```nohup java -jar  *.jar &```

* 查看3个jar进程都存在及发布部署完毕
    