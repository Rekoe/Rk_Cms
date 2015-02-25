package com.rekoe.crawler.core.data.queue;

import org.nutz.log.Logs;

import com.rekoe.crawler.core.data.SimpleBloomFilter;
import com.rekoe.crawler.core.data.Task;

/**
 * Bloom任务队列
 */
public class BloomTaskQueue implements TaskQueue<Task> {
	private static final org.nutz.log.Log log = Logs.get();
	/** 已执行任务对象集合 */
	private SimpleBloomFilter<Task> execTask = new SimpleBloomFilter<Task>();
	/** 待执行任务对象集合 */
	private Queue<Task> unExecTask = new BlockingQueue<Task>();

	/**
	 * 获得任务对象 队列
	 * 
	 * @return
	 */
	public Queue<Task> getUnExecTask() {
		return unExecTask;
	}

	/**
	 * 未执行的任务对象 出队列
	 * 
	 * @return
	 */
	public Task unExecTaskDeQueue() {
		return unExecTask.deQueue();
	}

	/**
	 * 保证每个任务对象 只被执行一次
	 * 
	 * @param url
	 */
	public void addUnExecTask(Task task) {
		if (null != task && !unExecTask.contains(task) && !execTask.contains(task)) {
			unExecTask.enQueue(task);
			log.info("=========添加url到任务队列=========" + task + ",队列大小：" + getUnExecTaskNum());
		}
	}

	/**
	 * 判断是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return unExecTask.isEmpty();
	}

	/**
	 * 未执行任务对象数量
	 * 
	 * @return
	 */
	public int getUnExecTaskNum() {
		return unExecTask.getSize();
	}

	/**
	 * 添加到执行过的任务对象队列中
	 * 
	 * @param urlMap
	 */
	public void addExecTask(Task task) {
		execTask.add(task);
	}

	/**
	 * 删除执行过的任务对象
	 * 
	 * @param urlMap
	 */
	public void removeExecTask(Task task) {

	}

	/**
	 * 已执行任务对象数量
	 * 
	 * @return
	 */
	public int getExecTaskNum() {
		return execTask.getSize();
	}

	/**
	 * 清空任务队列
	 */
	public void clear() {
		execTask = null;
		unExecTask.clear();
	}

}
