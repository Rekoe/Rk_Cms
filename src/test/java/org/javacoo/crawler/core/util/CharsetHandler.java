package org.javacoo.crawler.core.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.nutz.http.Http;

public class CharsetHandler implements ResponseHandler<String> {
	private String charset;

	public CharsetHandler(String charset) {
		this.charset = charset;
	}

	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			if (!StringUtils.isBlank(charset)) {
				return EntityUtils.toString(entity, charset);
			} else {
				return EntityUtils.toString(entity);
			}
		} else {
			return null;
		}
	}

	public String handleResponse(String url) {
		return Http.get(url).getContent(charset);
	}
}
