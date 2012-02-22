package com.rekoe.cms.web.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@IocBean
public class LabelDirective implements TemplateDirectiveModel {
	@Inject
	private Dao dao;
	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		
		for (TemplateModel templateModel : loopVars) {
			templateModel.toString();
		}
		// 真正开始处理输出内容
		Writer out = env.getOut();
		if (body != null) {
			out.write(getContent(params));
			body.render(out);
		}
	}

	private String getContent(Map<?,?> params) {
		//Integer id = Integer.parseInt(params.get("id").toString());
		//if (null != id) {
			//Label label = this.dao.fetch(Label.class, id);
			//return label.getLabelContent();
		//}
		return "abcedef";
	}
}
