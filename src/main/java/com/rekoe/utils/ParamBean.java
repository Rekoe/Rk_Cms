package com.rekoe.utils;

import java.util.HashMap;
import java.util.Map;

public class ParamBean {
	/** 待采集连接区域属性MAP */
	private Map<String, String> linksetStartMap = new HashMap<String, String>();
	/** 待采集连接区域过滤属性MAP */
	private Map<String, String> linksetEndMap = new HashMap<String, String>();
	/** 待采集内容区域属性MAP */
	private Map<String, String> contentStartMap = new HashMap<String, String>();
	/** 待采集内容区域过滤属性MAP */
	private Map<String, String> contentEndMap = new HashMap<String, String>();

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

}