package com.rekoe.crawler.core.filter.factory;

import java.util.List;

import com.rekoe.crawler.core.filter.Filter;

/**
 * 过滤器工厂
 */
public interface FilterFactory<K,V> {
	/**
	 * 注册过滤器
	 * @param filterList 过滤器集合
	 */
	void register(List<Filter<K,V>> filterList);
	/**
	 * 注册多个过滤器
	 * <p>方法说明:</p>
	 * <li></li>
	 * @auther DuanYong
	 * @since 2013-3-23 下午11:05:13
	 * @param filterList
	 * @return void
	 */
	void registerMulteity(List<Filter<K,V>> filterList);
	/**
	 * 根据过滤器名称查找已注册过滤器,如果未找到则返回一个空的过滤器
	 * @param filterName 过滤器名称
	 * @return 过滤器
	 */
	Filter<K,V> getFilterByName(String filterName);
	/**
	 * 根据过滤器名称查找已注册过滤器集合,如果未找到则返回一个空的过滤器
	 * @param filterName 过滤器名称
	 * @return 过滤器
	 */
	List<Filter<K,V>> getFilterListByName(String filterName);
	/**
	 * 清理缓存
	 */
	void clear();
}
