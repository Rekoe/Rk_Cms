package org.javacoo.crawler.core.processor.writer;

import org.javacoo.crawler.core.data.Task;
import org.javacoo.crawler.core.processor.AbstractProcessor;

/**
 * 任务处理器接口-写链实现类
 * <li>写链：存储抓取结果，可以在这一步直接做全文索引</li>
 * @author javacoo
 * @since 2011-11-09
 */
public class WriterProcessor extends AbstractProcessor{

	public WriterProcessor() {
		super();
	}

	@Override
	protected void innerProcess(Task task) {
		log.info("=========存储抓取结果=========");
		//过滤指定区域内容
		String html = task.getController().getHtmlParserWrapper().filterTargetContentHtml(task.getContentBean().getContent());
		task.getContentBean().setContent(html);
		log.info(html);
		//TODO　ＳＡＶＥ　ＴＯ　ＤＢ　task.getController().getCrawlScope().getCrawlerPersistent().save(task);
	}

}
