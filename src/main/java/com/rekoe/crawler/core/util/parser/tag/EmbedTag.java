package com.rekoe.crawler.core.util.parser.tag;

import org.htmlparser.tags.CompositeTag;
/**
 * 自定义标签
 * <li>抽取链 ： 当提取完成时 ， 抽取感兴趣的 HTML 或者 JavaScript等 </li>
 */
public class EmbedTag extends CompositeTag {
	private static final long serialVersionUID = 1L;
	private static final String[] mIds = new String[] {"embed"};
    private static final String SRC = "src";
    private static final String TYPE = "type";
	public String[] getIds() {
		return mIds;
	}
 
	public String[] getEnders() {
		return mIds;
	}
	
	public String getSrc(){
		return this.getAttribute(SRC);
	}
	public void setSrc(String value){
		this.setAttribute(SRC, value);
	}
	public String getType(){
		return this.getAttribute(TYPE);
	}
}
