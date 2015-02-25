/* system_user */
INSERT INTO system_user(ID,NAME,PASSWORD,SALT,SEX,DESCRIPTION,IS_LOCKED,CREATE_DATE,REGISTER_IP,OPENID,PROVIDERID) VALUES 
(1,'admin',NULL,NULL,0,"超级管理员",0,'2014-02-01 00:12:40','127.0.0.1','admin','local');
/*  .system_role   		*/
INSERT INTO system_role(ID,NAME,DESCRIPTION) VALUES
(1,'admin','超级管理员：拥有全部权限的角色'),
(2,'viewer','审阅者：拥有任何对象的浏览权限的角色'),
(3,'user-superadmin','用户超级管理员：拥有对用户的任意操作权限的角色'),
(4,'user-admin','用户管理员：拥有对用户的浏览、增加和编辑(不包括删除)权限的角色'),
(5,'security-admin','安全管理员：拥有对角色和权限的任意操作，对用户分配角色及对角色分配权限的权限');
/*  .system_permission   		*/
INSERT INTO `system_permission` VALUES (1,'*:*:*','全部权限','1',1),(2,'admin:role','对用户分配角色 ','721fbbca1ae44716918fdb3921deacb1',0),(3,'admin:setting','系统设置 ','800a35a4c142413c9d902f4d40245ec3',0),(4,'admin:article','文章管理 ','0b24b967550543469941c7b29e27f277',0),(5,'admin:articleCategory','文章分类 ','0b24b967550543469941c7b29e27f277',0),(6,'admin:admin','添加管理员','721fbbca1ae44716918fdb3921deacb1',0),(7,'admin:permission','权限管理','721fbbca1ae44716918fdb3921deacb1',0),(8,'admin:permissionCategory','权限分类','721fbbca1ae44716918fdb3921deacb1',0);
/*  .permission_category   		*/
INSERT INTO `permission_category` VALUES ('0b24b967550543469941c7b29e27f277','文章管理',1),('1','超级权限',1),('721fbbca1ae44716918fdb3921deacb1','用户管理',1),('800a35a4c142413c9d902f4d40245ec3','系统设置',1);
/*  .system_user_role   		*/
INSERT INTO system_user_role(USERID,ROLEID) VALUES 
(1,1);
/*  .system_role_permission  		*/
INSERT INTO system_role_permission(ROLEID,PERMISSIONID) VALUES 
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6);
/*  .system_setting  		*/
INSERT INTO system_setting(id,site_name,certtext,site_enabled,site_close_message) VALUES (1,'瑞克科技','Cms Game',1,'测试阶段<a herf="#">查看更多.....</a>');
/* .acquisition*/
INSERT INTO `acquisition` VALUES (1,'Sina','GBK','http://roll.news.sina.com.cn/news/gnxw/gdxw1/index_1.shtml','class=list_009','','false','true','id=artibody','','f910a34c9cbd4ece98363ca8398d995b',NULL),(2,'QQ','GBK','http://finance.qq.com/money/insurance/','id=newsList','','false','true','id=Cnt-Main-Article-QQ','','6d38b6b1afb4458fb6a2eb5a5ff9641e','http://finance.qq.com');
/* .crawler_rule */
INSERT INTO `crawler_rule` VALUES (1,'39健康-老人健康','2012-05-02 09:54:45','2012-05-02 09:58:41',0,0,0,0,200,'gb2312','http://oldman.39.net/nutrition/','',2,10,'class=listbox','','','','false','true','[{\"fields\":\"\",\"filterStart\":\"\",\"filterEnd\":\"\"}]','','','','id=contentText','IFRAME,align=right','','','',0,0,0,'',80,'39健康=美食汇,老人',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'a51b07543d8f42b68d6b85864b277b47'),(2,'本地采集','2012-04-16 07:51:23','2012-04-16 08:11:07',0,0,0,0,500,'gb2312','http://localhost/v7/list.php?fid=33','',2,10,'style=margin-left:5px;margin-top:10px;','','http://localhost/v7/','','false','false','[{\"fields\":\"\",\"filterStart\":\"\",\"filterEnd\":\"\"}]',NULL,'','','id=post1','','','','',0,0,0,'',80,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'a51b07543d8f42b68d6b85864b277b47'),(3,'新浪新闻','2012-05-10 07:20:36','2012-05-10 07:25:11',0,0,0,0,100,'gb2312','','http://roll.news.sina.com.cn/news/gnxw/gdxw1/index_[page].shtml',2,2,'class=list_009','','','','false','true','[{\"fields\":\"\",\"filterStart\":\"\",\"filterEnd\":\"\"}]','','','','id=artibody','','','','',0,0,0,'128.160.97.10',808,'','',NULL,NULL,NULL,NULL,'','','','a51b07543d8f42b68d6b85864b277b47'),(4,'本地采集-带评论','2012-05-12 10:54:52','2012-05-12 10:55:37',0,0,0,0,100,'utf-8','http://localhost:8080/shehui/index.jhtml','',2,10,'class=f-left','class=red,img','http://localhost:8080/','http://localhost:8080/','false','true','[{\"fields\":\"\",\"filterStart\":\"\",\"filterEnd\":\"\"}]','','','','class=content','','','','',0,0,0,'',80,'','dd','class=comment','class=w98','class=rmpl','dt,class=line','','class=pagebar','select','a51b07543d8f42b68d6b85864b277b47'),(5,'QQ美食','2012-05-13 04:50:29','2012-05-13 04:53:29',0,0,0,0,200,'utf-8','','http://meishi.qq.com/chengdu/s/p[page]',4,4,'class=list box','p','http://meishi.qq.com','http://meishi.qq.com','false','true','[{\"fields\":\"javacoo\",\"filterStart\":\"class=user_avg\",\"filterEnd\":\"a\"}]','','','','class=basic','','','','',0,0,0,'128.160.97.10',808,'','class=intro_comment box','','','class=list','','','class=mod_pagenav_main','','a51b07543d8f42b68d6b85864b277b47'),(6,'爱美网','2012-05-12 13:10:57','2012-05-12 13:20:10',0,0,0,0,100,'utf-8','http://health.lady8844.com/330834/','',2,10,'class=blist','','','','true','true','[{\"fields\":\"\",\"filterStart\":\"\",\"filterEnd\":\"\"}]','1','','','id=TEXT_CONTENT','','id=content_pagelist','','',0,0,0,'',80,'','','','','','','','','','a51b07543d8f42b68d6b85864b277b47');
