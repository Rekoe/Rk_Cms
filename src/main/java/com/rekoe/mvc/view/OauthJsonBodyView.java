package com.rekoe.mvc.view;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Encoding;
import org.nutz.lang.Streams;
import org.nutz.mvc.View;

@IocBean
public class OauthJsonBodyView implements View {

	@Override
	public void render(HttpServletRequest arg0, HttpServletResponse resp, Object obj) throws Throwable {
		resp.setContentType("application/json");
		byte[] data = String.valueOf(obj).getBytes(Encoding.UTF8);
		resp.setHeader("Content-Length", "" + data.length);
		OutputStream out = resp.getOutputStream();
		Streams.writeAndClose(out, data);
	}
}
