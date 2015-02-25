package com.rekoe.crawler.core.processor.extractor;


import org.nutz.log.Logs;

import com.rekoe.crawler.core.data.Task;

/**
 * 任务处理器接口-抽取HTML内容实现类
 * <li>抽取链 ： 当提取完成时 ， 抽取感兴趣的 HTML 或者 JavaScript等 </li>
 */
public class ExtractorHTMLProcessor extends Extractor{

	private static final org.nutz.log.Log log = Logs.get();
	public ExtractorHTMLProcessor() {
		super();
		setDefaultNextProcessor(new ExtractorPaginationProcessor());
	}

	@Override
	protected void extract(Task task) {
		processorHTML(task);
	}
	
	private void processorHTML(Task task){
		log.info("=========抽取指定区域内容=========");
		//取得指定区域内容
		String html = task.getController().getHtmlParserWrapper().getTargetContentHtml(task.getContentBean().getOrginHtml());
		html = extractorContentResource(task, html);
		html = filterContentLink(task, html);
		html = replaceWords(task, html);
		task.getContentBean().setTitle(replaceWords(task, task.getContentBean().getTitle()));
		task.getContentBean().setContent(html);
		extractBrief(task);
	}
	

	private void extractBrief(Task task){
    	//取得指定区域内容
		String html = task.getController().getHtmlParserWrapper().getContentBrief(task.getContentBean().getOrginHtml());
		html = replaceWords(task, html);
    	task.getContentBean().setBrief(html);
	}
	/**
	 * 过滤内容中的连接
	 * @param task 任务
	 * @param html 原始内容
	 * @return 过滤后的内容
	 */
	private String filterContentLink(Task task, String html) {
		//替换掉内容中的超链接
		if(task.getController().getCrawlScope().isReplaceHtmlLink()){
			html = task.getController().getHtmlParserWrapper().replaceHtmlLink(html);
		}
		return html;
	}

	/**
	 * 提取内容中的资源
	 * @param task 任务
	 * @param html 原始内容
	 * @return 提取后的内容
	 */
	private String extractorContentResource(Task task, String html) {
		//替换原图片地址为本地图片地址，并将原图片地址保存在任务中，以备下一步使用
		if(task.getController().getCrawlScope().isExtractContentRes()){
			String savePath = task.getController().getCrawlScope().getSavePath();
			html = task.getController().getHtmlParserWrapper().replaceHtmlResource(html,savePath,task.getContentBean().getResCrawlURIList(),task.getCrawlURI());
		}else{
			html = task.getController().getHtmlParserWrapper().getHtmlResource(html,task.getContentBean().getResCrawlURIList(),task.getCrawlURI());
		}
		return html;
	}
	

}
