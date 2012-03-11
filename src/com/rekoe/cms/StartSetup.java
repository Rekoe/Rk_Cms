package com.rekoe.cms;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.nutz.lang.Files;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class StartSetup implements Setup {

	public static Properties p = new Properties();
	@Override
	public void destroy(NutConfig nConfig) {

	}
	@Override
	public void init(NutConfig nConfig) {
//		try {
//			NutDao dao = DynamicDao.getNutzDao("114.113.152.25");
//			List<UserInfo> list = dao.query(UserInfo.class, null);
//			System.out.println(list);
//		} catch (PropertyVetoException e1) {
//			e1.printStackTrace();
//		}
		InputStream in = null;
        try {        	
        	in=new BufferedInputStream(new FileInputStream(Files.findFile("msg/MessageResources.properties")));         	
            if (in != null) {
                p.load(in);
            }
        } catch (IOException e) {
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