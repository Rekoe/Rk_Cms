var ioc = {
  configuration: {
    type: "freemarker.template.Configuration"
  },
  freeMarkerConfigurer: {
    type: "com.rekoe.web.freemarker.FreeMarkerConfigurer",
    events: {
      create: 'init'
    },
    args: [{
      refer: "configuration"
    }, {
      app: '$servlet'
    }, "WEB-INF", ".ftl", {
      refer: "freemarkerDirectiveFactory"
    }]
  },
  permission: {
    type: "com.rekoe.web.freemarker.PermissionDirective"
  },
  process: {
    type: "com.rekoe.web.freemarker.ProcessTimeDirective"
  },
  htmlCut: {
    type: "com.rekoe.web.freemarker.HtmlCutDirective"
  },
  articleCategoryParentList: {
    type: "com.rekoe.web.freemarker.ArticleCategoryParentListDirective",
    args: [{
      refer: "articleCategoryService"
    }]
  },
  articleCategoryTree: {
    type: "com.rekoe.web.freemarker.ArticleCategoryTreeDirective",
    args: [{
      refer: "articleCategoryService"
    }]
  },
  articleCategoryRootList: {
    type: "com.rekoe.web.freemarker.ArticleCategoryRootListDirective",
    args: [{
      refer: "articleCategoryService"
    }]
  },
  articleList: {
    type: "com.rekoe.web.freemarker.ArticleListDirective",
    args: [{
      refer: "articleService"
    }]
  },
  pagination: {
    type: "com.rekoe.web.freemarker.PaginationDirective"
  },
  timeFormat: {
    type: "com.rekoe.web.freemarker.TimeFormatDirective"
  },
  permissionDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["cms_perm", {
      refer: "permission"
    }]
  },
  processDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["process_time", {
      refer: "process"
    }]
  },
  paginationDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["pagination", {
      refer: "pagination"
    }]
  },
  htmlCutDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["htmlCut", {
      refer: "htmlCut"
    }]
  },
  articleCategoryParentListDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["article_category_parent_list", {
      refer: "articleCategoryParentList"
    }]
  },
  articleCategoryTreeDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["article_category_tree", {
      refer: "articleCategoryTree"
    }]
  },
  articleCategoryRootListDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["article_category_root_list", {
      refer: "articleCategoryRootList"
    }]
  },
  articleListDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["article_list", {
      refer: "articleList"
    }]
  },
  timeFormatDirective: {
    type: "com.rekoe.web.freemarker.FreemarkerDirective",
    args: ["timeFormat", {
      refer: "timeFormat"
    }]
  },
  freemarkerDirectiveFactory: {
    type: "com.rekoe.web.freemarker.FreemarkerDirectiveFactory",
    events: {
      create: 'init'
    },
    fields: {
      freemarker: 'com/rekoe/web/freemarker/freemarker.properties',
    },
    args: [{
      refer: "permissionDirective"
    }, {
      refer: "processDirective"
    }, {
      refer: "paginationDirective"
    }, {
      refer: "htmlCutDirective"
    }, {
      refer: "articleCategoryRootListDirective"
    }, {
      refer: "articleCategoryParentListDirective"
    }, {
      refer: "articleListDirective"
    }, {
      refer: "timeFormatDirective"
    }, {
      refer: "articleCategoryTreeDirective"
    }]
  }
};