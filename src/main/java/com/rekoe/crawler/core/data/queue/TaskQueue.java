package com.rekoe.crawler.core.data.queue;
/**
 * 任务队列接口
 */
public interface TaskQueue<T> {
	/**
	 * 获得任务对象 队列
	 * @return
	 */
	Queue<T> getUnExecTask();
	/**
	 * 未执行的任务对象 出队列
	 * @return
	 */
	T unExecTaskDeQueue();
	/**
	 * 保证每个任务对象 只被执行一次
	 * @param t
	 */
	void addUnExecTask(T t);
	/**
	 * 判断是否为空
	 * @return
	 */
	boolean isEmpty();
	/**
	 * 未执行任务对象数量
	 * @return
	 */
	int getUnExecTaskNum();
	/**
	 * 添加到执行过的任务对象队列中
	 * @param t
	 */
	void addExecTask(T t);
	/**
	 * 删除执行过的任务对象
	 * @param t
	 */
	void removeExecTask(T t);
	/**
	 * 已执行任务对象数量
	 * @return
	 */
	int getExecTaskNum();
	/**
	 * 清空任务队列
	 */
	void clear();
}
