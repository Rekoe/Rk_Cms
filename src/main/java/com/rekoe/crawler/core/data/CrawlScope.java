package com.rekoe.crawler.core.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.filter.Filter;
import com.rekoe.crawler.core.util.File.DefaultFileHelper;
import com.rekoe.utils.CommonUtils;

/**
 * 采集器配置
 * 
 * @author javacoo
 * @since 2011-11-09
 */
public class CrawlScope {
	/** 采集ID */
	private int id;
	/** 采集页面编码 */
	private String encoding;
	/**
	 * 页面补全URL 有些连接是相对地址，顾需补全
	 * */
	private String repairPageUrl;
	/**
	 * 图片补全URL 有些连接是相对地址，顾需补全
	 * */
	private String repairImageUrl;
	/**
	 * 内容分页补全URL 有些连接是相对地址，顾需补全
	 * */
	private String paginationRepairUrl;
	/** 是否将内容中的外部资源采集回来 */
	private boolean extractContentRes;
	/** 是否将内容中的超级链接去除 */
	private boolean replaceHtmlLink;
	/** 采集单过滤器列表 */
	private List<Filter> filterList;
	/** 过度过滤器列表 */
	private List<Filter> midFilterList;
	/** 采集多过滤器列表 */
	private List<Filter> multeityFilterList;
	/** 评论内容列表是否与内容页分离,默认false */
	private boolean commentListIsAlone = false;
	/** 采集种子列表 */
	private List<String> seeds = new ArrayList<String>();
	/** 唯一标示生成接口,默认实现 */
	/** 文件帮助类 */
	private com.rekoe.crawler.core.util.File.FileHelper fileHelper = new DefaultFileHelper();
	/** 每个线程休眠毫秒数 */
	private int sleepTime = Constants.SLEEP_TIME;
	/** 是否允许采集重复数据,默认允许 */
	private boolean allowRepeat = Constants.ALLOW_REPEAT;
	/** 是否使用代理 */
	private boolean useProxy = Constants.USE_PROXY;
	/** 代理地址 */
	private String proxyAddress;
	/** 代理端口 */
	private String proxyPort;
	/** 替换字符串 */
	private String replaceWords;
	/** 是否随机日期 */
	private boolean randomDateFlag;
	/** 随机日期格式 */
	private String dateFormat;
	/** 随机日期开始 */
	private String startRandomDate;
	/** 随机日期结束 */
	private String endRandomDate;
	/** 替换字符串map */
	private Map<String, String> replaceWordsMap = Constants.DEFAULT_COMMON_REPLACE_WORDS;
	/** 扩展字段键值字符窜 */
	private String extendField;
	/** 是否保存数据库 */
	private boolean saveToDataBase = Constants.USE_PROXY;
	/** 采集顺序 */
	private boolean gatherOrder = Constants.GATHER_ORDER;
	/** 采集数量 */
	private String gatherNum;

	public List<String> getSeeds() {
		return seeds;
	}

	public void setSeeds(List<String> seeds) {
		this.seeds = seeds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Filter> getFilterList() {
		return filterList;
	}

	public void setFilterList(List<Filter> filterList) {
		this.filterList = filterList;
	}

	/**
	 * @return the midFilterList
	 */
	public List<Filter> getMidFilterList() {
		return midFilterList;
	}

	/**
	 * @param midFilterList
	 *            the midFilterList to set
	 */
	public void setMidFilterList(List<Filter> midFilterList) {
		this.midFilterList = midFilterList;
	}

	public List<Filter> getMulteityFilterList() {
		return multeityFilterList;
	}

	public void setMulteityFilterList(List<Filter> multeityFilterList) {
		this.multeityFilterList = multeityFilterList;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void addSeed(String url) {
		seeds.add(url);
	}

	public void addSeeds(List<String> urlList) {
		seeds.addAll(urlList);
	}

	public void addSeeds(String[] urlArray) {
		for (String url : urlArray) {
			addSeed(url);
		}
	}

	public boolean isExtractContentRes() {
		return extractContentRes;
	}

	public void setExtractContentRes(boolean replaceHtmlImage) {
		this.extractContentRes = replaceHtmlImage;
	}

	public boolean isReplaceHtmlLink() {
		return replaceHtmlLink;
	}

	public void setReplaceHtmlLink(boolean replaceHtmlLink) {
		this.replaceHtmlLink = replaceHtmlLink;
	}

	public String getRepairPageUrl() {
		return repairPageUrl;
	}

	public void setRepairPageUrl(String repairPageUrl) {
		this.repairPageUrl = repairPageUrl;
	}

	public String getRepairImageUrl() {
		return repairImageUrl;
	}

	public void setRepairImageUrl(String repairImageUrl) {
		this.repairImageUrl = repairImageUrl;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public boolean isAllowRepeat() {
		return allowRepeat;
	}

	public void setAllowRepeat(boolean allowRepeat) {
		this.allowRepeat = allowRepeat;
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

	public String getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public boolean isCommentListIsAlone() {
		return commentListIsAlone;
	}

	public void setCommentListIsAlone(boolean commentListIsAlone) {
		this.commentListIsAlone = commentListIsAlone;
	}

	public String getReplaceWords() {
		return replaceWords;
	}

	public void setReplaceWords(String replaceWords) {
		this.replaceWords = replaceWords;
	}

	public Map<String, String> getReplaceWordsMap() {
		Map<String, String> currMap = CommonUtils.populateWordsMap(this.replaceWords);
		if (null != currMap) {
			replaceWordsMap.putAll(currMap);

		}
		return replaceWordsMap;
	}

	public void setReplaceWordsMap(Map<String, String> replaceWordsMap) {
		this.replaceWordsMap = replaceWordsMap;
	}

	public boolean isSaveToDataBase() {
		return saveToDataBase;
	}

	public void setSaveToDataBase(boolean saveToDataBase) {
		this.saveToDataBase = saveToDataBase;
	}

	public String getExtendField() {
		return extendField;
	}

	public void setExtendField(String extendField) {
		this.extendField = extendField;
	}

	public String getSavePath() {
		StringBuilder savePath = new StringBuilder();
		if (StringUtils.isNotBlank(Constants.RES_SAVE_PATH)) {
			savePath.append(Constants.RES_SAVE_ROOT_PATH).append(Constants.RES_SAVE_PATH).append("/");
		} else {
			SimpleDateFormat dtP = new SimpleDateFormat(Constants.GENERATOR_SIMPLE_P_FORMAT);
			SimpleDateFormat dtC = new SimpleDateFormat(Constants.GENERATOR_SIMPLE_C_FORMAT);
			savePath.append(Constants.RES_SAVE_ROOT_PATH).append(dtP.format(new java.util.Date())).append("/").append(dtC.format(new java.util.Date())).append("/");
		}
		return savePath.toString();
	}

	public boolean isRandomDateFlag() {
		return randomDateFlag;
	}

	public void setRandomDateFlag(boolean randomDateFlag) {
		this.randomDateFlag = randomDateFlag;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getStartRandomDate() {
		return startRandomDate;
	}

	public void setStartRandomDate(String startRandomDate) {
		this.startRandomDate = startRandomDate;
	}

	public String getEndRandomDate() {
		return endRandomDate;
	}

	public void setEndRandomDate(String endRandomDate) {
		this.endRandomDate = endRandomDate;
	}

	public String getPaginationRepairUrl() {
		return paginationRepairUrl;
	}

	public void setPaginationRepairUrl(String paginationRepairUrl) {
		this.paginationRepairUrl = paginationRepairUrl;
	}

	public boolean isGatherOrder() {
		return gatherOrder;
	}

	public void setGatherOrder(boolean gatherOrder) {
		this.gatherOrder = gatherOrder;
	}

	public String getGatherNum() {
		return gatherNum;
	}

	public void setGatherNum(String gatherNum) {
		this.gatherNum = gatherNum;
	}

	/**
	 * 完成采集任务
	 */
	public void finished() {
		// TODO
	}

	public static void main(String[] args) {
		SimpleDateFormat dt = new SimpleDateFormat("dd");
		System.out.println(dt.format(new java.util.Date()).toString());
	}

}
