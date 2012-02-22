package com.rekoe.cms.web;

import java.util.List;

import com.rekoe.cms.model.Permission;
import com.rekoe.cms.model.Role;

public class UserSession {

	private List<Role> roles;
	public UserSession(List<Role> roles)
	{
		this.roles = roles;
	}

	public void setRole(List<Role> roles) {
		this.roles = roles;
	}
	/*
	 * 检测用户执行权限
	 * @param roles
	 * @return
	 */
	public boolean check(int module,int action) {
		boolean flag = false;
		int rule = -1;
		if (roles != null && roles.size() > 0) {
			for (Role r : roles) {
				if (r.getRule() > rule) {
					List<Permission> permissions = r.getPermission();
					for (Permission p : permissions) {
						if (p.getModule() == module) {
							// 判断权限
							if ((p.getAction() & action) >0) {
								// 判断角色优先级
								flag = true;
								return flag;
							}else{
								flag = false;
							}
							rule = r.getRule();
						}
					}
				}
			}
		}
		return flag;
	}
}