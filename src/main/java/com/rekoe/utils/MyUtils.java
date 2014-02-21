package com.rekoe.utils;

import javax.servlet.ServletContext;

/**
 * 用于上传ioc : myUpload.js 
 */
public class MyUtils {
	private ServletContext sc;      
	public String getPath(String path) {         
		return sc.getRealPath(path);     
	} 
}

