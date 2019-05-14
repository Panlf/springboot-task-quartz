## springboot-task-quartz

### 本项目简介

本系统是SpringBoot2.1.4版本集成Quartz，实现如下功能：

- 新增定时任务
- 修改定时任务
- 删除某个任务
- 恢复某个任务
- 恢复所有任务
- 暂停某个任务
- 查询任务列表

本系统只具有后端代码，并无页面显示，可以启动本项目访问`http://ip:port:/swagger-ui.html` 查看所有功能并测试。

### 项目使用

#### 1、配置文件
下载代码查看`resources/application.properties`其中是SpringBoot的配置，可以根据需求修改，其中`quartz_example.properties`是关于Quartz的注释说明，具体在`quartz.properties`，也可以根据需求修改。

#### 2、数据库
数据表由Quartz提供，只需要新建数据库然后将Quartz提供的SQL文件跑一次即可。具体SQL文件在`quartz-2.3.1.jar/org.quartz.impl.jdbcjobstore`下面，择一即可。
