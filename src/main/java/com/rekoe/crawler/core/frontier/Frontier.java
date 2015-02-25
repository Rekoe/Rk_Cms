package com.rekoe.crawler.core.frontier;

import com.rekoe.crawler.core.CrawlerController;
import com.rekoe.crawler.core.data.Task;



/**
 * 边界控制器  接口
 * 主要是加载爬行种子链接并根据加载的种子链接初始化任务队列，以备线程控制器（ProcessorThreadPool）开启的任务执行线程（ProcessorThread）使用
 */
public interface Frontier {
	/**
	 * 初始化
	 * <li>加载爬行种子链接</li>
	 * <li>初始化任务队列</li>
	 * @param c 控制器对象
	 */
	void initialize(CrawlerController c);
	/**
	 * 取得下一个任务
	 * @return 下一个任务
	 */
	Task next();
	/**
	 * 是否为空
	 * @return
	 */
	boolean isEmpty();
	/**
	 * 完成任务
	 * @param task 任务
	 */
	void finished(Task task);
	/**
	 * 取得任务总数
	 * @return 任务总数
	 */
   int getTaskSize();
   /**
	 * 取得未执行任务总数
	 * @return 未执行任务总数
	 */
   int getUnExecTaskNum();
   /**
	 * 取得已执行任务总数
	 * @return 已执行任务总数
	 */
   int getExecTaskNum();
   /**
	 * 销毁对象
	 */
	void destory();

}
