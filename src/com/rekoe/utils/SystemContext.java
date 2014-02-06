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