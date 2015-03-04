package com.rekoe.mvc;

import javax.servlet.http.HttpServletRequest;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

/**
 * 执行时间过滤器
 */
public class ProcessTimeProcessor extends AbstractProcessor {
	private final static Log log = Logs.get() ;
	/**
	 * 请求执行开始时间
	 */
	public static final String START_TIME = "_start_time";

	@Override
	public void process(ActionContext ac) throws Throwable {
		long time = System.currentTimeMillis();
		HttpServletRequest request = ac.getRequest();
		request.setAttribute(START_TIME, time);
		doNext(ac);
		time = System.currentTimeMillis() - time;
		log.debugf("process in {%s} ms: {%s}", time, request.getRequestURI());
	}
}
