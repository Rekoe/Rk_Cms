package com.rekoe.cms.authorize.test;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.json.Json;

import com.rekoe.crawler.bean.CrawlerRuleBean;
import com.rekoe.crawler.bean.RuleBaseBean;
import com.rekoe.crawler.bean.RuleContentBean;
import com.rekoe.domain.CrawlerRule;

public class Take {

	public static void main(String[] args) {
		Dao dao = IocProvider.ioc().get(Dao.class);
		CrawlerRule crawlerRule = dao.fetch(CrawlerRule.class,Cnd.where("ruleId", "=", 81));
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
		RunTestRule runTestRule = new RunTestRule(crawlerRuleBean);
		Thread currThread = new Thread(runTestRule);
		currThread.start();
	}
}
