package org.javacoo.crawler.core.processor.fetch;


import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.javacoo.crawler.core.data.Task;
import org.javacoo.crawler.core.processor.AbstractProcessor;
import org.javacoo.crawler.core.util.HttpClientHelper;


/**
 * 任务处理器接口-提取DNS实现类
 * <li>提取链：主要是下载网页，进行 DNS 转换，填写请求和响应表单</li>
 * @author javacoo
 * @since 2011-11-09
 */
public class FetchDNSProcessor extends AbstractProcessor{

	public FetchDNSProcessor() {
		super();
	}

	@Override
	protected void innerProcess(Task task) {
		fetchHTML(task);
	}
	/**
	 * 根据任务URL地址,取得HTML
	 * @param task 任务
	 */
	private void fetchHTML(Task task){
		log.info("=========下载网页,提取原始html=========");
		HttpHost target = null;
		HttpGet httpGet = null;
		HttpClientContext context = null;
		try {
			target = task.getController().getHostCache().getHttpHost(task.getCrawlURI());
			httpGet = HttpClientHelper.getHttpGet(task.getCrawlURI());
			context = HttpClientHelper.getHttpClientContext();
			String html = task.getController().getHttpClient().execute(target, httpGet, task.getController().getHandler(), context);
			task.getContentBean().setOrginHtml(html);
			log.info("=========HTML内容=========");
			log.info(html);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != httpGet){
				httpGet.abort();
			}
		}
	}

}
