package org.javacoo.crawler.core.data;

import org.javacoo.crawler.core.CrawlerController;
import com.rekoe.crawler.core.data.uri.CrawlLinkURI;
import org.javacoo.crawler.core.processor.Processor;
import org.javacoo.crawler.core.processor.ProcessorChain;

/**
 * 任务
 * 
 * @author javacoo
 * @since 2011-11-09
 */
public class Task {
	/** 当前任务总数:用于采集监控 */
	private Integer currTaskTotalNum;
	/** 所属计划编号:用于采集监控 */
	private Integer planNum;
	/** 任务编号:用于采集监控 */
	private Integer taskNum;
	/** 是否完成 */
	private boolean finished;
	/** 任务URI对象 */
	private CrawlLinkURI crawlURI = new CrawlLinkURI();
	/** 采集内容值对象 */
	private transient ContentBean contentBean = new ContentBean();
	/** 下一个处理器 */
	private transient Processor nextProcessor;
	/** 下一个处理器链 */
	private transient ProcessorChain nextProcessorChain;
	/** 爬虫控制器 */
	private CrawlerController controller;

	public Processor getNextProcessor() {
		return nextProcessor;
	}

	public void setNextProcessor(Processor nextProcessor) {
		this.nextProcessor = nextProcessor;
	}

	public CrawlLinkURI getCrawlURI() {
		return crawlURI;
	}

	public void setCrawlURI(CrawlLinkURI crawlURI) {
		this.crawlURI = crawlURI;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public ProcessorChain getNextProcessorChain() {
		return nextProcessorChain;
	}

	public void setNextProcessorChain(ProcessorChain nextProcessorChain) {
		this.nextProcessorChain = nextProcessorChain;
	}

	public CrawlerController getController() {
		return controller;
	}

	public void setController(CrawlerController controller) {
		this.controller = controller;
	}

	public ContentBean getContentBean() {
		return contentBean;
	}

	public void setContentBean(ContentBean contentBean) {
		this.contentBean = contentBean;
	}

	public Integer getPlanNum() {
		return planNum;
	}

	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

	public Integer getCurrTaskTotalNum() {
		return currTaskTotalNum;
	}

	public void setCurrTaskTotalNum(Integer currTaskTotalNum) {
		this.currTaskTotalNum = currTaskTotalNum;
	}

	/**
	 * 完成任务,并清理
	 */
	public void finished() {
		setFinished(true);
		contentBean.clear();
		setNextProcessor(null);
		setNextProcessorChain(null);
		setController(null);
		this.crawlURI.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentBean == null) ? 0 : contentBean.hashCode());
		result = prime * result + ((crawlURI == null) ? 0 : crawlURI.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (contentBean == null) {
			if (other.contentBean != null)
				return false;
		} else if (!contentBean.equals(other.contentBean))
			return false;
		if (crawlURI == null) {
			if (other.crawlURI != null)
				return false;
		} else if (!crawlURI.equals(other.crawlURI))
			return false;
		return true;
	}
}
