package com.rekoe.module.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.web.ajax.Ajax;
import org.nutz.web.ajax.AjaxReturn;

import com.rekoe.common.Message;
import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Role;
import com.rekoe.domain.User;
import com.rekoe.service.RoleService;
import com.rekoe.service.UserService;

/**
 * @author 科技㊣²º¹³ 2014年2月3日 下午4:48:45 http://www.rekoe.com QQ:5382211
 */
@IocBean
@At("/admin")
@RequiresAuthentication
public class AdminUserAct {

	@Inject
	private UserService userService;
	@Inject
	private RoleService roleService;

	@At
	@Ok("fm:template.admin.common.main")
	public void main() {
	}

	@At
	@Ok("fm:template.admin.member.list")
	@RequiresPermissions({ "admin:admin" })
	public Pagination list(@Param("pageNumber") Integer pageNumber) {
		return userService.getUserListByPager(pageNumber, 20);
	}

	@At
	@Ok("fm:template.admin.member.add")
	public List<Role> add() {
		return roleService.list();
	}

	@At
	@Ok("json")
	@RequiresPermissions("admin:delete")
	public Message delete(@Param("ids") int[] uids, HttpServletRequest req) {
		for (int id : uids) {
			userService.delete(id);
		}
		return Message.success("admin.message.success", req);
	}

	@At
	@Ok(">>:${obj==true?'/admin/list.rk':'/admin/common/unauthorized.rk'}")
	public boolean save(HttpServletRequest req, @Param("username") String username, @Param("password") String password, @Param("isEnabled") boolean isEnabled, @Param("roleIds") int[] roleIds) {
		return userService.save(username, password, isEnabled, req.getRemoteAddr(), roleIds);
	}

	@At("/check_username")
	@Ok("raw")
	public boolean checkName(@Param("username") String username) {
		User user = userService.fetchByName(username);
		return Lang.isEmpty(user) ? true : false;
	}

	@At("/check_email")
	@Ok("raw")
	public boolean checkEmail(@Param("email") String email) {
		return StringUtils.isBlank(email) ? false : email.matches("\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)");
	}

	@At
	@Ok("fm:template.admin.member.edit")
	@Fail("json")
	public User edit(@Param("id") long id, HttpServletRequest req) {
		User user = userService.view(id);
		if (Lang.isEmpty(user)) {
			throw Lang.makeThrow(AccountException.class, "先解除帐号 %s 的锁定状态", user.getName());
		}
		List<Role> roleList = roleService.list();
		req.setAttribute("roleList", roleList);
		return user;
	}

	@At
	@Ok(">>:/admin/list.rk")
	public boolean update(@Param("id") long id, @Param("password") String password, @Param("isLocked") boolean isLocked, @Param("roleIds") Integer[] roleIds) {
		userService.update(id, password, isLocked, roleIds);
		return true;
	}

	/**
	 * 锁定用户
	 */
	@At
	@Ok("json")
	@RequiresPermissions("admin:lock")
	public AjaxReturn lock(@Param("id") long id) {
		return Ajax.ok();
	}

	@At("/profile/check_current_password")
	@Ok("raw")
	public boolean checkCurrentPassword(@Param("currentPassword") String password) {
		if (StringUtils.isBlank(password)) {
			return false;
		}
		Object principal = SecurityUtils.getSubject().getPrincipal();
		User user = (User) principal;
		if (StringUtils.equals(new Sha256Hash(password, user.getSalt(), 1024).toBase64(), user.getPassword())) {
			return true;
		}
		return false;
	}

	@At("/profile/edit")
	@Ok("fm:template.admin.profile.edit")
	public Subject profileEdit() {
		return SecurityUtils.getSubject();
	}

	@At("/profile/update")
	@Ok("fm:template.admin.profile.edit")
	public boolean profileUpdate(@Param("currentPassword") String currentPassword, @Param("password") String password) {
		if (checkCurrentPassword(currentPassword)) {
			Object principal = SecurityUtils.getSubject().getPrincipal();
			Object uid = Mirror.me(User.class).getValue(principal, "id");
			userService.updatePwd(uid, password);
			return true;
		}
		return false;
	}

	@At("/profile/re_update")
	@Ok(">>:${obj?'/admin/main':'/admin/common/unauthorized.rk'}")
	public boolean regUpate(@Param("username") String username, @Param("password") String password, @Attr("me") User suser) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return false;
		}
		suser.setSystem(false);
		suser.setName(username);
		String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
		suser.setSalt(salt);
		suser.setPassword(new Sha256Hash(password, salt, 1024).toBase64());
		userService.update(suser);
		return true;
	}
}
