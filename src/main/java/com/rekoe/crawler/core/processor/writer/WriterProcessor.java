package com.rekoe.crawler.core.processor.writer;

import org.nutz.log.Logs;

import com.rekoe.crawler.core.data.Task;
import com.rekoe.crawler.core.processor.AbstractProcessor;

/**
 * 任务处理器接口-写链实现类
 * <li>写链：存储抓取结果，可以在这一步直接做全文索引</li>
 */
public class WriterProcessor extends AbstractProcessor{

	private static final org.nutz.log.Log log = Logs.get();
	public WriterProcessor() {
		super();
	}

	@Override
	protected void innerProcess(Task task) {
		log.info("=========存储抓取结果=========");
		//过滤指定区域内容
		String title = task.getContentBean().getTitle();
		String html = task.getController().getHtmlParserWrapper().filterTargetContentHtml(task.getContentBean().getContent());
		task.getContentBean().setContent(html);
		log.info(title);
	}

}
