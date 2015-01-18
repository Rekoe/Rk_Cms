package com.rekoe.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * @author 科技㊣²º¹³
 * 2014年2月6日 下午8:58:00
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
public class CaptchaModule {

	@Inject
	private ImageCaptchaService imageCaptchaService;

	@At
	@Ok("raw")
	@Filters
	public Object captcha(HttpServletRequest request) {
		String captchaId = request.getSession(true).getId();
		return imageCaptchaService.getChallengeForID(captchaId, request.getLocale());
	}
}
