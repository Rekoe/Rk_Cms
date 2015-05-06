package com.rekoe.module;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

@IocBean
@Filters
public class ShiroTestModule {

    /**
     * 验证流程:
     * 1. 访问 /shiro/test 会跳转到登陆页面
     * 2. 登陆后再访问/shiro/test, 跳转到首页
     * 3. 创建用户wendal, 普通用户,无admin:admin权限
     * 4. 用wendal账号登陆,访问 /shiro/test2 , 跳转到登陆页面
     * 5. 用admin账号登陆,访问 /shiro/test2, 显示"yes, you can"
     */
    
    
    @RequiresAuthentication
    @At("/shiro/test")
    @Ok(">>:/")
    public void test() {
    }
    
    @At("/user/login")
    @Ok("->:/admin/index")
    public void index(){}
    
    @At("/shiro/test2")
    @RequiresPermissions("admin:admin")
    @Ok("raw")
    public String test2(){
        return "yes, you can";
    }
}
