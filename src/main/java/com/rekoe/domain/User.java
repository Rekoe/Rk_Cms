package com.rekoe.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;

/**
 * @author 科技㊣²º¹³ 2014年2月3日 下午4:48:45 http://www.rekoe.com QQ:5382211
 */
@Table("system_user")
@TableIndexes({ @Index(name = "user_name", fields = { "name" }, unique = true), @Index(name = "user_openid", fields = { "openid" }, unique = true) })
public class User implements Serializable {

	private static final long serialVersionUID = -965829144356813385L;

	@Id
	private Long id;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 20)
	private String name;
	@Column
	@ColDefine(type = ColType.CHAR, width = 44)
	private String password;
	@Column
	@ColDefine(type = ColType.CHAR, width = 24)
	private String salt;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 10)
	private String sex;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 64)
	private String openid;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 10)
	private String providerid;
	@Column("is_locked")
	@ColDefine(type = ColType.BOOLEAN)
	private boolean locked;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 500)
	private String description;
	@Column(hump = true)
	@ColDefine(type = ColType.TIMESTAMP)
	private Date createDate;
	@Column(hump = true)
	@ColDefine(type = ColType.VARCHAR, width = 15)
	private String registerIp;
	@ManyMany(target = Role.class, relation = "system_user_role", from = "userid", to = "roleid")
	private List<Role> roles;

	@Column("is_system")
	@ColDefine(type = ColType.BOOLEAN)
	private boolean system;

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}

}
