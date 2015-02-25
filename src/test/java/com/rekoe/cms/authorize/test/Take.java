package com.rekoe.cms.authorize.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.json.Json;
import org.nutz.lang.Lang;

import com.rekoe.crawler.bean.CrawlerRuleBean;
import com.rekoe.crawler.bean.ExtendFieldsBean;
import com.rekoe.crawler.bean.RuleBaseBean;
import com.rekoe.crawler.bean.RuleContentBean;
import com.rekoe.crawler.core.CrawlerController;
import com.rekoe.crawler.core.data.CrawlScope;
import com.rekoe.crawler.core.filter.BriefAreaFilter;
import com.rekoe.crawler.core.filter.ContentAreaFilter;
import com.rekoe.crawler.core.filter.FieldFilter;
import com.rekoe.crawler.core.filter.Filter;
import com.rekoe.crawler.core.filter.LinkAreaFilter;
import com.rekoe.domain.CrawlerRule;

public class Take {

	public static void main(String[] args) {
		Take take = new Take();
		Dao dao = IocProvider.ioc().get(Dao.class);
		/*CrawlerRule crawlerRule = dao.fetch(CrawlerRule.class,Cnd.where("ruleId", "=", 81));
		//CrawlerRule
		List<Integer> ruleIdList = new ArrayList<Integer>();
		ruleIdList.add(crawlerRule.getRuleId());
		CrawlerRuleBean crawlerRuleBean = new CrawlerRuleBean();
		crawlerRuleBean.setRuleId(crawlerRule.getRuleId());
		crawlerRuleBean.setStartTime(crawlerRule.getStartTime());
		crawlerRuleBean.setEndTime(crawlerRule.getEndTime());
		crawlerRuleBean.setRuleIdList(ruleIdList);
		crawlerRuleBean.setRuleBaseBean(Json.fromJson(RuleBaseBean.class, crawlerRule.getRuleBaseConfig()));
		crawlerRuleBean.setRuleContentBean(Json.fromJson(RuleContentBean.class, crawlerRule.getRuleContentConfig()));
		//RunTestRule runTestRule = new RunTestRule(crawlerRuleBean);
		//Thread currThread = new Thread(runTestRule);
		//currThread.start();
		take.startCrawker(crawlerRuleBean);*/
	}
	
	private void startCrawker(CrawlerRuleBean rule){
		CrawlerController crawlController = new CrawlerController();
		List<Filter<String,?>> filters = new ArrayList<Filter<String,?>>();
		filters.add(new LinkAreaFilter(rule.getRuleContentBean().getLinksetStart(),rule.getRuleContentBean().getLinksetEnd()));
		filters.add(new ContentAreaFilter(rule.getRuleContentBean().getContentStart(),rule.getRuleContentBean().getContentEnd()));
		filters.add(new BriefAreaFilter(rule.getRuleContentBean().getDescriptionStart(),rule.getRuleContentBean().getDescriptionEnd()));
		//filters.add(new PaginationAreaFilter(rule.getRuleContentPageBean().getPaginationStart(),rule.getRuleContentPageBean().getPaginationEnd()));
		//filters.add(new CommentIndexFilter(rule.getRuleCommentBean().getCommentIndexStart(),rule.getRuleCommentBean().getCommentIndexEnd()));
		//filters.add(new CommentAreaFilter(rule.getRuleCommentBean().getCommentAreaStart(),rule.getRuleCommentBean().getCommentAreaEnd()));
		//filters.add(new CommentFilter(rule.getRuleCommentBean().getCommentStart(),rule.getRuleCommentBean().getCommentEnd()));
		//filters.add(new CommentLinkFilter(rule.getRuleCommentBean().getCommentLinkStart(),rule.getRuleCommentBean().getCommentLinkEnd()));
		
		
		List<Filter<String,Map<String,String>>> midFilters = new ArrayList<Filter<String,Map<String,String>>>();
		//添加过度连接过滤器
		if(null != rule.getRuleContentBean() && !Lang.isEmpty(rule.getRuleContentBean().getMidExtendFields())){
			addFilter(rule.getRuleContentBean().getMidExtendFields(),midFilters);
		}
		
		
		List<Filter<String,Map<String,String>>> multeityFilters = new ArrayList<Filter<String,Map<String,String>>>();
		//添加扩展字段过滤器
		if(null != rule.getRuleFieldsBean() && !Lang.isEmpty(rule.getRuleFieldsBean().getExtendFields())){
			addFilter(rule.getRuleFieldsBean().getExtendFields(),multeityFilters);
		}
		CrawlScope crawlScope = new CrawlScope();
		//crawlScope.setCrawlerPersistent(this.getCrawlerPersistent());
		crawlScope.setEncoding(rule.getRuleBaseBean().getPageEncoding());
		crawlScope.setId(rule.getRuleId());
		crawlScope.setFilterList(filters);
		crawlScope.setMidFilterList(midFilters);
		crawlScope.setMulteityFilterList(multeityFilters);
		//评论内容列表是否与内容页分离，如果填写了,则为true
		if(null != rule.getRuleCommentBean() && StringUtils.isNotEmpty(rule.getRuleCommentBean().getCommentIndexStart())){
			crawlScope.setCommentListIsAlone(true);
		}
		crawlScope.setRepairPageUrl(rule.getRuleBaseBean().getUrlRepairUrl());
		crawlScope.setRepairImageUrl(rule.getRuleBaseBean().getResourceRepairUrl());
		crawlScope.setPaginationRepairUrl(rule.getRuleBaseBean().getPaginationRepairUrl());
		//设置休眠时间
		crawlScope.setSleepTime(rule.getRuleBaseBean().getPauseTime());
		//是否下载图片至本地
		crawlScope.setExtractContentRes(Boolean.valueOf(rule.getRuleBaseBean().getSaveResourceFlag()));
		//是否去掉内容中连接
		crawlScope.setReplaceHtmlLink(Boolean.valueOf(rule.getRuleBaseBean().getReplaceLinkFlag()));
		crawlScope.setAllowRepeat(Boolean.valueOf(rule.getRuleBaseBean().getRepeatCheckFlag()));
		crawlScope.setUseProxy(Boolean.valueOf(rule.getRuleBaseBean().getUseProxyFlag()));
		crawlScope.setGatherOrder(Boolean.valueOf(rule.getRuleBaseBean().getGatherOrderFlag()));
		
		crawlScope.setProxyAddress(rule.getRuleBaseBean().getProxyAddress());
		crawlScope.setProxyPort(rule.getRuleBaseBean().getProxyPort());
		crawlScope.setReplaceWords(rule.getRuleBaseBean().getReplaceWords());

		//随机生成日期
		crawlScope.setDateFormat(rule.getRuleBaseBean().getDateFormat());
		crawlScope.setRandomDateFlag(Boolean.valueOf(rule.getRuleBaseBean().getRandomDateFlag()));
		crawlScope.setStartRandomDate(rule.getRuleBaseBean().getStartRandomDate());
		crawlScope.setEndRandomDate(rule.getRuleBaseBean().getEndRandomDate());
		crawlScope.setGatherNum(rule.getRuleBaseBean().getGatherNum());
		
		crawlScope.addSeeds(rule.getRuleContentBean().getAllPlans());
		
		crawlController.initialize(crawlScope);
		crawlController.start();
	}
	
	private void addFilter(List<ExtendFieldsBean> extendFields,List<Filter<String,Map<String,String>>> filters){
		for(ExtendFieldsBean extendFieldsBean : extendFields){
			filters.add(new FieldFilter(extendFieldsBean.getFields(),extendFieldsBean.getFilterStart(),extendFieldsBean.getFilterEnd()));
		}
	}
}
