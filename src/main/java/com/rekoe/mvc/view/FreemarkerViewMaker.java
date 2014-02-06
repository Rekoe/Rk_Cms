package com.rekoe.mvc.view;

import org.nutz.ioc.Ioc;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

import com.rekoe.web.freemarker.FreeMarkerConfigurer;

public class FreemarkerViewMaker implements ViewMaker {

	public View make(Ioc ioc, String type, String value) {
		if ("fm".equalsIgnoreCase(type)) {
			return new FreemarkerView(ioc.get(FreeMarkerConfigurer.class), value);
		}
		return null;
	}

}
