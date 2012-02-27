package com.rk.cms.controller;

import org.nutz.dao.Cnd;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Param;

import com.rekoe.cms.model.Permission;
/**
 * 权限控制类
 * @author Administrator
 *
 */
@IocBean
@InjectName
public class PermissionAction extends BaseAction{

	@At("/admin/permission/set")
	@Fail("json")
	public String add(@Param("role") int role,@Param("resource") int resource,@Param("acl") int acl){
		
//		PermissionDao dao = new PermissionDao(ioc);
		
		Permission p = basicDao.findByCondition(Permission.class, Cnd.where("module", "=", resource).and("roleid", "=", role));
		
		if(p!=null){
			p.setAction(p.getAction()+acl);
			basicDao.update(p);
		}else{
			p = new Permission();
			p.setAction(acl);
			p.setModule(resource);
			p.setRoleid(role);
			basicDao.save(p);
		}
		
		return "{success:true}";
	}
}
