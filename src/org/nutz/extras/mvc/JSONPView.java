package org.nutz.extras.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

public class JSONPView implements View{
	private static final Log log = Logs.getLog(JSONPView.class);
	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, Object obj) throws Throwable {
		StringBuilder sb = new StringBuilder();
		sb.append(Mvcs.getReq().getParameter("callback"));
		sb.append("(");
		if(obj instanceof String)
		{
			sb.append(obj.toString());
		}else
		{
			sb.append(Json.toJson(obj));
		}
		sb.append(")");
		renderJson(response, sb.toString());
	}
	
	private static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}
	private static void render(HttpServletResponse response, String contentType,
			String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
