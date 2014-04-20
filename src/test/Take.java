package com.rekoe.cms.authorize.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.Files;
import org.nutz.lang.util.Callback;

public class Take {

	public static void main(String[] args) {
		String path = Take.class.getResource("/").getPath();
		String proFile = path + "file.json";
		final List<String> urls = new ArrayList<String>();
		Files.readLine(Files.findFile(proFile), new Callback<String>() {
			@Override
			public void invoke(String str) {
				urls.add(str);
			}
		});
		for (String url : urls) {
			if (StringUtils.isBlank(url)) {
				continue;
			}
			Response req = Http.get(url);
			String savePath = path + "/temp/" + StringUtils.substringAfterLast(url, "/");
			savePath = StringUtils.substringBefore(savePath, "?");
			Files.write(savePath, req.getStream());
			System.out.println("save Path:" + savePath);
		}
	}
}
