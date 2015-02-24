package com.rekoe.crawler.core.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.rekoe.crawler.core.constants.Constants;

/**
 * 过滤器接口实现类-抽象实现类
 * @author javacoo
 * @since 2011-11-10
 * @LastModify 2012-05-12
 */
public abstract class AbstractFilter<K,V> implements Filter<K,V>{
	/**过滤器内容MAP*/
	protected Map<String,String> areaTagStrMap = new HashMap<String,String>();
	/**待提取区域标签属性Map*/
	protected Map<K,V> fetchAreaTagMap = new HashMap<K,V>();
	/**待提取区域中要删除的标签属性Map*/
	protected Map<K,V> deleteAreaTagMap = new HashMap<K,V>();
	/**待提取区域标签属性字符串*/
	protected String fethAreaTagStr;
	/**待提取区域中要删除的标签属性字符串*/
	protected String deleteAreaTagStr;
	/**过滤器名称*/
	protected String filterName;
	/**字段名称*/
	protected String fieldName;
	/**
	 * <li>约定采集参数格式如下</li>
	 * <li>1，标签属性/值形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN</li>
	 * <li>2，标签名称形式，如：div,p,span</li>
	 * <li>3，混合形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN,div,p,span</li>
	 * @param fethAreaTagStr 要采集的区域标签字符串
	 * @param deleteAreaTagStr 要过滤的区域标签字符串
	 */
	public AbstractFilter(String fethAreaTagStr, String deleteAreaTagStr){
		build(fethAreaTagStr,deleteAreaTagStr);
	}
	public AbstractFilter(String fieldName,String fethAreaTagStr, String deleteAreaTagStr){
		this.fieldName = fieldName;
		build(fethAreaTagStr,deleteAreaTagStr);
	}
	private final void build(String fethAreaTagStr, String deleteAreaTagStr){
		initialize(fethAreaTagStr,deleteAreaTagStr);
		verify();
		buildFilterMap();
	}
	/**
	 * 构建标签属性Map
	 */
	protected abstract void buildFilterMap();
	/**
	 * 标签属性字符串格式校验
	 * @return
	 */
	protected void verify(){}
	
	public Map<String, String> getFilter() {
		return areaTagStrMap;
	}

	public String getFilterName() {
		return this.filterName;
	}
	/**
	 * 取得待提取区域标签属性Map
	 * @return 待提取区域标签属性Map
	 */
	public Map<K, V> getFetchAreaTagMap(){
		return this.fetchAreaTagMap;
	}
	/**
	 * 取得待提取区域中要删除的标签属性Map
	 * @return 待提取区域中要删除的标签属性Map
	 */
	public Map<K, V> getDeleteAreaTagMap(){
		return this.deleteAreaTagMap;
	}
	/**
	 * 设置过滤器区域标签字符串map
	 * @param fethAreaTagStr 要采集的区域标签字符串
	 * @param filterAreaTagStr 要过滤的区域标签字符串
	 */
	private void initialize(String fethAreaTagStr, String deleteAreaTagStr){
		this.fethAreaTagStr = fethAreaTagStr;
		this.deleteAreaTagStr = deleteAreaTagStr;
	}
	
	/**
	 * 解析并组装采集参数，支持标签属性/值形式和标签名称形式，可混合使用
	 * <li>约定采集参数格式如下</li>
	 * <li>1，标签属性/值形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN</li>
	 * <li>2，标签名称形式，如：div,p,span</li>
	 * <li>3，混合形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN,div,p,span</li>
	 * @param areaTagStr 标签属性字符串
	 */
	protected Map<String, String> parserAreaTagStrToBuildFilterMap(String areaTagStr) {
		Map<String, String> filterMap = new ConcurrentHashMap<String, String>();
		if(StringUtils.isNotBlank(areaTagStr)){
			String[] paramStrArr = areaTagStr.split(",");
			String[] tempStrArr = null;
			StringBuilder sb = new StringBuilder();
			for(String temp : paramStrArr){
				if(temp.contains("=")){
					tempStrArr = temp.split("=");
					filterMap.put(tempStrArr[0], tempStrArr[1]);
				}else{
					if(StringUtils.isNotBlank(temp)){
						sb.append(temp).append("|");
					}
				}
			}
			if(StringUtils.isNotBlank(sb.toString())){
				filterMap.put(Constants.SINGLE_TAG, sb.substring(0, sb.length() - 1));
			}
		}
		return filterMap;
	}
}
