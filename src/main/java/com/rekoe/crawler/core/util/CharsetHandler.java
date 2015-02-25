package com.rekoe.crawler.core.util;

import org.nutz.http.Http;

public class CharsetHandler {
	private String charset;

	public CharsetHandler(String charset) {
		this.charset = charset;
	}

	public String handleResponse(String url) {
		return Http.get(url).getContent(charset);
	}
}
