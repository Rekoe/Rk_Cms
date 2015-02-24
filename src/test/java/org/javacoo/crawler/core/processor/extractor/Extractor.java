package org.javacoo.crawler.core.processor.extractor;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.javacoo.crawler.core.data.Task;
import org.javacoo.crawler.core.processor.AbstractProcessor;

/**
 * 任务处理器接口-抽取内容抽象实现类
 * <li>抽取链 ： 当提取完成时 ， 抽取感兴趣的 HTML 和 JavaScript ， 通常那里有新的要抓抽取的 URL</li>
 * @author javacoo
 * @since 2011-11-09
 */
public abstract class Extractor extends AbstractProcessor {
	public Extractor() {
		super();
	}
	protected void innerProcess(Task task) {
		 extract(task);
	}
	/**
	 * 抽取内容有具体子类实现
	 * @param task 任务
	 */
	protected abstract void extract(Task task);
	
	/**
	 * 替换指定关键字
	 * @param task 任务
	 * @param html 原始内容
	 * @return 替换后的内容
	 */
	protected String replaceWords(Task task, String html) {
		if(StringUtils.isNotBlank(html) && null != task.getController().getCrawlScope().getReplaceWordsMap()){
			Map<String, String> replaceMap = task.getController().getCrawlScope().getReplaceWordsMap();
			for(Iterator<String> it = replaceMap.keySet().iterator();it.hasNext();){
				String key = it.next();
				html = html.replaceAll(key, replaceMap.get(key));
			}
		}
		return html;
	}
}
