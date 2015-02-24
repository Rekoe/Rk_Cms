package org.javacoo.crawler.core.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javacoo.crawler.core.processor.extractor.ExtractorHTMLProcessor;
import org.javacoo.crawler.core.processor.fetch.FetchDNSProcessor;
import org.javacoo.crawler.core.processor.post.PostProcessor;
import org.javacoo.crawler.core.processor.prepare.PrepareProcessor;
import org.javacoo.crawler.core.processor.writer.WriterProcessor;




/**
 * 处理器链集合
 * @author javacoo
 * @since 2011-11-09
 */
public class ProcessorChainList {
	/**预取链：主要是做一些准备工作，例如，对处理进行延迟和重新处理，否决随后的操作*/
	public static final String PRE_FETCH_PROCESSORS = "pre-fetch-processors";
	/**提取链：主要是下载网页，进行 DNS 转换，填写请求和响应表单*/
	public static final String FETCH_PROCESSORS = "fetch-processors";
	/**抽取链 ： 当提取完成时 ， 抽取感兴趣的 HTML 和 JavaScript 等 */
	public static final String EXTRACT_PROCESSORS = "extract-processors";
	/**写链：存储抓取结果，可以在这一步直接做全文索引*/
	public static final String WRITE_PROCESSORS = "write-processors";
	/**提交链：做和此 URL 相关操作的最后处理*/
	public static final String POST_PROCESSORS = "post-processors";
	
	private List<ProcessorChain> chainList = new ArrayList<ProcessorChain>();
	private Map<String, ProcessorChain> chainMap = new HashMap<String,ProcessorChain>();
	
	public ProcessorChainList() {
		super();
		addProcessorMap(PRE_FETCH_PROCESSORS, new PrepareProcessor());
		addProcessorMap(FETCH_PROCESSORS, new FetchDNSProcessor());
		addProcessorMap(EXTRACT_PROCESSORS, new ExtractorHTMLProcessor());
		addProcessorMap(WRITE_PROCESSORS, new WriterProcessor());
		addProcessorMap(POST_PROCESSORS, new PostProcessor());
	}
	
	public void addProcessorMap(String name, Processor processor) {
		ProcessorChain processorChain = new ProcessorChain(processor);
		ProcessorChain previousChain = getLastChain();
		if (previousChain != null) {
			previousChain.setNextProcessorChain(processorChain);
		}
		chainList.add(processorChain);
		chainMap.put(name, processorChain);
	}

	public ProcessorChain getLastChain() {
		if (size() == 0) {
			return null;
		} else {
			return chainList.get(size() - 1);
		}
	}
	
	public int size() {
		return chainList.size();
	}

	public ProcessorChain getFirstChain() {
		return chainList.get(0);
	}

}
