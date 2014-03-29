package com.rekoe.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;

@Table("facebook_account${id}")
@TableIndexes({ @Index(name = "facebookID_index", fields = { "passportid" }, unique = true) })
public class Account {

	@Name
	@Comment("ID主键")
	@Prev(els = { @EL("uuid()") })
	private String id;
	@Column
	@Comment("facebookID")
	private String passportid;
	@Column
	@ColDefine(type=ColType.TIMESTAMP)
	@Comment("创建时间")
	private Date createTime;
	@Column("modify_time")
	@ColDefine(type=ColType.TIMESTAMP,update=true)
	@Comment("最后登录时间")
	private Date modifyTime;
	@Column("zone_log")
	@ColDefine(type=ColType.TEXT)
	@Comment("最近登录的服务器列表")
	private String zoneLog;
	@Column("last_zoneid")
	@Comment("最后登录的服务器")
	private String lastZoneid;
	@Column("is_lock")
	@ColDefine(type=ColType.BOOLEAN)
	@Comment("是否锁定账号")
	private boolean lock;
	@Column("register_ip")
	@Comment("最后登录IP")
	private String registerIp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassportid() {
		return passportid;
	}
	public void setPassportid(String passportid) {
		this.passportid = passportid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getZoneLog() {
		return zoneLog;
	}
	public void setZoneLog(String zoneLog) {
		this.zoneLog = zoneLog;
	}
	public String getLastZoneid() {
		return lastZoneid;
	}
	public void setLastZoneid(String lastZoneid) {
		this.lastZoneid = lastZoneid;
	}
	public boolean isLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
}
