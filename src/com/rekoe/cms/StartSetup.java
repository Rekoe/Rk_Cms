package com.rekoe.cms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.nutz.ioc.Ioc;
import org.nutz.lang.Files;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.rekoe.cms.model.WebConfig;
import com.rekoe.cms.web.freemarker.FreeMarkerConfigurer;
import com.rekoe.cms.web.freemarker.LabelDirective;
import com.rekoe.cms.web.freemarker.PermistionDirective;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class StartSetup implements Setup {

	@Override
	public void destroy(NutConfig nConfig) {

	}
	public void initFreeMarkerConfigurer(String path,Ioc ioc) throws IOException, TemplateException
	{
		FreeMarkerConfigurer freeMarkerConfigurer = ioc.get(FreeMarkerConfigurer.class);
		Configuration configuration = new Configuration();
		freeMarkerConfigurer.setConfiguration(configuration);
		loadSettings(configuration);
		LabelDirective label = ioc.get(LabelDirective.class);
		PermistionDirective cmsPerm = ioc.get(PermistionDirective.class);
		configuration.setSharedVariable("label", label);
		configuration.setSharedVariable("cms_perm", cmsPerm);
		configuration.setDirectoryForTemplateLoading(new File(path));
	}
	public static WebConfig web =null; 
	
	public static WebConfig getWeb() {
		return web;
	}
	@Override
	public void init(NutConfig nConfig) {
		/*Ioc ioc = nConfig.getIoc();
		Dao dao = ioc.get(Dao.class);
		
		String path = nConfig.getServletContext().getRealPath("WEB-INF");
		Ioc ioc = nConfig.getIoc();
		try {
			initFreeMarkerConfigurer(path,ioc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		//NutMessageLoader msg = ioc.get(NutMessageLoader.class);
		
		 BasicDao basicDao = ioc.get(BasicDao.class);
		Dao dao = ioc.get(Dao.class);
		Role role = basicDao.findByCondition(Role.class, "id", "48", "permission");//dao.fetch(Role.class, Cnd.where("id", "=", 48));
		dao.fetchLinks(role, "permission");
		System.out.println(role.getPermission());
//		List<Role> roleList = Daos.queryWithLinks(dao, Role.class, null, null, "permission");
//		for(Role role:roleList)
//		{
//			List<Permission>list = role.getPermission();
//			for(Permission permission:list)
//			{
//				System.out.println(permission.getResource());
//			}
//		}
		
	*/}
//	
//	private <T> List<T> search(Class<T> c,Dao dao){
//		return dao.query(c, Cnd.wrap(""), null);
//	
//	}
	
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