package com.rekoe.utils;

import java.util.List;

public interface ParseHtmlTool {
	/**
	 * 取得连接集合
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @return 连接集合
	 */
	List<String> getUrlList(String orginHtml);

	/**
	 * 取得标题集合
	 * 
	 * @param orginHtml
	 *            原始HTML
	 * @return 标题集合
	 */
	List<String> getTitleList(String orginHtml);

	/**
	 * 取得指定区域的HTML内容
	 * 
	 * @return 指定区域的HTML内容
	 */
	String getHtml(String orginHtml);
}