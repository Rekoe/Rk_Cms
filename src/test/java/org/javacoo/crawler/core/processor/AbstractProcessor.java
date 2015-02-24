package org.javacoo.crawler.core.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javacoo.crawler.core.data.Task;


/**
 * 任务处理器接口抽象实现类
 * @author javacoo
 * @since 2011-11-09
 */
public abstract class AbstractProcessor implements Processor{

	protected static Log log =  LogFactory.getLog(AbstractProcessor.class);
	/**默认下一个处理器*/
	private Processor defaultNextProcessor = null;
	
	public void process(Task task) {
		task.setNextProcessor(getDefaultNextProcessor());
		innerProcess(task);
	}
	/**
	 * 具体处理任务细节由子类实现
	 */
	protected abstract void innerProcess(Task task);
	
	public Processor getDefaultNextProcessor() {
		return defaultNextProcessor;
	}

	public void setDefaultNextProcessor(Processor defaultNextProcessor) {
		this.defaultNextProcessor = defaultNextProcessor;
	}
	
	
	

}
