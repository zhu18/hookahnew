## 大数据交易平台
### 项目介绍
  hookah - /ˈhʊkə/ 水烟袋 <br>
  山东青岛大数据交易平台，交易离线数据、API等。<br>
  2月27号开始，3月底上线。<br>
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
  邵建双、赵博
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
  把doc/nginx目录下的servers拷贝到自己的nginx目录中
  在nginx.conf文件http配置部分最后加入```include servers/*;```
  前台访问地址:www.hookah.app <br>
  后台访问地址:console.hookah.app/home  待改 <br>
  添加以下代码到本机的hosts文件中
  ```$xslt
  127.0.0.1 www.hookah.app hookah.app
  127.0.0.1 console.hookah.app
```
### 时间线

#### 2017-3-1
 * 项目结构调整拆分为商品、订单、货架、其他4大部分
 * 后台管理界面单独拆分为hookah-console-angular
 * 配置nginx环境与开发说明
 *
#### 2017-2-28
* 技术栈选型
* 项目结构搭建
* 基础模块搭建
* 服务治理基础框架搭建

  