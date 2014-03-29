package com.rekoe.domain;

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

@Table("facebook_zone")
@TableIndexes({ @Index(name = "z_sid", fields = { "serverid" }, unique = true) })
public class Zone {

	@Name
	@Comment("ID主键")
	@Prev(els = { @EL("uuid()") })
	private String id;
	@Column
	@Comment("服务器ID")
	private int serverid;
	@Column
	@Comment("服务器状态")
	private int status;// 繁忙 拥挤 畅通 维护
	@Column
	@ColDefine(type = ColType.BOOLEAN)
	@Comment("是否开启")
	private boolean open;
	@Column
	@Comment("服务器名字")
	private String name;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 1024)
	@Comment("url")
	private String url;
	@Column
	@ColDefine(type = ColType.BOOLEAN)
	@Comment("是否推荐")
	private boolean recommend;
	@Column
	@ColDefine(type = ColType.BOOLEAN)
	@Comment("是否新服")
	private boolean fresh;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getServerid() {
		return serverid;
	}

	public void setServerid(int serverid) {
		this.serverid = serverid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public boolean isFresh() {
		return fresh;
	}

	public void setFresh(boolean fresh) {
		this.fresh = fresh;
	}
}
