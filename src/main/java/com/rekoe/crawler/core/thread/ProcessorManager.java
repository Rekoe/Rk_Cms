package com.rekoe.crawler.core.thread;


import java.util.concurrent.CountDownLatch;

import org.nutz.log.Logs;

import com.rekoe.crawler.core.CrawlerController;
import com.rekoe.crawler.core.constants.Constants;
/**
 * 线程池-开启指定数目的线程执行爬行任务
 */
public class ProcessorManager implements Runnable{
	private static final org.nutz.log.Log log = Logs.get();
	/** 爬虫控制器 */
	private CrawlerController controller;
	/** 线程池服务类 */
	private ThreadPoolService threadPoolService = new ThreadPoolService(Constants.THREAD_NUM);
	
	public ProcessorManager(CrawlerController c) {
		super();
		this.controller = c;
	}
	
	public void run() {
		long tStart = System.currentTimeMillis();
		log.info("主线程："+Thread.currentThread().getName() + "开始...");
		log.info("开启："+Constants.THREAD_NUM+ "个线程执行采集任务");
		try {
			CountDownLatch latch = new CountDownLatch(Constants.THREAD_NUM);
			//开启指定数目线程执行采集内容
			for(int i=0;i<Constants.THREAD_NUM;i++){
				threadPoolService.execute(new ProcessorRunnableThread(controller,latch));
			}
			latch.await();
			threadPoolService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			this.controller.shutdown();
			long tEnd = System.currentTimeMillis();
			log.info("主线程："+Thread.currentThread().getName() + "结束...");
			log.info("主线程："+Thread.currentThread().getName() + "总共用时:" + (tEnd - tStart) + "ms");
		}
	}
	/**
	 * 取得当前爬虫控制器
	 * @return 当前爬虫控制器
	 */
	public CrawlerController getController() {
		return controller;
	}
	/**
	 * 立即停止执行所有任务
	 */
	public void shutdownNow(){
		threadPoolService.shutdownNow();
	}
	/**
	 * 取得当前爬虫线程池服务类
	 * @return 当前爬线程池服务类
	 */
	public ThreadPoolService getThreadPoolService() {
		return threadPoolService;
	}

	public void setThreadPoolService(ThreadPoolService threadPoolService) {
		this.threadPoolService = threadPoolService;
	} 
	
}
