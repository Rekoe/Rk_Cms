package org.javacoo.crawler.core.thread;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javacoo.crawler.core.CrawlerController;
import org.javacoo.crawler.core.data.Task;
import org.javacoo.crawler.core.processor.Processor;

import com.rekoe.crawler.core.constants.Constants;
/**
 * 处理线程-从边界控制器获取任务并执行
 * <li>把任务交给任务处理链处理</li>
 * @author javacoo
 * @since 2011-11-10
 */
public class ProcessorRunnableThread implements Runnable {
	private static Log log =  LogFactory.getLog(ProcessorRunnableThread.class);
	/**爬虫控制器*/
	private CrawlerController controller;
	private CountDownLatch latch;

	public ProcessorRunnableThread(CrawlerController controller,CountDownLatch latch) {
		this.controller = controller;
		this.latch = latch;
	}
	public void run() {
		try {
			Task currentTask = null;
			while(!this.controller.getFrontier().isEmpty() && !Thread.interrupted() && !this.controller.getProcessorManager().getThreadPoolService().isShutdown()) {
				if(checkContinue()){
					currentTask = this.controller.getFrontier().next();
					processorTask(currentTask);
					this.controller.getFrontier().finished(currentTask);
				}
				log.info("======================采集内容子线程："+Thread.currentThread().getName() + "休眠："+this.controller.getCrawlScope().getSleepTime()+"毫秒");
				Thread.sleep(this.controller.getCrawlScope().getSleepTime());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			log.info("======================采集内容子线程："+Thread.currentThread().getName() + "结束.");
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
		while (currentTask.getNextProcessorChain() != null  && !Thread.interrupted()) {
			currentTask.setNextProcessor(currentTask.getNextProcessorChain().getFirstProcessor());
			currentTask.setNextProcessorChain(currentTask.getNextProcessorChain().getNextProcessorChain());
			//依次执行每个处理器
			while (currentTask.getNextProcessor() != null  && !Thread.interrupted()) {
				Processor currentProcessor = currentTask.getNextProcessor();
				currentProcessor.process(currentTask);
			}
		}
	}
	/**
	 * 检查爬虫状态是否为运行中
	 * @return
	 */
	private boolean checkContinue(){
		return Constants.CRAWL_STATE_RUNNING.equals(this.controller.getState());
	}

}
