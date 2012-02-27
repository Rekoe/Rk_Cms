package com.rekoe.cms.action.user.center;

import java.util.Date;

import org.nutz.dao.Cnd;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.rekoe.cms.controller.BaseAction;
import com.rekoe.cms.model.CmsUser;

@IocBean
@InjectName
@At("/users")
///users/loginService
public class UserAct extends BaseAction{

	@At("/loginService")
	@Ok(">>:${obj == null ? '/users/register' : '/users/index'}")
	public Object login(@Param("::user.") CmsUser user) throws Exception {
		user = basicDao.findByCondition(CmsUser.class, Cnd.where("username", "=", user.getUsername()));
		return user;
	}
	@At("/registerService")
	public void registerSubmit(@Param("::user.") CmsUser user)
	{
		CmsUser $tempUser = basicDao.findByCondition(CmsUser.class, Cnd.where("username", "=", user.getUsername()));
		if($tempUser == null)
		{
			final CmsUser cmsUser = new CmsUser();
			cmsUser.setoAuthProvider("rk");
			cmsUser.setUsername(user.getUsername());
			cmsUser.setLastLoginDate(new Date());
			Trans.exec(new Atom() {
				@Override
				public void run() {
					basicDao.save(cmsUser);
				}
			});
		}
	}
	@At("/register")
	@Ok("fm:usercenter.register")
	public void register()
	{
		
	}
}
