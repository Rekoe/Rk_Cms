var ioc = {
	mapTags : {
		factory : "$freeMarkerConfigurer#addTags",
		args : [ {
			'cms_perm' : {
				refer : 'permission'
			},
			'process_time' : {
				refer : 'process'
			},
			'pagination' : {
				refer : 'pagination'
			},
			'htmlCut' : {
				refer : 'htmlCut'
			},
			'article_category_parent_list' : {
				refer : 'articleCategoryParentList'
			},
			'article_category_tree' : {
				refer : 'articleCategoryTree'
			},
			'article_category_root_list' : {
				refer : 'articleCategoryRootList'
			},
			'article_list' : {
				refer : 'articleList'
			},
			'timeFormat' : {
				refer : 'timeFormat'
			}
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
		args : [ {
			refer : "articleCategoryService"
		} ]
	},
	articleCategoryTree : {
		type : "com.rekoe.web.freemarker.ArticleCategoryTreeDirective",
		args : [ {
			refer : "articleCategoryService"
		} ]
	},
	articleCategoryRootList : {
		type : "com.rekoe.web.freemarker.ArticleCategoryRootListDirective",
		args : [ {
			refer : "articleCategoryService"
		} ]
	},
	articleList : {
		type : "com.rekoe.web.freemarker.ArticleListDirective",
		args : [ {
			refer : "articleService"
		} ]
	},
	pagination : {
		type : "com.rekoe.web.freemarker.PaginationDirective"
	},
	timeFormat : {
		type : "com.rekoe.web.freemarker.TimeFormatDirective"
	}
};