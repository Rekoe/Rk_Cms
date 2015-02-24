package org.javacoo.crawler.core.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.javacoo.crawler.core.CrawlerController;
import org.javacoo.crawler.core.data.Task;
import org.javacoo.crawler.core.processor.Processor;


/**
 * 处理线程-从边界控制器获取任务并执行
 * <li>把任务交给任务处理链处理</li>
 * @author javacoo
 * @since 2011-11-10
 */
public class ProcessorCallableThread implements Callable<Task> {
	/**爬虫控制器*/
	private CrawlerController controller;
	private CountDownLatch latch;
	private Task task;

	public ProcessorCallableThread(CrawlerController controller,CountDownLatch latch,Task task) {
		this.controller = controller;
		this.latch = latch;
		this.task = task;
	}
	
	public Task call() throws Exception {
		try {
			processorTask(task);
			this.controller.getFrontier().finished(task);
			return task;
		}finally{
			System.out.println("======================采集内容子线程："+Thread.currentThread().getName() + "结束.");
			latch.countDown();
		}
	}
	
	/**
	 * 处理任务
	 * @param currentTask
	 */
	private void processorTask(Task currentTask) {
		currentTask.setNextProcessorChain(this.controller.getFirstProcessorChain());
		//依次执行处理链
		while (currentTask.getNextProcessorChain() != null) {
			currentTask.setNextProcessor(currentTask.getNextProcessorChain().getFirstProcessor());
			currentTask.setNextProcessorChain(currentTask.getNextProcessorChain().getNextProcessorChain());
			//依次执行每个处理器
			while (currentTask.getNextProcessor() != null) {
				Processor currentProcessor = currentTask.getNextProcessor();
				currentProcessor.process(currentTask);
			}
		}
	}
	


}
