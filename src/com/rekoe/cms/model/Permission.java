package com.rekoe.cms.model;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

/**
 * 权限类
 * @author Administrator
 *
 */
@Table("t_Permission")
public class Permission implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3920523629540268618L;

	public static final int C  =  1;
	
	public static final int R  =  2;
	
	public static final int U  =  4;
	
	public static final int D  =  8;
	
	
	/*
	 * 数据库Id
	 */
	@Id
	private int id;
	/*
	 * 模块id
	 */
	@Column("module")
	private int module;
	/*
	 * 访问权限
	 */
	@Column("action")
	private int action;
	/*
	 * 权限对应角色
	 */
	@One(target=Role.class,field="roleid")
	private Role role;
	/*
	 * 角色id
	 */
	@Column
	private int roleid;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getModule() {
		return module;
	}

	public void setModule(int module) {
		this.module = module;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	
}
