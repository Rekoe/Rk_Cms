package com.rekoe.cms.web.freemarker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.Mvcs;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@IocBean(create = "init")
public class FreeMarkerConfigurer {

	Configuration configuration;

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	public void init()
	{
		String path = Mvcs.getServletContext().getRealPath("WEB-INF");
		Ioc ioc = Mvcs.getIoc();
		try {
			initFreeMarkerConfigurer(path,ioc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
	public void initFreeMarkerConfigurer(String path,Ioc ioc) throws IOException, TemplateException
	{
		FreeMarkerConfigurer freeMarkerConfigurer = ioc.get(FreeMarkerConfigurer.class);
		Configuration configuration = new Configuration();
		freeMarkerConfigurer.setConfiguration(configuration);
		loadSettings(configuration);
		LabelDirective label = ioc.get(LabelDirective.class);
		PermistionDirective cmsPerm = ioc.get(PermistionDirective.class);
		configuration.setDirectoryForTemplateLoading(new File(path));
		HtmlCutDirective html_cut = ioc.get(HtmlCutDirective.class);
		TextCutDirective text_cut = ioc.get(TextCutDirective.class);
		ProcessTimeDirective process_time = ioc.get(ProcessTimeDirective.class); 
		configuration.setSharedVariable("label", label);
		configuration.setSharedVariable("cms_perm", cmsPerm);
		configuration.setSharedVariable("html_cut", html_cut);
		configuration.setSharedVariable("text_cut", text_cut);
		configuration.setSharedVariable("process_time", process_time);
	}
	
	protected void loadSettings(Configuration config){		
		InputStream in = null;
        try {        	
        	in=new BufferedInputStream(new FileInputStream(Files.findFile("freemarker.properties")));         	
            if (in != null) {
                Properties p = new Properties();
                p.load(in);
                config.setSettings(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
        	 e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch(IOException io) {
                	io.printStackTrace();
                }
            }
        }
	}
}
