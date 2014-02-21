package com.rekoe.utils;

import java.math.BigDecimal;

public class SqlUtils {

	public final static String JDBC_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8";
	
	public static void main(String[] args) {
		//100*5.11 应该 ＝ 511
		System.out.println(100*5.11);
		BigDecimal bg = new BigDecimal(100*5.11);
		int f1 = bg.setScale(4, BigDecimal.ROUND_HALF_UP).intValue();
		System.out.println(f1);
	}
}
