package com.rekoe.cms.authorize.test;

import org.json.JSONObject;
import org.nutz.lang.Files;

/**
 * @author 科技㊣²º¹³
 * 2014年4月20日 下午3:53:35
 * http://www.rekoe.com
 * QQ:5382211
 */
public class JsonTest {

	public static void main(String[] args) {
		String json = Files.read("d:/json.json");
		JSONObject resp = new JSONObject(json);
		System.out.println(resp.get("id"));
	}

}
