package com.rekoe.crawler.core.filter;

import java.util.Map;
/**
 * 过滤器接口
 */
public interface Filter<K,V> {
	/**
	 * 取得当前过滤器名称
	 * @return 过滤器名称
	 */
	String getFilterName();
	/**
	 * 取得当前过滤器内容
	 * @return 过滤器内容
	 */
	Map<String,String> getFilter();
	/**
	 * 取得待提取区域标签属性Map
	 * @return 待提取区域标签属性Map
	 */
	Map<K, V> getFetchAreaTagMap();
	/**
	 * 取得待提取区域中要删除的标签属性Map
	 * @return 待提取区域中要删除的标签属性Map
	 */
	Map<K, V> getDeleteAreaTagMap();
}
