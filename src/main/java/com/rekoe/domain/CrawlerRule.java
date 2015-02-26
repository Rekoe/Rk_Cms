package com.rekoe.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("crawler_rule")
public class CrawlerRule {

	@Id
	@Column("rule_id")
	private int id;
	
	@Column("acq_name")
	@Comment("采集名称")
	private String name;
	
	@Column("start_time")
	@Comment("开始时间")
	@ColDefine(type = ColType.TIMESTAMP)
	private Date startTime;
	
	@Column("end_time")
	@Comment("停止时间")
	@ColDefine(type = ColType.TIMESTAMP)
	private Date endTime;
	
	@Column
	@Comment("当前状态(0:静止;1:采集;2:暂停)")
	private int status;
	
	@Column("curr_num")
	@Comment("当前号码")
	private int currNum;
	
	@Column("curr_item")
	@Comment("当前条数")
	private int currItem;
	
	@Column("total_item")
	@Comment("每页总条数")
	private int totalItem;
	
	@Column("pause_time")
	@Comment("暂停时间(毫秒)")
	private int pauseTime;
	
	@Column("page_encoding")
	@Comment("页面编码")
	@Default("GBK")
	private String pageEncoding;
	
	@Column("plan_list")
	@Comment("采集列表")
	@ColDefine(type = ColType.TEXT)
	private String planList;
	
	@Column("dynamic_addr")
	@Comment("动态地址")
	@ColDefine(width=1024)
	private String dynamicAddr;
	
	@Column("dynamic_start")
	@Comment("页码开始")
	private int dynamicStart;
	
	@Column("dynamic_end")
	@Comment("页码结束")
	private int dynamicEnd;
	
	@Column("linkset_start")
	@Comment("内容链接区开始")
	private String linksetStart;
	
	@Column("linkset_end")
	@Comment("内容链接区结束")
	private String linksetEnd;
	
	@Column("link_start")
	@Comment("内容链接开始")
	private String linkStart;
	
	@Column("link_end")
	@Comment("内容链接结束")
	private String linkEnd;
	
	@Column("title_start")
	@Comment("标题开始")
	private String titleStart;
	
	@Column("title_end")
	@Comment("标题结束")
	private String titleEnd;
	
	@Column("keywords_start")
	@Comment("关键字开始")
	@ColDefine(width=1024)
	private String keywordsStart;
	
	@Column("keywords_end")
	@Comment("关键字结束")
	private String keywordsEnd;
	
	@Column("description_start")
	@Comment("描述开始")
	private String descriptionStart;
	
	@Column("description_end")
	@Comment("描述结束")
	private String descriptionEnd;
	
	@Column("content_start")
	@Comment("内容开始")
	private String contentStart;
	
	@Column("content_end")
	@Comment("内容结束")
	private String contentEnd;
	
	@Column("pagination_start")
	@Comment("内容分页开始")
	private String paginationStart;
	
	@Column("pagination_end")
	@Comment("内容分页结束")
	private String paginationEnd;
	
	@Column("pagination_repair_url")
	@Comment("内容分页补全URL")
	private String paginationRepairUrl;
	
	@Column
	private int queue;
	
	@Column("repeat_check_type")
	@Comment("重复类型")
	@ColDefine(type = ColType.BOOLEAN)
	private boolean repeatCheckType;
	
	@Column("use_proxy")
	@Comment("是否使用代理")
	@ColDefine(type = ColType.BOOLEAN)
	private boolean useProxy;
	
	@Column("proxy_address")
	@Comment("代理地址")
	private String proxyAddress;
	
	@Column("proxy_port")
	@Comment("代理地址端口")
	private int proxyPort;
	
	@Column("replace_words")
	@Comment("替换字符串")
	private String replaceWords;
	
	@Column("comment_start")
	@Comment("评论内容开始标签属性")
	private String commentStart;
	
	@Column("comment_index_start")
	@Comment("内容评论列表页入口连接标签属性")
	private String commentIndexStart;
	
	@Column("comment_index_end")
	@Comment("内容评论列表页入口连接过滤标签属性")
	private String commentIndexEnd;
	
	@Column("comment_area_start")
	@Comment("评论内容列表区域标签属性")
	private String commentAreaStart;
	
	@Column("comment_area_end")
	@Comment("评论内容列表区域过滤标签属性")
	private String commentAreaEnd;
	
	@Column("comment_end")
	@Comment("评论内容过滤标签属性")
	private String commentEnd;
	
	@Column("comment_link_start")
	@Comment("评论连接标签属性")
	private String commentLinkStart;
	
	@Column("comment_link_end")
	@Comment("评论链接过虑标签属性")
	private String commentLinkEnd;

	@Column("article_category_id")
	@Comment("分类类型")
	private String articleCategoryId;

	@One(target = ArticleCategory.class, field = "articleCategoryId")
	private ArticleCategory articleCategory;// 文章分类

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCurrNum() {
		return currNum;
	}

	public void setCurrNum(int currNum) {
		this.currNum = currNum;
	}

	public int getCurrItem() {
		return currItem;
	}

	public void setCurrItem(int currItem) {
		this.currItem = currItem;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public int getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(int pauseTime) {
		this.pauseTime = pauseTime;
	}

	public String getPageEncoding() {
		return pageEncoding;
	}

	public void setPageEncoding(String pageEncoding) {
		this.pageEncoding = pageEncoding;
	}

	public String getPlanList() {
		return planList;
	}

	public void setPlanList(String planList) {
		this.planList = planList;
	}

	public String getDynamicAddr() {
		return dynamicAddr;
	}

	public void setDynamicAddr(String dynamicAddr) {
		this.dynamicAddr = dynamicAddr;
	}

	public int getDynamicStart() {
		return dynamicStart;
	}

	public void setDynamicStart(int dynamicStart) {
		this.dynamicStart = dynamicStart;
	}

	public int getDynamicEnd() {
		return dynamicEnd;
	}

	public void setDynamicEnd(int dynamicEnd) {
		this.dynamicEnd = dynamicEnd;
	}

	public String getLinksetStart() {
		return linksetStart;
	}

	public void setLinksetStart(String linksetStart) {
		this.linksetStart = linksetStart;
	}

	public String getLinksetEnd() {
		return linksetEnd;
	}

	public void setLinksetEnd(String linksetEnd) {
		this.linksetEnd = linksetEnd;
	}

	public String getLinkStart() {
		return linkStart;
	}

	public void setLinkStart(String linkStart) {
		this.linkStart = linkStart;
	}

	public String getLinkEnd() {
		return linkEnd;
	}

	public void setLinkEnd(String linkEnd) {
		this.linkEnd = linkEnd;
	}

	public String getTitleStart() {
		return titleStart;
	}

	public void setTitleStart(String titleStart) {
		this.titleStart = titleStart;
	}

	public String getTitleEnd() {
		return titleEnd;
	}

	public void setTitleEnd(String titleEnd) {
		this.titleEnd = titleEnd;
	}

	public String getKeywordsStart() {
		return keywordsStart;
	}

	public void setKeywordsStart(String keywordsStart) {
		this.keywordsStart = keywordsStart;
	}

	public String getKeywordsEnd() {
		return keywordsEnd;
	}

	public void setKeywordsEnd(String keywordsEnd) {
		this.keywordsEnd = keywordsEnd;
	}

	public String getDescriptionStart() {
		return descriptionStart;
	}

	public void setDescriptionStart(String descriptionStart) {
		this.descriptionStart = descriptionStart;
	}

	public String getDescriptionEnd() {
		return descriptionEnd;
	}

	public void setDescriptionEnd(String descriptionEnd) {
		this.descriptionEnd = descriptionEnd;
	}

	public String getContentStart() {
		return contentStart;
	}

	public void setContentStart(String contentStart) {
		this.contentStart = contentStart;
	}

	public String getContentEnd() {
		return contentEnd;
	}

	public void setContentEnd(String contentEnd) {
		this.contentEnd = contentEnd;
	}

	public String getPaginationStart() {
		return paginationStart;
	}

	public void setPaginationStart(String paginationStart) {
		this.paginationStart = paginationStart;
	}

	public String getPaginationEnd() {
		return paginationEnd;
	}

	public void setPaginationEnd(String paginationEnd) {
		this.paginationEnd = paginationEnd;
	}

	public String getPaginationRepairUrl() {
		return paginationRepairUrl;
	}

	public void setPaginationRepairUrl(String paginationRepairUrl) {
		this.paginationRepairUrl = paginationRepairUrl;
	}

	public int getQueue() {
		return queue;
	}

	public void setQueue(int queue) {
		this.queue = queue;
	}

	public boolean isRepeatCheckType() {
		return repeatCheckType;
	}

	public void setRepeatCheckType(boolean repeatCheckType) {
		this.repeatCheckType = repeatCheckType;
	}

	public boolean isUseProxy() {
		return useProxy;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

	public String getProxyAddress() {
		return proxyAddress;
	}

	public void setProxyAddress(String proxyAddress) {
		this.proxyAddress = proxyAddress;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getReplaceWords() {
		return replaceWords;
	}

	public void setReplaceWords(String replaceWords) {
		this.replaceWords = replaceWords;
	}

	public String getCommentStart() {
		return commentStart;
	}

	public void setCommentStart(String commentStart) {
		this.commentStart = commentStart;
	}

	public String getCommentIndexStart() {
		return commentIndexStart;
	}

	public void setCommentIndexStart(String commentIndexStart) {
		this.commentIndexStart = commentIndexStart;
	}

	public String getCommentIndexEnd() {
		return commentIndexEnd;
	}

	public void setCommentIndexEnd(String commentIndexEnd) {
		this.commentIndexEnd = commentIndexEnd;
	}

	public String getCommentAreaStart() {
		return commentAreaStart;
	}

	public void setCommentAreaStart(String commentAreaStart) {
		this.commentAreaStart = commentAreaStart;
	}

	public String getCommentAreaEnd() {
		return commentAreaEnd;
	}

	public void setCommentAreaEnd(String commentAreaEnd) {
		this.commentAreaEnd = commentAreaEnd;
	}

	public String getCommentEnd() {
		return commentEnd;
	}

	public void setCommentEnd(String commentEnd) {
		this.commentEnd = commentEnd;
	}

	public String getCommentLinkStart() {
		return commentLinkStart;
	}

	public void setCommentLinkStart(String commentLinkStart) {
		this.commentLinkStart = commentLinkStart;
	}

	public String getCommentLinkEnd() {
		return commentLinkEnd;
	}

	public void setCommentLinkEnd(String commentLinkEnd) {
		this.commentLinkEnd = commentLinkEnd;
	}

	/**
	 * 动态页翻页页号
	 */
	public static final String PAGE = "[page]";
	/**
	 * 停止状态
	 */
	public static final int STOP = 0;
	/**
	 * 采集状态
	 */
	public static final int START = 1;
	/**
	 * 暂停状态
	 */
	public static final int PAUSE = 2;

	public static enum RuleResultType {
		SUCCESS, TITLESTARTNOTFOUND, TITLEENDNOTFOUND, CONTENTSTARTNOTFOUND, CONTENTENDNOTFOUND, TITLEEXIST, URLEXIST, UNKNOW
	}

	public static enum AcquisitionRepeatCheckType {
		NONE, TITLE, URL
	}

	/** 总共用时 */
	private String useTime;

	/**
	 * 是否停止
	 * 
	 * @return
	 */
	public boolean isStop() {
		int status = getStatus();
		return status == 0;
	}

	/**
	 * 是否暂停
	 * 
	 * @return
	 */
	public boolean isPuase() {
		int status = getStatus();
		return status == 2;
	}

	public String getUseTime() {
		this.useTime = "";
		if (null != this.getEndTime() && null != this.getStartTime()) {
			long timeInSeconds = (this.getEndTime().getTime() - this.getStartTime().getTime());
			long days, hours, minutes, seconds;
			// 1(day)*24(hour)*60(minite)*60(seconds)*1000(millisecond)
			days = timeInSeconds / 86400000;
			timeInSeconds = timeInSeconds - (days * 3600 * 24 * 1000);
			// 1(hour)*60(minite)*60(seconds)*1000(millisecond)
			hours = timeInSeconds / 3600000;
			timeInSeconds = timeInSeconds - (hours * 3600 * 1000);
			// 1(seconds)*1000(millisecond)
			minutes = timeInSeconds / 60000;
			timeInSeconds = timeInSeconds - (minutes * 60 * 1000);
			// 1(seconds)*1000(millisecond)
			seconds = timeInSeconds / 1000;
			this.useTime = days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
		}
		return this.useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

	/**
	 * 是否中断。停止和暂停都需要中断。
	 * 
	 * @return
	 */
	public boolean isBreak() {
		int status = getStatus();
		return status == 0 || status == 2;
	}

	public String[] getPlans() {
		String plan = getPlanList();
		if (!StringUtils.isBlank(plan)) {
			return StringUtils.split(plan);
		} else {
			return new String[0];
		}
	}

	public String[] getAllPlans() {
		String[] plans = getPlans();
		Integer start = getDynamicStart();
		Integer end = getDynamicEnd();
		if (!StringUtils.isBlank(getDynamicAddr()) && start != null && end != null && start >= 0 && end >= start) {
			int plansLen = plans.length;
			String[] allPlans = new String[plansLen + end - start + 1];
			for (int i = 0; i < plansLen; i++) {
				allPlans[i] = plans[i];
			}
			for (int i = 0, len = end - start + 1; i < len; i++) {
				allPlans[plansLen + i] = getDynamicAddrByNum(start + i);
			}
			return allPlans;
		} else {
			return plans;
		}
	}

	public String getDynamicAddrByNum(int num) {
		return StringUtils.replace(getDynamicAddr(), PAGE, String.valueOf(num));
	}

	public int getTotalNum() {
		int count = 0;
		Integer start = getDynamicStart();
		Integer end = getDynamicEnd();
		if (!StringUtils.isBlank(getDynamicAddr()) && start != null && end != null) {
			count = end - start + 1;
		}
		if (!StringUtils.isBlank(getPlanList())) {
			count += getPlans().length;
		}
		return count;
	}

	public void init() {
		if (getStatus() == 0) {
			setStatus(STOP);
		}
		if (getCurrNum() == 0) {
			setCurrNum(0);
		}
		if (getCurrItem() == 0) {
			setCurrItem(0);
		}
		if (getTotalItem() == 0) {
			setTotalItem(0);
		}
		if (getPauseTime() == 0) {
			setPauseTime(0);
		}
		if (getQueue() == 0) {
			setQueue(0);
		}
	}

	public String getArticleCategoryId() {
		return articleCategoryId;
	}

	public void setArticleCategoryId(String articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}

	public ArticleCategory getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategory articleCategory) {
		this.articleCategory = articleCategory;
	}
	
}