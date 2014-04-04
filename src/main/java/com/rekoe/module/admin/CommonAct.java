package com.rekoe.module.admin;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

import com.rekoe.common.Message;

@IocBean
@At("/admin/common")
@RequiresAuthentication
public class CommonAct {

	protected static final String ERROR = "/admin/common/error";
	protected static final Message MESSAGE_ERROR = Message.error("admin.message.error",Mvcs.getReq());
	protected static final Message MESSAGE_ERROR_FORBIT = Message.error("admin.error.message.forbit",Mvcs.getReq());
	protected static final Message MESSAGE_SUCCESS = Message.success("admin.message.success",Mvcs.getReq());

	// admin/common/unauthorized.jhtml

	@At
	@Ok("fm:template.admin.common.error")
	public Message unauthorized() {
		return MESSAGE_ERROR;
	}
	
	@At
	@Ok("fm:template.admin.common.error")
	public Message forbit() {
		return MESSAGE_ERROR_FORBIT;
	}
	@At
	@Ok("fm:template.admin.common.error")
	public Message success() {
		return MESSAGE_SUCCESS;
	}

	@At
	@Ok("fm:template.admin.common.index")
	public void index() {

	}
	@At
	@Ok("fm:template.admin.common.maintain")
	@Filters
	public void maintain() {

	}
}
