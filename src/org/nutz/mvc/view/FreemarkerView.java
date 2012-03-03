package org.nutz.mvc.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.Ioc;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;

import com.rekoe.cms.StartSetup;
import com.rekoe.cms.web.freemarker.FreeMarkerConfigurer;

import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;


public class FreemarkerView extends AbstractPathView{
	private static final String ATTR_APPLICATION_MODEL = ".freemarker.Application";
    private static final String ATTR_JSP_TAGLIBS_MODEL = ".freemarker.JspTaglibs";
    private static final String ATTR_REQUEST_MODEL = ".freemarker.Request";
    private static final String ATTR_REQUEST_PARAMETERS_MODEL = ".freemarker.RequestParameters";    
    private static final String KEY_APPLICATION = "Application";
    private static final String KEY_REQUEST_MODEL = "Request";
    private static final String KEY_SESSION_MODEL = "Session";    
    private static final String KEY_REQUEST_PARAMETER_MODEL = "Parameters";
    private static final String KEY_EXCEPTION = "exception";
    private static final String OBJ = "obj";	
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";
    private static final String SESSION = "session";
    private static final String APPLICATION = "application";
    private static final String KEY_JSP_TAGLIBS = "JspTaglibs";
    public static final String PATH_BASE = "base";
	
	public FreemarkerView(String path){
		super(path);
	}
	
	public void render(HttpServletRequest request, HttpServletResponse response, Object value) throws Throwable {
		String $temp = evalPath(request, value);
		String path = getPath($temp,request);
		ServletContext sc=request.getSession().getServletContext();		
		Ioc ioc = Mvcs.getIoc();
		Configuration cfg = ioc.get(FreeMarkerConfigurer.class).getConfiguration();
		//添加数据模型
		Map<String,Object> root = new HashMap<String,Object>();		
		root.put(OBJ, value);
		root.put(REQUEST, request);
		root.put(RESPONSE, response);
		HttpSession session = request.getSession();
		root.put(SESSION, session);
		root.put(PATH_BASE, getWebRealPath(request));
		root.put(APPLICATION, sc);
		root.put("props", System.getProperties());
		root.put("appBase","admin");
		Map<String, String> msgs = Mvcs.getMessages(request);
		root.put("mvcs", msgs);
		Enumeration<?> reqs=request.getAttributeNames();
		while(reqs.hasMoreElements()){
			String strKey=(String)reqs.nextElement();
			root.put(strKey, request.getAttribute(strKey));
		}
		//让freemarker支持jsp 标签
		//jspTaglibs(sc,request,response,root,cfg.getObjectWrapper());
		//模版路径
		try {
			Template template = cfg.getTemplate(path);
			response.setContentType("text/html; charset="+template.getEncoding());		
			template.process(root, response.getWriter());
		}catch (TemplateException e) {
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void jspTaglibs(ServletContext servletContext,HttpServletRequest request,HttpServletResponse response, Map<String, Object> model,ObjectWrapper wrapper){
		synchronized (servletContext) {
            ServletContextHashModel servletContextModel = (ServletContextHashModel) servletContext.getAttribute(ATTR_APPLICATION_MODEL);
            if (servletContextModel == null) {
                GenericServlet servlet = JspSupportServlet.jspSupportServlet;
                // TODO if the jsp support  servlet isn't load-on-startup then it won't exist
                // if it hasn't been accessed, and a JSP page is accessed
                if (servlet != null) {
                    servletContextModel = new ServletContextHashModel(servlet, wrapper);
                    servletContext.setAttribute(ATTR_APPLICATION_MODEL, servletContextModel);
                    TaglibFactory taglibs = new TaglibFactory(servletContext);
                    servletContext.setAttribute(ATTR_JSP_TAGLIBS_MODEL, taglibs);
                }
            }
            model.put(KEY_APPLICATION, servletContextModel);
            model.put(KEY_JSP_TAGLIBS, (TemplateModel) servletContext.getAttribute(ATTR_JSP_TAGLIBS_MODEL));
        }
		HttpSession session = request.getSession(false);
        if (session != null) {
            model.put(KEY_SESSION_MODEL, new HttpSessionHashModel(session, wrapper));
        }
		HttpRequestHashModel requestModel = (HttpRequestHashModel) request.getAttribute(ATTR_REQUEST_MODEL);
        if ((requestModel == null) || (requestModel.getRequest() != request)) {
            requestModel = new HttpRequestHashModel(request, response, wrapper);
            request.setAttribute(ATTR_REQUEST_MODEL, requestModel);
        }
        model.put(KEY_REQUEST_MODEL, requestModel);
        HttpRequestParametersHashModel reqParametersModel = (HttpRequestParametersHashModel) request.getAttribute(ATTR_REQUEST_PARAMETERS_MODEL);
        if (reqParametersModel == null || requestModel.getRequest() != request) {
            reqParametersModel = new HttpRequestParametersHashModel(request);
            request.setAttribute(ATTR_REQUEST_PARAMETERS_MODEL, reqParametersModel);
        }
        model.put(KEY_REQUEST_PARAMETER_MODEL, reqParametersModel);
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (exception == null) {
            exception = (Throwable) request.getAttribute("javax.servlet.error.JspException");
        }
        if (exception != null) {
            model.put(KEY_EXCEPTION, exception);
        }
	}
	
	private static String getWebRealPath(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append("http://");
		sb.append(request.getServerName());
		if (request.getServerPort() != 80) {
			sb.append(":");
			sb.append(request.getServerPort());
		}
		sb.append(request.getContextPath());
		sb.append("/");
		return sb.toString();
	}
	/**
	 * 子类可以覆盖这个方法，给出自己特殊的后缀
	 * 
	 * @return 后缀
	 */
	protected static String getExt() {
		return ".html";
	}
	
	private String getPath(String path, HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		// 空路径，采用默认规则
		if (Strings.isBlank(path)) {
			sb.append(Mvcs.getServletContext().getRealPath("WEB-INF"));
			sb.append((path.startsWith("/") ? "" : "/"));
			sb.append(Files.renameSuffix(path, getExt()));
		}
		// 绝对路径 : 以 '/' 开头的路径不增加 '/WEB-INF'
		else if (path.charAt(0) == '/') {
			String ext = getExt();
			sb.append(path);
			if (!path.toLowerCase().endsWith(ext))
				sb.append(ext);
		}
		// 包名形式的路径
		else {
			sb.append(path.replace('.', '/'));
			sb.append(getExt());
		}
		return sb.toString();
	}
}