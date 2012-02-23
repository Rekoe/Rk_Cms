package org.nutz.extras.mvc;

import org.nutz.ioc.Ioc;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.View;
import org.nutz.mvc.ViewMaker;

public class JSONPViewMaker implements ViewMaker{
	private static final Log log = Logs.getLog(JSONPViewMaker.class);
	@Override
	public View make(Ioc ioc, String type, String value) {
		log.debugf("Type:%s,Value:%s", type, value);
		if ("jsonp".equalsIgnoreCase(type)) {
			return new JSONPView();
		}
		return null;
	}

}
