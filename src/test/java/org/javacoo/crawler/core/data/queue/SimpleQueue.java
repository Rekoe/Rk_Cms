package org.javacoo.crawler.core.data.queue;

import java.util.LinkedList;
/**
 * 队列
 * 先进后出、
 * @author javacoo
 * @since 2011-11-01
 * @param <T>
 */
public class SimpleQueue<T> implements Queue<T>{
	private LinkedList<T> queue = new LinkedList<T>();
	/**
	 * 入队列
	 * @param t
	 */
	public void enQueue(T t){
		queue.addFirst(t);
	}
	/**
	 * 出队列
	 * @return t
	 */
	public T deQueue(){
		return queue.removeFirst();
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
