package com.rekoe.crawler.core.filter.factory;

import java.util.HashMap;
import java.util.Map;
/**
 * 过滤器构建接口
 * @author javacoo
 * @since 2012-05-10
 */
public class FilterBean {
	/**待采集连接区域属性MAP*/
	private Map<String, String> linksetStartMap = new HashMap<String, String>();
	/**待采集连接区域过滤属性MAP*/
	private Map<String, String> linksetEndMap = new HashMap<String, String>();
	/**待采集内容区域属性MAP*/
	private Map<String, String> contentStartMap = new HashMap<String, String>();
	/**待采集内容区域过滤属性MAP*/
	private Map<String, String> contentEndMap = new HashMap<String, String>();
	/**待采集内容区域属性MAP*/
	private Map<String, String> briefStartMap = new HashMap<String, String>();
	/**待采集摘要区域过滤属性MAP*/
	private Map<String, String> briefEndMap = new HashMap<String, String>();
	/**待采集内容分页区域属性MAP*/
	private Map<String, String> paginationStartMap = new HashMap<String, String>();
	/**待采集摘要区域过滤属性MAP*/
	private Map<String, String> paginationEndMap = new HashMap<String, String>();
	/**待采集摘要字段区域属性MAP*/
	private Map<String, Map<String, String>> fieldStartMap = new HashMap<String, Map<String, String>>();
	/**待采集字段区域过滤属性MAP*/
	private Map<String, Map<String, String>> fieldEndMap = new HashMap<String, Map<String, String>>();
	/**待采集评论列表入口连接区域属性MAP*/
	private Map<String, String> commentIndexStartMap = new HashMap<String, String>();
	/**待采集评论列表入口连接区域过滤属性MAP*/
	private Map<String, String> commentIndexEndMap = new HashMap<String, String>();
	/**待采集评论区域属性MAP*/
	private Map<String, String> commentStartMap = new HashMap<String, String>();
	/**待采集评论内容列表区域属性MAP*/
	private Map<String, String> commentAreaStartMap = new HashMap<String, String>();
	/**待采集评论内容列表区域过滤属性MAP*/
	private Map<String, String> commentAreaEndMap = new HashMap<String, String>();
	/**待采集评论区域过滤属性MAP*/
	private Map<String, String> commentEndMap = new HashMap<String, String>();
	/**待采集评论链接区域属性MAP*/
	private Map<String, String> commentLinkStartMap = new HashMap<String, String>();
	/**待采集评论链接域过滤属性MAP*/
	private Map<String, String> commentLinkEndMap = new HashMap<String, String>();
	
	
	
	public Map<String, String> getLinksetStartMap() {
		return linksetStartMap;
	}
	public void setLinksetStartMap(Map<String, String> linksetStartMap) {
		this.linksetStartMap = linksetStartMap;
	}
	public Map<String, String> getLinksetEndMap() {
		return linksetEndMap;
	}
	public void setLinksetEndMap(Map<String, String> linksetEndMap) {
		this.linksetEndMap = linksetEndMap;
	}
	public Map<String, String> getContentStartMap() {
		return contentStartMap;
	}
	public void setContentStartMap(Map<String, String> contentStartMap) {
		this.contentStartMap = contentStartMap;
	}
	public Map<String, String> getContentEndMap() {
		return contentEndMap;
	}
	public void setContentEndMap(Map<String, String> contentEndMap) {
		this.contentEndMap = contentEndMap;
	}
	public Map<String, Map<String, String>> getFieldStartMap() {
		return fieldStartMap;
	}
	public void setFieldStartMap(Map<String, Map<String, String>> fieldStartMap) {
		this.fieldStartMap = fieldStartMap;
	}
	public Map<String, Map<String, String>> getFieldEndMap() {
		return fieldEndMap;
	}
	public void setFieldEndMap(Map<String, Map<String, String>> fieldEndMap) {
		this.fieldEndMap = fieldEndMap;
	}
	public void addFieldStartMap(String key,Map<String, String> value){
		this.fieldStartMap.put(key, value);
	}
	public Map<String, String> getFieldStartMapByField(String key){
		return this.fieldStartMap.get(key);
	}
	public void addFieldEndMap(String key,Map<String, String> value){
		this.fieldEndMap.put(key, value);
	}
	public Map<String, String> getFieldEndMapByField(String key){
		return this.fieldEndMap.get(key);
	}
	public Map<String, String> getBriefStartMap() {
		return briefStartMap;
	}
	public void setBriefStartMap(Map<String, String> briefStartMap) {
		this.briefStartMap = briefStartMap;
	}
	public Map<String, String> getBriefEndMap() {
		return briefEndMap;
	}
	public void setBriefEndMap(Map<String, String> briefEndMap) {
		this.briefEndMap = briefEndMap;
	}
	public Map<String, String> getPaginationStartMap() {
		return paginationStartMap;
	}
	public void setPaginationStartMap(Map<String, String> paginationStartMap) {
		this.paginationStartMap = paginationStartMap;
	}
	public Map<String, String> getPaginationEndMap() {
		return paginationEndMap;
	}
	public void setPaginationEndMap(Map<String, String> paginationEndMap) {
		this.paginationEndMap = paginationEndMap;
	}
	
	public Map<String, String> getCommentIndexStartMap() {
		return commentIndexStartMap;
	}
	public void setCommentIndexStartMap(Map<String, String> commentIndexStartMap) {
		this.commentIndexStartMap = commentIndexStartMap;
	}
	public Map<String, String> getCommentIndexEndMap() {
		return commentIndexEndMap;
	}
	public void setCommentIndexEndMap(Map<String, String> commentIndexEndMap) {
		this.commentIndexEndMap = commentIndexEndMap;
	}
	public Map<String, String> getCommentStartMap() {
		return commentStartMap;
	}
	public void setCommentStartMap(Map<String, String> commentStartMap) {
		this.commentStartMap = commentStartMap;
	}
	public Map<String, String> getCommentEndMap() {
		return commentEndMap;
	}
	public void setCommentEndMap(Map<String, String> commentEndMap) {
		this.commentEndMap = commentEndMap;
	}
	public Map<String, String> getCommentLinkStartMap() {
		return commentLinkStartMap;
	}
	public void setCommentLinkStartMap(Map<String, String> commentLinkStartMap) {
		this.commentLinkStartMap = commentLinkStartMap;
	}
	public Map<String, String> getCommentLinkEndMap() {
		return commentLinkEndMap;
	}
	public void setCommentLinkEndMap(Map<String, String> commentLinkEndMap) {
		this.commentLinkEndMap = commentLinkEndMap;
	}
	public Map<String, String> getCommentAreaStartMap() {
		return commentAreaStartMap;
	}
	public void setCommentAreaStartMap(Map<String, String> commentAreaStartMap) {
		this.commentAreaStartMap = commentAreaStartMap;
	}
	public Map<String, String> getCommentAreaEndMap() {
		return commentAreaEndMap;
	}
	public void setCommentAreaEndMap(Map<String, String> commentAreaEndMap) {
		this.commentAreaEndMap = commentAreaEndMap;
	}
	
}
