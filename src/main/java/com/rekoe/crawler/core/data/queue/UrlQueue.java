package com.rekoe.crawler.core.data.queue;

import java.util.HashSet;
import java.util.Set;

import com.rekoe.crawler.core.data.uri.CrawlLinkURI;

/**
 * URL队列
 */
public class UrlQueue implements TaskQueue<CrawlLinkURI>{
	/**URL集合*/
	private Set<CrawlLinkURI> allUrl = new HashSet<CrawlLinkURI>();
	/**已访问URL集合*/
	private Set<CrawlLinkURI> visitedUrl = new HashSet<CrawlLinkURI>();
	/**待访问URL集合*/
	private Queue<CrawlLinkURI> unVisitedUrl = new BlockingQueue<CrawlLinkURI>();

	/**
	 * 判断是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return unVisitedUrl.isEmpty();
	}
	/**
	 * 清空URL队列
	 */
	public void clear(){
		unVisitedUrl.clear();
		visitedUrl.clear();
	}
	public void addExecTask(CrawlLinkURI t) {
		visitedUrl.add(t);
	}
	public void addAllURI(CrawlLinkURI t){
		allUrl.add(t);
	}
	public void addUnExecTask(CrawlLinkURI t) {
		if (!allUrl.contains(t)){
			allUrl.add(t);
			unVisitedUrl.enQueue(t);
		}
	}
	public int getExecTaskNum() {
		return visitedUrl.size();
	}
	public Queue<CrawlLinkURI> getUnExecTask() {
		return unVisitedUrl;
	}
	public int getUnExecTaskNum() {
		return unVisitedUrl.getSize();
	}
	public void removeExecTask(CrawlLinkURI t) {
		visitedUrl.remove(t);
	}
	public CrawlLinkURI unExecTaskDeQueue() {
		return unVisitedUrl.deQueue();
	}
}
