/**
 * 如石子一粒,仰高山之巍峨,但不自惭形秽.
 * 若小草一棵,慕白杨之伟岸,却不妄自菲薄.
 */
package com.rekoe.crawler.core.constants;

/**
 * 数据采集常量定义
 * <p>说明:</p>
 * <li></li>
 * @author DuanYong
 * @since 2013-6-17 下午2:33:42
 * @version 1.0
 */
public class GatherConstant {
    /**静态值*/
	public static final String EXTEND_FIELDS_VALUE_TYPE_STATIC = "static";
    /**静态值多值：逗号分隔*/
	public static final String EXTEND_FIELDS_VALUE_TYPE_STATIC_MULIT = "staticMulit";
	/**静态值KEY*/
	public static final String EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_KEY = "staticMapKey";
	/**静态值value*/
	public static final String EXTEND_FIELDS_VALUE_TYPE_STATIC_MAP_VALUE = "staticMapValue";
	/**动态态值*/
	public static final String EXTEND_FIELDS_VALUE_TYPE_DYNAMIC = "dynamic";
	/**动态态值KEY*/
	public static final String EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_KEY = "dynamicMapKey";
	/**动态态值value*/
	public static final String EXTEND_FIELDS_VALUE_TYPE_DYNAMIC_MAP_VALUE = "dynamicMapValue";
	
	public static final String DATA_BASE_SETTING_VALUES_KRY_PRIMARY_TABLE = "primaryTable";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT = "content";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_ID = "contentId";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_TITLE = "contentTitle";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_BRIEF = "contentBrief";
	public static final String DATA_BASE_SETTING_VALUES_KRY_VIEW_DATE = "viewDate";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_TITLE_IMG = "contentTitleImg";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE = "contentResource";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE_NAME = "contentResourceName";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_RESOURCE_DESC = "contentResourceDesc";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_PAGE = "contentPage";
	public static final String DATA_BASE_SETTING_VALUES_KRY_CONTENT_COMMENT = "contentComment";
	
	
	public static final String EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_SIMPLE = "0";
	
	public static final String EXTEND_FIELDS_VALUE_TYPE_STATIC_KEY_MAP = "1";

	public static final String RULE_NAME_EXTENSION = ".cow";


	

	public static final String BIGINT = "BIGINT";
	public static final String BINARY = "BINARY";
	public static final String BIT = "BIT";
	public static final String CHAR = "CHAR";
	public static final String DATE = "DATE";
	public static final String DECIMAL = "DECIMAL";
	public static final String DOUBLE = "DOUBLE";
	public static final String FLOAT = "FLOAT";
	public static final String INTEGER = "INTEGER";
	public static final String INT = "INT";
	public static final String LONGVARBINARY = "LONGVARBINARY";
	public static final String LONGVARCHAR = "LONGVARCHAR";
	public static final String NULL = "NULL";
	public static final String NUMERIC = "NUMERIC";
	public static final String OTHER = "OTHER";
	public static final String REAL = "REAL";
	public static final String SMALLINT = "SMALLINT";
	public static final String TIME = "TIME";
	public static final String TIMESTAMP = "TIMESTAMP";
	public static final String DATETIME = "DATETIME";
	public static final String TINYINT = "TINYINT";
	public static final String VARBINARY = "VARBINARY";
	public static final String VARCHAR = "VARCHAR";

	public static final String TASK_TYPE_1 = "1";// 采集任务

	public static final String TASK_TYPE_2 = "2";// FTP上传任务

	public static final String TASK_TYPE_3 = "3";// 数据入库任务

	public static final String TASK_TYPE_4 = "4";// 图片处理任务

	/**
	 * ---------------------采集规则SQLMAP------------------------------------------
	 * ---
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_RULE = "insertCrawlerRule";// 插入采集规则SQLMAP

	public static final String SQLMAP_ID_UPDATE_CRAWLER_RULE = "updateCrawlerRule";// 修改采集规则SQLMAP

	public static final String SQLMAP_ID_LIST_CRAWLER_RULE = "getCrawlerRuleList";// 采集规则列表SQLMAP

	public static final String SQLMAP_ID_DELETE_CRAWLER_RULE = "deleteCrawlerRule";// 删除采集规则SQLMAP

	public static final String SQLMAP_ID_GET_CRAWLER_RULE = "getCrawlerRuleById";// 取得采集规则SQLMAP

	public static final String SQLMAP_ID_START_CRAWLER_RULE = "startCrawlerRule";// 开始执行采集规则SQLMAP

	public static final String SQLMAP_ID_STOP_CRAWLER_RULE = "stopCrawlerRule";// 停止执行采集规则SQLMAP

	/**
	 * ---------------------采集任务SQLMAP------------------------------------------
	 * ---
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_TASK = "insertCrawlerTask";// 插入采集任务SQLMAP

	public static final String SQLMAP_ID_LIST_CRAWLER_TASK = "getCrawlerTaskList";// 采集任务列表SQLMAP

	public static final String SQLMAP_ID_UPDATE_STATUS_CRAWLER_TASK = "updateCrawlerTaskStatus";// 更新任务状态SQLMAP

	public static final String SQLMAP_ID_UPDATE_TOTAL_CRAWLER_TASK = "updateCrawlerTaskTotal";// 更新任务总数SQLMAP

	public static final String SQLMAP_ID_UPDATE_COMPLETE_CRAWLER_TASK = "updateCrawlerTaskComplete";// 更新任务完成数量SQLMAP

	public static final String SQLMAP_ID_GET_BY_RULEID_CRAWLER_TASK = "getCrawlerTaskByRuleId";// 取得任务SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_TASKID_CRAWLER_TASK = "deleteCrawlerTask";// 根据任务ID删除任务

	public static final String SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_TASK = "deleteCrawlerTaskByRuldId";// 根据规则ID删除任务

	/**
	 * ---------------------采集内容SQLMAP------------------------------------------
	 * ---
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_CONTENT = "insertCrawlerContent";// 插入采集内容SQLMAP

	public static final String SQLMAP_ID_UPDATE_CRAWLER_CONTENT = "updateCrawlerContent";// 更新采集内容SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT = "deleteCrawlerContentByRuleId";// 根据规则ID删除采集内容SQLMAP

	public static final String SQLMAP_ID_DELETE_CRAWLER_CONTENT = "deleteCrawlerContentById";// 根据ID删除采集内容SQLMAP

	public static final String SQLMAP_ID_GET_CRAWLER_CONTENT = "getCrawlerContentById";// 查询采集内容SQLMAP

	public static final String SQLMAP_ID_GET_LIST_CRAWLER_CONTENT = "getCrawlerContentList";// 查询采集内容列表SQLMAP

	/**
	 * ---------------------采集内容评论SQLMAP----------------------------------------
	 * -----
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_CONTENT_COMMENT = "insertCrawlerContentComment";// 插入采集内容评论SQLMAP

	public static final String SQLMAP_ID_UPDATE_CRAWLER_CONTENT_COMMENT = "updateCrawlerContentComment";// 更新采集内容评论SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_COMMENT = "deleteCrawlerContentCommentByRuleId";// 根据规则ID删除采集内评论容SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_COMMENT = "deleteCrawlerContentCommentByContentId";// 根据内容ID删除采集内评论容SQLMAP

	public static final String SQLMAP_ID_DELETE_CRAWLER_CONTENT_COMMENT = "deleteCrawlerContentCommentById";// 根据ID删除采集内容评论SQLMAP

	public static final String SQLMAP_ID_GET_CRAWLER_CONTENT_COMMENT = "getCrawlerContentCommentById";// 查询采集内容评论SQLMAP

	public static final String SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_COMMENT = "getCrawlerContentCommentList";// 查询采集内容评论列表SQLMAP

	/**
	 * ---------------------采集内容分页SQLMAP----------------------------------------
	 * -----
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_CONTENT_PAGINATION = "insertCrawlerContentPagination";// 插入采集内容分页SQLMAP

	public static final String SQLMAP_ID_UPDATE_CRAWLER_CONTENT_PAGINATION = "updateCrawlerContentPagination";// 更新采集内容分页SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_PAGINATION = "deleteCrawlerContentPaginationByRuleId";// 根据规则ID删除采集内容分页SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_PAGINATION = "deleteCrawlerContentPaginationByContentId";// 根据内容ID删除采集内容分页SQLMAP

	public static final String SQLMAP_ID_DELETE_CRAWLER_CONTENT_PAGINATION = "deleteCrawlerContentPaginationById";// 根据ID删除采集内容分页SQLMAP

	public static final String SQLMAP_ID_GET_CRAWLER_CONTENT_PAGINATION = "getCrawlerContentPaginationById";// 查询采集内容分页SQLMAP

	public static final String SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_PAGINATION = "getCrawlerContentPaginationList";// 查询采集内容分页列表SQLMAP

	/**
	 * ---------------------采集内容资源SQLMAP----------------------------------------
	 * -----
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_CONTENT_RESOURCE = "insertCrawlerContentResource";// 插入采集内容资源SQLMAP

	public static final String SQLMAP_ID_UPDATE_CRAWLER_CONTENT_RESOURCE = "updateCrawlerContentResource";// 更新采集内容资源SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_RESOURCE = "deleteCrawlerContentResourceByRuleId";// 根据规则ID删除采集内容资源SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_RESOURCE = "deleteCrawlerContentResourceByContentId";// 根据内容ID删除采集内容分资源SQLMAP

	public static final String SQLMAP_ID_DELETE_CRAWLER_CONTENT_RESOURCE = "deleteCrawlerContentResourceById";// 根据ID删除采集内容资源SQLMAP

	public static final String SQLMAP_ID_GET_CRAWLER_CONTENT_RESOURCE = "getCrawlerContentResourceById";// 查询采集内容资源SQLMAP

	public static final String SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_RESOURCE = "getCrawlerContentResourceList";// 查询采集内容资源列表SQLMAP

	/**
	 * ---------------------采集历史SQLMAP------------------------------------------
	 * ---
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_CONTENT_HISTORY = "insertCrawlerHistory";// 插入采集内容分页SQLMAP

	public static final String SQLMAP_ID_UPDATE_CRAWLER_CONTENT_HISTORY = "updateCrawlerHistory";// 更新采集内容分页SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_CONTENT_HISTORY = "deleteCrawlerHistoryByRuleId";// 根据规则ID删除采集内容分页SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_CONTENT_HISTORY = "deleteCrawlerHistoryByContentId";// 根据内容ID删除采集内容分页SQLMAP

	public static final String SQLMAP_ID_DELETE_CRAWLER_CONTENT_HISTORY = "deleteCrawlerHistoryById";// 根据ID删除采集内容分页SQLMAP

	public static final String SQLMAP_ID_GET_CRAWLER_CONTENT_HISTORY = "getCrawlerHistoryById";// 查询采集内容分页SQLMAP

	public static final String SQLMAP_ID_GET_LIST_CRAWLER_CONTENT_HISTORY = "getCrawlerHistoryList";// 查询采集内容分页列表SQLMAP

	
	/**
	 * ---------------------扩展字段SQLMAP------------------------------------------
	 * ---
	 */

	public static final String SQLMAP_ID_INSERT_CRAWLER_EXTEND_FIELD = "insertCrawlerExtendField";// 插入扩展字段SQLMAP

	public static final String SQLMAP_ID_UPDATE_CRAWLER_EXTEND_FIELD = "updateCrawlerExtendField";// 更新扩展字段SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_RULEID_CRAWLER_EXTEND_FIELD = "deleteCrawlerExtendFieldByRuleId";// 根据规则ID删除扩展字段SQLMAP

	public static final String SQLMAP_ID_DELETE_BY_CONTENTID_CRAWLER_EXTEND_FIELD = "deleteCrawlerExtendFieldByContentId";// 根据内容ID删除扩展字段SQLMAP

	public static final String SQLMAP_ID_DELETE_CRAWLER_EXTEND_FIELD = "deleteCrawlerExtendFieldById";// 根据ID删除扩展字段SQLMAP

	public static final String SQLMAP_ID_GET_CRAWLER_EXTEND_FIELD = "getCrawlerExtendFieldById";// 查询扩展字段SQLMAP

	public static final String SQLMAP_ID_GET_LIST_CRAWLER_EXTEND_FIELD = "getCrawlerExtendFieldList";// 查询扩展字段列表SQLMAP
	
	private GatherConstant() {
	}
}
