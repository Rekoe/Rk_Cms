var ioc = {
	configuration : {
		type : "freemarker.template.Configuration"
	},
	freeMarkerConfigurer : {
		type : "com.rekoe.web.freemarker.FreeMarkerConfigurer",
		events : {
			create : 'init'
		},
		args : [ {
			refer : "configuration"
		}, {
			app : '$servlet'
		}, "/WEB-INF", {
			refer : "freemarkerDirectiveFactory"
		} ]
	},
	permission : {
		type : "com.rekoe.web.freemarker.PermissionDirective"
	},
	process : {
		type : "com.rekoe.web.freemarker.ProcessTimeDirective"
	},
	htmlCut : {
		type : "com.rekoe.web.freemarker.HtmlCutDirective"
	},
	articleCategoryParentList : {
		type : "com.rekoe.web.freemarker.ArticleCategoryParentListDirective",
		args : [{
			refer : "articleCategoryService"
		} ]
	},
	articleCategoryRootList : {
		type : "com.rekoe.web.freemarker.ArticleCategoryRootListDirective",
		args : [{
			refer : "articleCategoryService"
		} ]
	},
	articleList : {
		type : "com.rekoe.web.freemarker.ArticleListDirective",
		args : [{
			refer : "articleService"
		} ]
	},
	pagination : {
		type : "com.rekoe.web.freemarker.PaginationDirective"
	},
	permissionDirective : {
		type : "com.rekoe.web.freemarker.FreemarkerDirective",
		args : [ "cms_perm", {
			refer : "permission"
		} ]
	},
	processDirective : {
		type : "com.rekoe.web.freemarker.FreemarkerDirective",
		args : [ "process_time", {
			refer : "process"
		} ]
	},
	paginationDirective : {
		type : "com.rekoe.web.freemarker.FreemarkerDirective",
		args : [ "pagination", {
			refer : "pagination"
		} ]
	},
	htmlCutDirective : {
		type : "com.rekoe.web.freemarker.FreemarkerDirective",
		args : [ "htmlCut", {
			refer : "htmlCut"
		} ]
	},
	articleCategoryParentListDirective : {
		type : "com.rekoe.web.freemarker.FreemarkerDirective",
		args : [ "article_category_parent_list", {
			refer : "articleCategoryParentList"
		} ]
	},
	articleCategoryRootListDirective : {
		type : "com.rekoe.web.freemarker.FreemarkerDirective",
		args : [ "article_category_root_list", {
			refer : "articleCategoryRootList"
		} ]
	},
	articleListDirective : {
		type : "com.rekoe.web.freemarker.FreemarkerDirective",
		args : [ "article_list", {
			refer : "articleList"
		} ]
	},
	freemarkerDirectiveFactory : {
		type : "com.rekoe.web.freemarker.FreemarkerDirectiveFactory",
		events : {
			create : 'init'
		},
		fields : {
			freemarker : 'freemarker.properties',
		},
		args : [ {
			refer : "permissionDirective"
		}, {
			refer : "processDirective"
		}, {
			refer : "paginationDirective"
		}, {
			refer : "htmlCutDirective"
		}, {
			refer : "articleCategoryRootListDirective"
		}, {
			refer : "articleCategoryParentListDirective"
		}, {
			refer : "articleListDirective"
		} ]
	}
};