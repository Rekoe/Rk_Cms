package com.rekoe.cms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rekoe.cms.dao.BasicDao;
import com.rekoe.cms.model.NavModel;

public class BaseAction {
	private static final Logger log = LoggerFactory.getLogger(BaseAction.class);
	@Inject
	protected BasicDao basicDao;
	
	public int getPage(Integer page)
	{
		return page==null?1:page.intValue();
	}
	public void setBasicDao(BasicDao basicDao) {
		this.basicDao = basicDao;
	}
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}
	public static void render(HttpServletResponse response, String contentType,String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public static List<NavModel> getListForSelect(List<NavModel> topList) {
		List<NavModel> list = new ArrayList<NavModel>();
		for (NavModel nm : topList) {
			addChildToList(list, nm);
		}
		return list;
	}
	/**
	 * 递归将子栏目加入列表。条件：有内容的栏目。
	 * @param list  栏目容器
	 * @param channel  待添加的栏目，且递归添加子栏目
	 * @param rights 有权限的栏目，为null不控制权限。
	 */
	private static void addChildToList(List<NavModel> list, NavModel navModel) {
		list.add(navModel);
		List<NavModel> child = navModel.getChildren();
		if(child != null)
		{
			for (NavModel c : child) {
				addChildToList(list, c);
			}
		}
	}
}
