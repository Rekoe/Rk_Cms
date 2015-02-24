package org.javacoo.crawler.core.data.queue;
/**
 * 队列
 * @author javacoo
 * @since 2012-02-08
 * @param <T>
 */
public interface Queue<T> {
	/**
	 * 入队列
	 * @param t
	 */
	public void enQueue(T t);
	/**
	 * 出队列
	 * @return t
	 */
	public T deQueue();
	/**
	 * 判断队列是否为空
	 * @return
	 */
	public boolean isEmpty();
	/**
	 * 判断队列是否含有t
	 * @param t
	 * @return
	 */
	public boolean contains(T t);
	/**
	 * 取得队列大小
	 * @return
	 */
	public int getSize();
	/**
	 * 清空队列
	 */
	public void clear();

}
