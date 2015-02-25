package com.rekoe.crawler.core.processor.prepare;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.nutz.lang.Lang;
import org.nutz.log.Logs;

import com.rekoe.crawler.core.data.Task;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.data.uri.CrawlURI;
import com.rekoe.crawler.core.filter.Filter;
import com.rekoe.crawler.core.processor.AbstractProcessor;

/**
 * 任务处理器接口-预处理链实现类 <li>预取链：主要是做一些准备工作，例如，对处理进行延迟和重新处理，否决随后的操作</li>
 */
public class PrepareProcessor extends AbstractProcessor {

	private static final org.nutz.log.Log log = Logs.get();
	public PrepareProcessor() {
		super();
	}

	@Override
	protected void innerProcess(Task task) {
		// 如果有中间连接
		if (!Lang.isEmpty(task.getController().getCrawlScope().getMidFilterList())) {
			String tempHtml = null;
			CrawlLinkURI crawlLinkURI = task.getCrawlURI();
			log.info("=========取得中间连接---进入地址：=========" + crawlLinkURI);
			for (Filter<String, Map<String, String>> fieldfilter : task.getController().getCrawlScope().getMidFilterList()) {
				String field = null;
				List<CrawlLinkURI> tempLinkList = null;
				tempHtml = fetchHTML(task, crawlLinkURI);
				for (Iterator<String> it = fieldfilter.getFetchAreaTagMap().keySet().iterator(); it.hasNext();) {
					field = it.next();
					log.info("=========取得中间连接=========" + field);
					// 取得、过滤指定属性/标签内容
					tempLinkList = task.getController().getHtmlParserWrapper().getCrawlLinkURIByFilterMap(tempHtml, fieldfilter.getFetchAreaTagMap().get(field), fieldfilter.getDeleteAreaTagMap().get(field), crawlLinkURI);
					if (!Lang.isEmpty(tempLinkList)) {
						crawlLinkURI = tempLinkList.get(0);
						log.info("=========取得中间连接地址：=========" + crawlLinkURI);
					}
				}
			}
			log.info("=========取得中间连接---结果地址：=========" + crawlLinkURI);
			task.setCrawlURI(crawlLinkURI);
		}
	}

	/**
	 * 根据任务URL地址,取得HTML
	 * 
	 * @param task
	 *            任务
	 */
	private String fetchHTML(Task task, CrawlURI crawlURI) {
		log.info("=========下载网页,提取原始html=========");
		return task.getController().getHandler().handleResponse(task.getController().getHostCache().getHttpHostUrl(crawlURI));
	}

}
