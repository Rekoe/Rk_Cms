RK_Cms
======================================

nutz version - nutz.b.51

权限管理系统 采用apache shiro

数据库mysql

默认账号密码: admin 123


编译说明
=================================================

基于maven

```
maven clean package war:war
```

运行说明
================================================

初始化数据库, root密码设置为root.
```
create database platform default character set uft8;
```

将war放入tomcat的webapps目录,启动tomcat即可

生成eclipse项目文件
================================================

```
mvn eclipse:eclipse -Dwtpversion=1.0
```

查看依赖树
================================================

```
mvn dependency:tree
```

在docker容器中运行
===============================================

先启动一个mysql容器

```
docker run --name rk_cms_db -d -e MYSQL_ROOT_PASSWORD="root" mairadb
```

然后启动rk_cms

```
docker run --name my_rk_cms -it --link rk_cms_db:tomysql wendal/rk_cms
```

协议 LICENSE
========================================================

Apache License
