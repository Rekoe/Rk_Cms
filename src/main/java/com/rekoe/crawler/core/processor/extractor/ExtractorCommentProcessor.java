package com.rekoe.crawler.core.processor.extractor;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.lang.Lang;
import org.nutz.log.Logs;

import com.rekoe.crawler.core.constants.Constants;
import com.rekoe.crawler.core.data.Task;
import com.rekoe.crawler.core.data.queue.UrlQueue;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;

/**
 * 任务处理器接口-抽取评论实现类 <li>抽取链 ： 提取评论内容</li>
 */
public class ExtractorCommentProcessor extends Extractor {
	
	private static final org.nutz.log.Log log = Logs.get();
	
	/** 存放SimpleUrlQueue的ThreadLocal对象 */
	private static ThreadLocal<UrlQueue> commentUrlQueueThreadLocal = new ThreadLocal<UrlQueue>();

	public ExtractorCommentProcessor() {
		super();
		setDefaultNextProcessor(new ExtractorContentResourceProcessor());
	}

	@Override
	protected void extract(Task task) {
		initProcessor(task);
		processorHTML(task);
	}

	/**
	 * 抽取第一页内容，得到内容分页
	 */
	private void initProcessor(Task task) {
		log.info("=========抽取第一页内容,得到内容分页连接,加入到内容分页队列中=========");
		String orginHtml = task.getContentBean().getOrginHtml();
		// 如果评论内容列表与内容页分离
		if (task.getController().getCrawlScope().isCommentListIsAlone()) {
			CrawlLinkURI indexUrl = task.getController().getHtmlParserWrapper().getCommentIndexUrl(orginHtml, task.getCrawlURI());
			if (null != indexUrl) {
				// 未访问
				getUrlQueue().addUnExecTask(indexUrl);
			}
		} else {
			// 第一页已经访问
			getUrlQueue().addExecTask(task.getCrawlURI());
			populateCommentList(task, task.getContentBean().getOrginHtml());
		}

	}

	/**
	 * 抽取原始HTML中评论链接,加入到评论分页队列中
	 * 
	 * @param task
	 *            当前任务对象
	 * @param orginHtml
	 *            原始HTML内容
	 */
	private void populateCommentList(Task task, String orginHtml) {
		List<CrawlLinkURI> urlList = task.getController().getHtmlParserWrapper().getCommentUrlList(orginHtml, task.getCrawlURI());
		if (!Lang.isEmpty(urlList)) {
			for (CrawlLinkURI url : urlList) {
				getUrlQueue().addUnExecTask(url);
			}
		}
	}

	/**
	 * 补全URL
	 * 
	 * @param task
	 *            任务
	 * @param CrawlLinkURI
	 *            Url对象
	 * @return 处理后URL
	 */
	private String repairUrl(Task task, CrawlLinkURI url) {
		String pUrl = url.getUrl();
		if (!Constants.PATH_TYPE_0.equals(url.getPathType())) {
			// 若pUrl是相对于根路径路径
			if (Constants.PATH_TYPE_1.equals(url.getPathType())) {
				pUrl = task.getController().getCrawlScope().getRepairPageUrl() + pUrl;
			} else {
				// 没有则，取得当前URL
				String tempPageUrl = "";
				if (task.getCrawlURI().getUrl().contains("http://")) {
					tempPageUrl = task.getCrawlURI().getUrl().trim();
				} else {
					tempPageUrl = task.getController().getCrawlScope().getRepairPageUrl() + task.getCrawlURI().getUrl().trim();
				}
				pUrl = tempPageUrl.substring(0, tempPageUrl.lastIndexOf("/") + 1) + pUrl;
			}
		}
		return pUrl;
	}

	/**
	 * 处理任务
	 * 
	 * @param task
	 */
	private void processorHTML(Task task) {
		log.info("=========处理评论任务开始=========");
		try {
			String tempOrginHtml = "";
			CrawlLinkURI crawlURI = null;
			while (!getUrlQueue().isEmpty() && !Thread.interrupted()) {
				crawlURI = getUrlQueue().unExecTaskDeQueue();
				tempOrginHtml = fetchHTML(task, crawlURI);
				if (StringUtils.isNotBlank(tempOrginHtml)) {
					// 提取评论分页中的链接,并加入到队列中
					populateCommentList(task, tempOrginHtml);
					saveCommentContent(task, tempOrginHtml);
					getUrlQueue().addExecTask(crawlURI);
				}
			}
		} finally {
			log.info("=========处理评论任务结束=========");
			getUrlQueue().clear();
			commentUrlQueueThreadLocal.set(null);
		}
	}

	/**
	 * 保存评论内容
	 * 
	 * @param task
	 *            任务
	 * @param orginHtml
	 *            原始HTML
	 */
	private void saveCommentContent(Task task, String orginHtml) {
		// 取得指定区域内评论列表内容区域内容
		orginHtml = task.getController().getHtmlParserWrapper().getCommentListArea(orginHtml);
		// 取得指定评论列表内容区域评论集合
		List<String> commentList = task.getController().getHtmlParserWrapper().getCommentList(orginHtml);
		if (!Lang.isEmpty(commentList)) {
			for (String comment : commentList) {
				comment = filterContentLink(task, comment);
				// 过滤指定区域内容
				comment = task.getController().getHtmlParserWrapper().filterCommentHtml(comment);
				// 得到纯文本
				comment = task.getController().getHtmlParserWrapper().getPlainText(comment);
				// 关键字替换
				comment = replaceWords(task, comment);
				task.getContentBean().getCommentList().add(comment);
			}

		}
	}

	/**
	 * 过滤内容中的连接
	 * 
	 * @param task
	 *            任务
	 * @param html
	 *            原始内容
	 * @return 过滤后的内容
	 */
	private String filterContentLink(Task task, String html) {
		// 替换掉内容中的超链接
		if (task.getController().getCrawlScope().isReplaceHtmlLink()) {
			html = task.getController().getHtmlParserWrapper().replaceHtmlLink(html);
		}
		return html;
	}

	/**
	 * 根据任务URL地址,取得HTML
	 * 
	 * @param task
	 *            任务
	 */
	private String fetchHTML(Task task, CrawlLinkURI uri) {
		log.info("=========下载评论页内容,提取原始html=========");
		return task.getController().getHandler().handleResponse(task.getController().getHostCache().getHttpHostUrl(uri));
	}

	/**
	 * 取得当前主线程的SimpleUrlQueue对象
	 * 
	 * @return 当前主线程的SimpleUrlQueue对象
	 */
	private static UrlQueue getUrlQueue() {
		if (commentUrlQueueThreadLocal.get() == null) {
			UrlQueue pageUrlQueue = new UrlQueue();
			commentUrlQueueThreadLocal.set(pageUrlQueue);
			return pageUrlQueue;
		} else {
			return commentUrlQueueThreadLocal.get();
		}
	}

}
