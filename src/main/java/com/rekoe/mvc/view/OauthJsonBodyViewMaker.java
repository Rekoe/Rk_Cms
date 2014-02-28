package com.rekoe.mvc.view;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class OauthJsonBodyViewMaker implements ViewMaker {

	public View make(Ioc ioc, String type, String value) {
		if ("oauth".equalsIgnoreCase(type)) {
			return ioc.get(OauthJsonBodyView.class);
		}
		return null;
	}

}
