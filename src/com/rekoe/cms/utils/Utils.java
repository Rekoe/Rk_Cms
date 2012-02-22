package com.rekoe.cms.utils;

public class Utils {

	//Module
	public final static String[] SYSTEM_MODULE = {"个人资料","用户 ","登录"};
	/**
	 * 
	 * @param module 1 修改个人资料 2 用户权限 3 登录
	 */
	public String getSystemModuleDesc(int module)
	{
		return SYSTEM_MODULE[module-1];
	}
}
