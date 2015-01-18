package com.rekoe.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.random.R;
import org.nutz.mvc.upload.TempFile;

import com.rekoe.module.admin.FileAct.FileInfo;
import com.rekoe.web.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * @author 科技㊣²º¹³
 * 2014年2月3日 下午4:48:45
 * http://www.rekoe.com
 * QQ:5382211
 */
@IocBean
public class FileService extends BaseService<Object> {

	@Inject
	private FreeMarkerConfigurer freeMarkerConfigurer;

	public String process(String template, Map<String, ?> model) {
		Configuration localConfiguration = freeMarkerConfigurer.getConfiguration();
		return process(template, model, localConfiguration);
	}

	public String process(String template, Map<String, ?> model, Configuration configuration) {
		if (template == null)
			return null;
		StringWriter localStringWriter = new StringWriter();
		try {
			new Template("template", new StringReader(template), configuration).process(model, localStringWriter);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return localStringWriter.toString();
	}

	public String upload(FileInfo.FileType fileType, TempFile tempFile, boolean async) throws IOException {
		String str1 = "/";
		Map<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put("uuid", R.UU16());
		String str2 = process(str1, localHashMap);
		String oldFileName = tempFile.getMeta().getFileLocalName();
		String str3 = str2 + UUID.randomUUID() + "." + oldFileName;
		final File uploadFile = tempFile.getFile();
		Files.move(uploadFile, new File("/"));
		return str3;
	}
}
