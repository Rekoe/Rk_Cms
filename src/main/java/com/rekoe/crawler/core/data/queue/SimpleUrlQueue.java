package com.rekoe.crawler.core.data.queue;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang3.StringUtils;

/**
 * URL队列
 */
public class SimpleUrlQueue {
	/**已访问URL集合*/
	private Set<String> visitedUrl = new CopyOnWriteArraySet<String>();
	/**待访问URL集合*/
	private Queue<String> unVisitedUrl = new BlockingQueue<String>();

	/**
	 * 获得 URL 队列
	 * @return
	 */
	public Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}
	/**
	 * 未访问的 URL 出队列
	 * @return
	 */
	public String unVisitedUrlDeQueue() {
		return unVisitedUrl.deQueue();
	}
	/**
	 * 保证每个 URL 只被访问一次
	 * @param url
	 */
	public void addUnVisitedUrl(String url) {
		if (!StringUtils.isEmpty(url) && !unVisitedUrl.contains(url) && !visitedUrl.contains(url)){
			unVisitedUrl.enQueue(url);
		}
	}
	/**
	 * 判断是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return unVisitedUrl.isEmpty();
	}
	/**
	 * 未访问URL数量
	 * @return
	 */
	public int getUnVisitedUrlNum(){
		return unVisitedUrl.getSize();
	}
	/**
	 * 添加到访问过的URL队列中
	 * @param urlMap
	 */
	public void addVisitedUrl(String url){
		visitedUrl.add(url);
	}
	/**
	 * 删除访问过的URL
	 * @param urlMap
	 */
	public void removeVisitedUrl(String url){
		visitedUrl.remove(url);
	}
	/**
	 * 已访问URL数量
	 * @return
	 */
	public int getVisitedUrlNum(){
		return visitedUrl.size();
	}
	/**
	 * 清空URL队列
	 */
	public void clear(){
		unVisitedUrl.clear();
		visitedUrl.clear();
	}
}
