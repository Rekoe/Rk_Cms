package com.rekoe.crawler.core.processor.extractor;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.json.Json;
import org.nutz.log.Logs;

import com.rekoe.crawler.core.data.Task;

/**
 * 任务处理器接口-抽取FIELD内容实现类
 * <li>抽取链 ： 提取内容到指定字段 </li>
 */
public class ExtractorFieldProcessor extends Extractor{

	private static final org.nutz.log.Log log = Logs.get();
	public ExtractorFieldProcessor() {
		super();
	}

	@Override
	protected void extract(Task task) {
		processorHTML(task);
	}
	
	private void processorHTML(Task task){
		log.info("=========提取内容到指定字段=========");
		String extendStr = task.getController().getCrawlScope().getExtendField();
		if(StringUtils.isNotBlank(extendStr)){
			Map<String,String> extendMap = Json.fromJsonAsMap(String.class, extendStr);
			task.getContentBean().getFieldValueMap().putAll(extendMap);
		}
		task.getContentBean().getFieldValueMap().putAll(task.getController().getHtmlParserWrapper().getFieldValues(task.getContentBean().getOrginHtml()));
		
	}
	
}
