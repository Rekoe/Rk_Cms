var ioc = {
	fastHashMapCaptchaStore : {
		type : "com.octo.captcha.service.captchastore.FastHashMapCaptchaStore"
	},
	mailEngine : {
		type : "com.rekoe.mvc.view.GMailEngine",
		args : [ "com/rekoe/cms/captcha" ]
	},
	imageCaptchaService : {
		type : "com.octo.captcha.service.image.DefaultManageableImageCaptchaService",
		args : [ {
			refer : "fastHashMapCaptchaStore"
		}, {
			refer : "mailEngine"
		}, 180, 100000, 75000 ]
	}
};