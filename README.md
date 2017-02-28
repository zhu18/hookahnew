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
  hookan-console 后台管理项目目录
  hookan-website 前端网站项目目录
  doc 文档目录
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
### 时间线
#### 2017-2-27
* 技术栈选型
* 项目结构搭建
* 基础模块搭建

  