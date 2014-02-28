RK_Cms<br>
======<br>
version - nutz.b.50<br>
一个权限管理系统 采用apache shiro<br>
1) create database platform <br>
2)mvn clean<br>
3)mvn eclipse:eclipse<br>
mvn dependency:tree 可以查看依赖树<br>
关于部署webapps路径问题 修改文件 .settings/.jsdtscope<br>
修改< classpathentry kind="src" path="WebRoot"/ > 为:< classpathentry kind="src" path="src/main/webapp"/ ><br>
或者执行命令 mvn eclipse:eclipse -Dwtpversion=1.0