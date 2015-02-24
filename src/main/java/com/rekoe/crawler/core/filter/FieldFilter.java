package com.rekoe.crawler.core.filter;

import java.util.Map;

import com.rekoe.crawler.core.constants.Constants;


/**
 * 过滤器接口实现类-字段过滤
 * <li>采集指定参数的内容到指定字段</li>
 * @author javacoo
 * @since 2011-12-03
 * @LastModify 2012-05-12
 */
public class FieldFilter extends AbstractFilter<String,Map<String, String>>{
	/**
	 * <li>约定采集参数格式如下</li>
	 * <li>1，标签属性/值形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN</li>
	 * <li>2，标签名称形式，如：div,p,span</li>
	 * <li>3，混合形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN,div,p,span</li>
	 * @param fieldName 采集到的内容将保存到该字段
	 * @param fethAreaTagStr 要采集的区域标签字符串
	 * @param filterAreaTagStr 要过滤的区域标签字符串
	 */
	public FieldFilter(String fieldName,String fethAreaTagStr, String filterAreaTagStr){
		super(fieldName,fethAreaTagStr,filterAreaTagStr);
		this.filterName = Constants.FILTER_NAME_FIELD;
	}
	
	/**
	 * 构建标签属性Map
	 */
	public void buildFilterMap(){
		this.fetchAreaTagMap.put(this.fieldName, parserAreaTagStrToBuildFilterMap(this.fethAreaTagStr));
		this.deleteAreaTagMap.put(this.fieldName, parserAreaTagStrToBuildFilterMap(this.deleteAreaTagStr));
	}
}
