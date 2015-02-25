package com.rekoe.crawler.core.processor;

import com.rekoe.crawler.core.data.Task;


/**
 * 任务处理器接口
 */
public interface Processor {
	/**
	 * 处理任务
	 * @param task 任务
	 */
	void process(Task task);
}
