package com.rekoe.crawler.core.processor.fetch;

import org.nutz.log.Logs;

import com.rekoe.crawler.core.data.Task;
import com.rekoe.crawler.core.processor.AbstractProcessor;

/**
 * 任务处理器接口-提取DNS实现类 <li>提取链：主要是下载网页，进行 DNS 转换，填写请求和响应表单</li>
 */
public class FetchDNSProcessor extends AbstractProcessor {
	private static final org.nutz.log.Log log = Logs.get();

	public FetchDNSProcessor() {
		super();
	}

	@Override
	protected void innerProcess(Task task) {
		fetchHTML(task);
	}

	/**
	 * 根据任务URL地址,取得HTML
	 * 
	 * @param task
	 *            任务
	 */
	private void fetchHTML(Task task) {
		log.info("=========下载网页,提取原始html=========");
		String html = task.getController().getHandler().handleResponse(task.getController().getHostCache().getHttpHostUrl(task.getCrawlURI()));
		task.getContentBean().setOrginHtml(html);
		log.info("=========HTML内容=========");
	}
}
