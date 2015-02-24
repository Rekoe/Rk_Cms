package org.javacoo.crawler.core.processor.extractor;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.javacoo.crawler.core.data.Task;
import org.nutz.json.Json;

/**
 * 任务处理器接口-抽取FIELD内容实现类
 * <li>抽取链 ： 提取内容到指定字段 </li>
 * @author javacoo
 * @since 2011-12-03
 */
public class ExtractorFieldProcessor extends Extractor{

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
