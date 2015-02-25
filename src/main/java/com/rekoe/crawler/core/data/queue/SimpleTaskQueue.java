package com.rekoe.crawler.core.data.queue;

import java.util.HashSet;
import java.util.Set;

import com.rekoe.crawler.core.data.Task;


/**
 * 任务队列
 */
public class SimpleTaskQueue implements TaskQueue<Task>{
	/**已执行任务对象集合*/
	private Set<Task> execTask = new HashSet<Task>();
	/**待执行任务对象集合*/
	private Queue<Task> unExecTask = new BlockingQueue<Task>();

	/**
	 * 获得任务对象 队列
	 * @return
	 */
	public Queue<Task> getUnExecTask() {
		return unExecTask;
	}
	/**
	 * 未执行的任务对象 出队列
	 * @return
	 */
	public Task unExecTaskDeQueue() {
		return unExecTask.deQueue();
	}
	/**
	 * 保证每个任务对象 只被执行一次
	 * @param url
	 */
	public void addUnExecTask(Task task) {
		if (null != task && !unExecTask.contains(task) && !execTask.contains(task)){
			unExecTask.enQueue(task);
		}
	}
	/**
	 * 判断是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return unExecTask.isEmpty();
	}
	/**
	 * 未执行任务对象数量
	 * @return
	 */
	public int getUnExecTaskNum(){
		return unExecTask.getSize();
	}
	/**
	 * 添加到执行过的任务对象队列中
	 * @param urlMap
	 */
	public void addExecTask(Task task){
		execTask.add(task);
	}
	/**
	 * 删除执行过的任务对象
	 * @param urlMap
	 */
	public void removeExecTask(Task task){
		execTask.remove(task);
	}
	/**
	 * 已执行任务对象数量
	 * @return
	 */
	public int getExecTaskNum(){
		return execTask.size();
	}
	
	/**
	 * 清空任务队列
	 */
	public void clear(){
		execTask.clear();
		unExecTask.clear();
	}
	
}
