package com.rekoe.utils;

import java.io.UnsupportedEncodingException;

public class SystemContext {

	public static final int PAGE_SIZE = 20;

	public static void main(String[] args) throws UnsupportedEncodingException {
		/*String ss = "用户名不能为空";
		byte[] uncode = ss.getBytes("Unicode");
		int x = 0xff;
		String result = "";
		for (int i = 2; i < uncode.length; i++) {
			if (i % 2 == 0)
				result += "\\u";
			String abc = Integer.toHexString(x & uncode[i]);
			result += abc.format("%2s", abc).replaceAll(" ", "0");
		}
		System.out.println(result);
		File file = Files.findFile("msg/zh_CN/message_zh_CN.properties");
		Files.readLine(file, new Callback<String>() {
			@Override
			public void invoke(String str) {
				String code = decodeUnicode(str);
				
				sb.append(code);
				sb.append("\n");
			}
		});
		//Files.write("e:/abc", sb);*/
		//System.out.println(decodeUnicode("admin.main.statistics=\u8bbf\u95ee\u7edf\u8ba1"));
		//System.out.println(decodeUnicode("admin.main.statisticsSetting=\u7edf\u8ba1\u8bbe\u7f6e"));
		//System.out.println(decodeUnicode("admin.main.sales=\u9500\u552e\u7edf\u8ba1"));
		System.out.println(decodeUnicode("shop.member.index.orderHasExpired=\u5df2\u8fc7\u671f"));
		System.out.println(decodeUnicode("shop.member.submit=\u63d0&nbsp;&nbsp;\u4ea4"));
		System.out.println(decodeUnicode("shop.member.back=\u8fd4&nbsp;&nbsp;\u56de"));
		/*String code = decodeUnicode("\u9605\u8BFB\u5168\u6587");
		/*String code = decodeUnicode("\u9605\u8BFB\u5168\u6587");
		/*String code = decodeUnicode("\u9605\u8BFB\u5168\u6587");
		/*String code = decodeUnicode("\u9605\u8BFB\u5168\u6587");
		/*String code = decodeUnicode("\u9605\u8BFB\u5168\u6587");
		/*String code = decodeUnicode("\u9605\u8BFB\u5168\u6587");
		System.out.println(code);
		Response req = Http.get("http://www.jtbc.cn/common/images/icon.2.jpg");
		Files.write("e:/common/images/icon.2.jpg", req.getReader());*/
		/*final Map<String,String> imgMap = new HashMap<String,String>();
		File file = Files.findFile("template/jtbc.ftl");
		Files.readLine(file, new Callback<String>() {
			@Override
			public void invoke(String str) {
				String img = StringUtils.substringBetween(str, "<img src=\"", "\"");
				String name = StringUtils.substringAfter(img, "common/images/");
				if(StringUtils.isNotBlank(img))
				{
					imgMap.put(img, name);
				}
			}
		});
		for(Entry<String, String> entry:imgMap.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			if(StringUtils.isNotBlank(key))
			{
				Response req = Http.get("http://www.jtbc.cn/"+key);
				Files.write("e:/common/images/"+value, req.getStream());
			}
		}*/
	}

	/**
	 * unicode 转换成 中文
	 * @param theString
	 * @return
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
}