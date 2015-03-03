package com.rekoe.web.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.subject.Subject;
import org.nutz.lang.Lang;

import com.rekoe.utils.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PermissionShiroFreemarker implements TemplateDirectiveModel {
	private PermissionResolver permissionResolver = new WildcardPermissionResolver();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Subject subject = SecurityUtils.getSubject();
		Writer out = env.getOut();
		String wildcardString = DirectiveUtils.getString("perm", params);
		Collection<Permission> permList = resolvePermissions(wildcardString);
		for (Permission permission : permList) {
			if (subject.isPermitted(permission)) {
				out.write("Ok");
				break;
			}
		}
	}

	public Collection<Permission> resolvePermissions(String... stringPerms) {
		Collection<Permission> perms = Collections.emptySet();
		PermissionResolver resolver = permissionResolver;
		if (resolver != null && !Lang.isEmptyArray(stringPerms)) {
			perms = new LinkedHashSet<Permission>(stringPerms.length);
			for (String strPermission : stringPerms) {
				Permission permission = resolver.resolvePermission(strPermission);
				perms.add(permission);
			}
		}
		return perms;
	}
}
