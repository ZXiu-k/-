### 考勤管理系统

------

基于 Vue 和 SpringBoot 开发的管理系统

#### 页面展示

##### 登录页面

![1](C:\Users\ZXiu\Pictures\考勤管理系统\1.png)

##### 员工页面

个人信息

![2](C:\Users\ZXiu\Pictures\考勤管理系统\2.png)

签到页面

![3](C:\Users\ZXiu\Pictures\考勤管理系统\3.png)

请假页面

![4](C:\Users\ZXiu\Pictures\考勤管理系统\4.png)

##### 管理员页面

员工管理

![5](C:\Users\ZXiu\Pictures\考勤管理系统\5.png)

考勤记录查看

![6](C:\Users\ZXiu\Pictures\考勤管理系统\6.png)

请假审批

![7](C:\Users\ZXiu\Pictures\考勤管理系统\7.png)

每月考勤总结

![8](C:\Users\ZXiu\Pictures\考勤管理系统\8.png)

### 使用方法

------

数据库建表语句以及测试数据在 `attendance_management_system.sql` 中

前端为 `attendance_management` 文件夹

运行 `npm run serve` 前先运行  `npm install` 安装模块

后端为 `attendance_management_system` 文件夹

运行前要在 `application.yml` 中更改数据库的用户名和密码



##### 跳过运行直接部署打包好的文件：

参考视频：[springboot和vue项目打包部署教程](https://www.bilibili.com/video/BV1gk4y1U7BK)

后端运行示例：

```bash
java -jar .\attendance-0.0.1-SNAPSHOT.jar --spring.datasource.username=填你的用户名 --spring.datasource.passeword=填你的密码
```

`disk` 和 `attendance-0.0.1-SNAPSHOT.jar` 可在 release 中下载