package com.rekoe.web.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nutz.lang.Lang;

import com.rekoe.utils.DirectiveUtils;
import com.rekoe.utils.StrUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * HTML文本提取并截断
 */
public class HtmlCutDirective implements TemplateDirectiveModel {
	public static final String PARAM_S = "s";
	public static final String PARAM_LEN = "len";
	public static final String PARAM_APPEND = "append";

	@SuppressWarnings({"unchecked","rawtypes"})
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String s = DirectiveUtils.getString(PARAM_S, params);
		Integer len = DirectiveUtils.getInt(PARAM_LEN, params);
		String append = DirectiveUtils.getString(PARAM_APPEND, params);
		if (StringUtils.isNotBlank(s)) {
			Writer out = env.getOut();
			if (!Lang.isEmpty(len)) {
				out.append(StrUtils.htmlCut(s, len, append));
			} else {
				out.append(s);
			}
		}
	}
}
