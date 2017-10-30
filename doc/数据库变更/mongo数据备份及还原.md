# mongo数据还原/备份
## 整库
### 导出
```mongodump -h IP --port 端口 -u 用户名 -p 密码 -d 数据库 -o 文件存在路径 
如果没有用户谁，可以去掉-u和-p。
如果导出本机的数据库，可以去掉-h。
如果是默认端口，可以去掉--port。
如果想导出所有数据库，可以去掉-d。
eg: ./mongodump -d hookah -o /server/mongodb/bak/bdg1023dump```

### 导入
```mongorestore -h IP --port 端口 -u 用户名 -p 密码 -d 数据库 --drop 文件存在路径
恢复所有数据库到mongodb中:./mongorestore /server/mongodb
还原指定数据库：./mongorestore -d hookah /server/mongodb/bak/bdg1023dump/hookah
将tank还原到tank_new数据库中:./mongorestore -d wjl_test /server/mongodb/bak/bdg1023dump/hookah
```

## 整表
### 导出
```mongoexport -d tank -c users -o /home/zhangy/mongodb/tank/users.dat```

### 导入
```mongoimport -d tank -c users --upsert tank/users.dat```