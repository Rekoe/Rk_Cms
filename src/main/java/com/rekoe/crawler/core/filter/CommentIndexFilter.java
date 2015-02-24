package com.rekoe.crawler.core.filter;

import com.rekoe.crawler.core.constants.Constants;

/**
 * 过滤器接口实现类-评论列表入口连接区域过滤
 * @author javacoo
 * @since 2012-05-11
 * @LastModify 2012-05-12
 */
public class CommentIndexFilter extends AbstractFilter<String,String>{
	/**
	 * <li>约定采集参数格式如下</li>
	 * <li>1，标签属性/值形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN</li>
	 * <li>2，标签名称形式，如：div,p,span</li>
	 * <li>3，混合形式，如：class=articleList|tips,id=fxwb|fxMSN|fxMSN,div,p,span</li>
	 * @param fethAreaTagStr 要采集的区域标签字符串
	 * @param deleteAreaTagStr 要过滤的区域标签字符串
	 */
	public CommentIndexFilter(String fethAreaTagStr, String deleteAreaTagStr){
		super(fethAreaTagStr,deleteAreaTagStr);
		this.filterName = Constants.FILTER_NAME_COMMENT_INDEX_AREA;
	}
	/**
	 * 构建标签属性Map
	 */
	public void buildFilterMap(){
		this.fetchAreaTagMap = parserAreaTagStrToBuildFilterMap(this.fethAreaTagStr);
		this.deleteAreaTagMap = parserAreaTagStrToBuildFilterMap(this.deleteAreaTagStr);
	}
}
