package org.javacoo.crawler.core.processor.prepare;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.javacoo.crawler.core.data.Task;
import org.javacoo.crawler.core.processor.AbstractProcessor;
import org.javacoo.crawler.core.util.CollectionUtils;
import org.javacoo.crawler.core.util.HttpClientHelper;
import org.nutz.http.Http;
import org.nutz.lang.Lang;

import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import com.rekoe.crawler.core.data.uri.CrawlURI;
import com.rekoe.crawler.core.filter.Filter;

/**
 * 任务处理器接口-预处理链实现类
 * <li>预取链：主要是做一些准备工作，例如，对处理进行延迟和重新处理，否决随后的操作</li>
 * @author javacoo
 * @since 2011-11-09
 */
public class PrepareProcessor extends AbstractProcessor{

	public PrepareProcessor() {
		super();
	}

	@Override
	protected void innerProcess(Task task) {
		//如果有中间连接
		if(!Lang.isEmpty(task.getController().getCrawlScope().getMidFilterList())){
			String tempHtml = null;
			CrawlLinkURI crawlLinkURI = task.getCrawlURI();
			log.info("=========取得中间连接---进入地址：========="+crawlLinkURI);
			for(Filter<String,Map<String, String>> fieldfilter : task.getController().getCrawlScope().getMidFilterList()){
				String field = null;
				List<CrawlLinkURI> tempLinkList =null;
				tempHtml = fetchHTML(task,crawlLinkURI);
				for(Iterator<String> it = fieldfilter.getFetchAreaTagMap().keySet().iterator(); it.hasNext();){
					field = it.next();
					log.info("=========取得中间连接========="+field);
				    // 取得、过滤指定属性/标签内容
					tempLinkList = task.getController().getHtmlParserWrapper().getCrawlLinkURIByFilterMap(tempHtml,fieldfilter.getFetchAreaTagMap().get(field),fieldfilter.getDeleteAreaTagMap().get(field),crawlLinkURI);
					if(!Lang.isEmpty(tempLinkList)){
						crawlLinkURI = tempLinkList.get(0);
						log.info("=========取得中间连接地址：========="+crawlLinkURI);
					}
				}
			}
			log.info("=========取得中间连接---结果地址：========="+crawlLinkURI);
			task.setCrawlURI(crawlLinkURI);
		}
	}
	
	/**
	 * 根据任务URL地址,取得HTML
	 * @param task 任务
	 */
	private String fetchHTML(Task task,CrawlURI crawlURI){
		log.info("=========下载网页,提取原始html=========");
		HttpHost target = null;
		HttpGet httpGet = null;
		HttpClientContext context = null;
		try {
			target = task.getController().getHostCache().getHttpHost(crawlURI);
			httpGet = HttpClientHelper.getHttpGet(crawlURI);
			context = HttpClientHelper.getHttpClientContext();
			String html = Http.get(crawlURI.getUrl()).getContent();//task.getController().getHttpClient().execute(target, httpGet, task.getController().getHandler(), context);
			//task.getContentBean().setOrginHtml(html);
			log.info("=========HTML内容=========");
			return html;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != httpGet){
				httpGet.abort();
			}
		}
		return "";
	}

}
