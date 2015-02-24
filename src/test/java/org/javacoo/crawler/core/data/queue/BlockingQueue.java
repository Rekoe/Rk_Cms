package org.javacoo.crawler.core.data.queue;

import java.util.concurrent.LinkedBlockingQueue;
/**
 * 队列
 * 采用jdk的LinkedBlockingQueue实现
 * @author javacoo
 * @since 2012-02-08
 * @param <T>
 */
public class BlockingQueue<T> implements Queue<T>{
	private LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<T>();
	/**
	 * 入队列
	 * @param t
	 */
	public void enQueue(T t){
		try {
			queue.put(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 出队列
	 * @return t
	 */
	public T deQueue(){
		return queue.poll();
	}
	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	/**
	 * 判断队列是否含有t
	 * @param t
	 * @return
	 */
	public boolean contains(T t){
		return queue.contains(t);
	}
	/**
	 * 取得队列大小
	 * @return
	 */
	public int getSize(){
		return queue.size();
	}
	
	/**
	 * 清空队列
	 */
	public void clear(){
		queue.clear();
	}

}
