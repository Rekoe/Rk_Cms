package com.rekoe.crawler.core.filter.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.nutz.lang.Lang;

import com.rekoe.crawler.core.filter.EmptyFilter;
import com.rekoe.crawler.core.filter.Filter;

/**
 * 过滤器工厂接口默认实现类
 */
public class DefaultFilterFactory implements FilterFactory<String, String> {
	/** 过滤器map */
	private Map<String, Filter<String, String>> filterMap = new ConcurrentHashMap<String, Filter<String, String>>();
	/** 多过滤器map */
	private Map<String, List<Filter<String, String>>> multeityFilterMap = new ConcurrentHashMap<String, List<Filter<String, String>>>();

	public Filter<String, String> getFilterByName(String filterName) {
		if (filterMap.containsKey(filterName)) {
			return filterMap.get(filterName);
		}
		return new EmptyFilter("", "");
	}

	public List<Filter<String, String>> getFilterListByName(String filterName) {
		if (multeityFilterMap.containsKey(filterName)) {
			return multeityFilterMap.get(filterName);
		}
		List<Filter<String, String>> emptyList = new ArrayList<Filter<String, String>>();
		emptyList.add(new EmptyFilter("", ""));
		return emptyList;
	}

	/**
	 * 注册过滤器
	 * 
	 * @param filterList
	 *            过滤器集合
	 */
	public void register(List<Filter<String, String>> filterList) {
		if (!Lang.isEmpty(filterList)) {
			for (Filter<String, String> filter : filterList) {
				filterMap.put(filter.getFilterName(), filter);
			}
		}
	}

	/**
	 * 注册过滤器
	 * 
	 * @param filterList
	 *            过滤器集合
	 */
	public void registerMulteity(List<Filter<String, String>> filterList) {
		if (!Lang.isEmpty(filterList)) {
			for (Filter<String, String> filter : filterList) {
				if (multeityFilterMap.containsKey(filter.getFilterName())) {
					multeityFilterMap.get(filter.getFilterName()).add(filter);
				} else {
					List<Filter<String, String>> list = new ArrayList<Filter<String, String>>();
					list.add(filter);
					multeityFilterMap.put(filter.getFilterName(), list);
				}
			}
		}
	}

	/**
	 * 清理
	 */
	public void clear() {
		filterMap.clear();
		multeityFilterMap.clear();
	}

}
