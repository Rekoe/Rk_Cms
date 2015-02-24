package org.javacoo.crawler.core.data.queue;

import java.util.HashSet;
import java.util.Set;

import org.javacoo.crawler.core.data.ContentBean;




/**
 * 内容队列
 * @author javacoo
 * @since 2011-11-08
 * @param <ContentBean>
 */
public class ContentQueue {
	/**已保存内容对象集合*/
	private Set<ContentBean> savedContent = new HashSet<ContentBean>();
	/**待保存内容对象集合*/
	private SimpleQueue<ContentBean> unSavedContent = new SimpleQueue<ContentBean>();

	/**
	 * 获得内容对象 队列
	 * @return
	 */
	public SimpleQueue<ContentBean> getUnSavedContent() {
		return unSavedContent;
	}
	/**
	 * 未保存的内容对象 出队列
	 * @return
	 */
	public ContentBean unSavedContentDeQueue() {
		return unSavedContent.deQueue();
	}
	/**
	 * 保证每个内容对象 只被保存一次
	 * @param url
	 */
	public void addUnSavedContent(ContentBean content) {
		if (null != content && !unSavedContent.contains(content) && !savedContent.contains(content)){
			unSavedContent.enQueue(content);
		}
	}
	/**
	 * 判断是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return unSavedContent.isEmpty();
	}
	/**
	 * 未保存内容对象数量
	 * @return
	 */
	public int getUnSavedContentNum(){
		return unSavedContent.getSize();
	}
	/**
	 * 添加到保存过的内容对象队列中
	 * @param urlMap
	 */
	public void addSavedContent(ContentBean content){
		savedContent.add(content);
	}
	/**
	 * 删除保存过的内容对象
	 * @param urlMap
	 */
	public void removeSavedContent(ContentBean content){
		savedContent.remove(content);
	}
	/**
	 * 已保存内容对象数量
	 * @return
	 */
	public int getSavedContentNum(){
		return savedContent.size();
	}
	
}
