package com.rekoe.crawler.core.processor.extractor;

import com.rekoe.crawler.core.data.Task;



/**
 * 任务处理器接口-抽取URL内容实现类
 */
public class ExtractorURIProcessor extends Extractor {
	static final String ABS_HTTP_URI_PATTERN = "^https?://[^\\s<>]*$";

	public ExtractorURIProcessor() {
		super();
	}
	@Override
	protected void extract(Task task) {
		
	}

}
