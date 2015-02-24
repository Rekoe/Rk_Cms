package org.javacoo.crawler.core.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rekoe.crawler.core.data.uri.CrawlResURI;


/**
 * 采集内容值对象
 * @author javacoo
 * @since 2011-11-8
 */
public class ContentBean {
	/**采集任务ID*/
	private Integer acquId;
	/**标题*/
	private String title;
	/**文章内容*/
	private String content;
	/**原始HTML内容*/
	private String orginHtml;
	/**分页内容集合*/
	private List<String> contentList = new ArrayList<String>();
	/**评论内容集合*/
	private List<String> commentList = new ArrayList<String>();
	/**摘要*/
	private String brief;
	/**标题图片*/
	private String titleImg;
	/**资源URI集合*/
	private List<CrawlResURI> resCrawlURIList = new ArrayList<CrawlResURI>();
	/**字段对应值Map*/
	private Map<String,String> fieldValueMap = new HashMap<String, String>();
	
	public ContentBean() {
		super();
	}
	public ContentBean(String title, String content, Integer acquId) {
		super();
		this.title = title;
		this.content = content;
		this.acquId = acquId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getOrginHtml() {
		return orginHtml;
	}
	public void setOrginHtml(String orginHtml) {
		this.orginHtml = orginHtml;
	}
	public Integer getAcquId() {
		return acquId;
	}
	public void setAcquId(Integer acquId) {
		this.acquId = acquId;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public Map<String, String> getFieldValueMap() {
		return fieldValueMap;
	}
	public void setFieldValueMap(Map<String, String> fieldValueMap) {
		this.fieldValueMap = fieldValueMap;
	}
	public List<String> getContentList() {
		return contentList;
	}
	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}
	
	public List<String> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<String> commentList) {
		this.commentList = commentList;
	}
	
	
	public List<CrawlResURI> getResCrawlURIList() {
		return resCrawlURIList;
	}
	public void setResCrawlURIList(List<CrawlResURI> resCrawlURIList) {
		this.resCrawlURIList = resCrawlURIList;
	}
	
	/**
	 * 清理方法，主要是清理采集回来的内容
	 */
	public void clear(){
		setContent(null);
		setOrginHtml(null);
		setBrief(null);
		setTitleImg(null);
		this.fieldValueMap.clear();
		setFieldValueMap(null);
		this.contentList.clear();
		setContentList(null);
		this.commentList.clear();
		setCommentList(null);
		this.resCrawlURIList.clear();
		setResCrawlURIList(null);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContentBean other = (ContentBean) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	

}
