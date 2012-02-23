package com.rekoe.cms.action.front;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Profile;
import org.brickred.socialauth.SocialAuthConfig;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.util.SocialAuthUtil;
import org.nutz.dao.Cnd;
import org.nutz.ioc.annotation.InjectName;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.lang.Files;
import org.nutz.lang.stream.NullInputStream;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.view.ServerRedirectView;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.rekoe.cms.controller.BaseAction;
import com.rekoe.cms.model.CmsUser;

@IocBean(create="init")
@InjectName
@At("/user")
public class LoginAct extends BaseAction{

	//jsonp1329961249803
	//http://www.rekoe.com/user/test?callback=jsonp1329961249803
	/*提供社会化登录*/
	@At("/login/?")
	@Ok("void")
	public void login(String provider, HttpSession session, HttpServletRequest req) throws Exception {
		String returnTo = req.getRequestURL().toString() + "/callback";
		if (req.getParameterMap().size() > 0) {
			StringBuilder sb = new StringBuilder().append(returnTo).append("?");
			for (Object name : req.getParameterMap().keySet()) {
				sb.append(name).append('=').append(URLEncoder.encode(req.getParameter(name.toString()), Encoding.UTF8)).append("&");
			}
			returnTo = sb.toString();
		}
		SocialAuthManager manager = new SocialAuthManager(); //每次都要新建哦
		manager.setSocialAuthConfig(config);
		session.setAttribute("openid.manager", manager);
		String url = manager.getAuthenticationUrl(provider, returnTo);
		Mvcs.getResp().setHeader("Location", url);
		Mvcs.getResp().setStatus(302);
	}
	
	/*登出*/
	@At("/logout")
	@Ok("void")
	public void logout(HttpSession session) {
		session.invalidate(); //销毁会话,啥都米有了
	}
	
	/*无需做链接,这是OpenID的回调地址*/
	@At("/login/?/callback")
	public View returnPoint(String providerId, HttpServletRequest request, HttpSession session) throws Exception {
		SocialAuthManager manager = (SocialAuthManager) session.getAttribute("openid.manager");
		if (manager == null)
			throw new SocialAuthException("Not manager found!");
		session.removeAttribute("openid.manager"); //防止重复登录的可能性
		Map<String, String> paramsMap = SocialAuthUtil.getRequestParametersMap(request); 
		AuthProvider provider = manager.connect(paramsMap);
		Profile p = provider.getUserProfile();
		String providerid = p.getProviderId();
		CmsUser user = basicDao.findByCondition(CmsUser.class, Cnd.where("oAuthProvider", "=", providerid).and("openid", "=", p.getValidatedId()));
        if(user == null)
        {
        	final CmsUser cmsUser = new CmsUser();
        	cmsUser.setLastLoginDate(new Date());
        	cmsUser.setoAuthProvider(providerid);
        	cmsUser.setOpenid(p.getValidatedId());
        	cmsUser.setUsername(p.getDisplayName());
        	Trans.exec(new Atom() {
				@Override
				public void run() {
					basicDao.save(cmsUser);
				}
			});
        }else{
        	user.setLastLoginDate(new Date());
        	basicDao.update(user);
        }
        return new ServerRedirectView("/index.jsp");
	}
	

	private SocialAuthConfig config;
	
	public void init() throws Exception {
		SocialAuthConfig config = new SocialAuthConfig();
		File devConfig = Files.findFile("oauth_consumer.properties_dev"); //开发期所使用的配置文件
		if (devConfig == null)
			devConfig = Files.findFile("oauth_consumer.properties"); //真实环境所使用的配置文件
		if (devConfig == null)
			config.load(new NullInputStream());
		else
			config.load(new FileInputStream(devConfig));
		this.config = config;
	}
	@At("/test")
	@Ok("jsonp")
	public Object test(@Param("callback")String callback)
	{
		CmsUser cmsUser = new CmsUser();
		cmsUser.setId(1);
    	cmsUser.setLastLoginDate(new Date());
    	cmsUser.setoAuthProvider("qq");
    	cmsUser.setOpenid("1");
    	cmsUser.setUsername("张三");
		return cmsUser;
	}
}
