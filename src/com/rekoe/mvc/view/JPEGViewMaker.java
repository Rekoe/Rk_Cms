package com.rekoe.mvc.view;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class JPEGViewMaker implements ViewMaker {

	@Override
	public View make(Ioc ioc, String type, String value) {
		if ("captcha".equalsIgnoreCase(type)) {
			return ioc.get(JPEGView.class);
		}
		return null;
	}

}
