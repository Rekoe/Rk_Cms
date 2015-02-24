package com.rekoe.domain;

import java.util.Date;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;

/**
 * 采集规则持久对象
 */
@Table("crawler_rule")
public class CrawlerRule {
	@Id
	@Column("rule_id")
	private Integer ruleId;

	@Readonly
	private List<Integer> ruleIdList;

	@Column("rule_name")
	@ColDefine(width = 200)
	private String ruleName;

	@Column("rule_type")
	@ColDefine(width = 5)
	@Default("false")
	private String ruleType;

	@Column("start_time")
	@ColDefine(type = ColType.TIMESTAMP)
	private Date startTime;

	@Column("end_time")
	@ColDefine(type = ColType.TIMESTAMP)
	private Date endTime;
	@Column
	@ColDefine(width = 10)
	private String status;

	@Column("total_item")
	private Integer totalItem;

	@Column("rule_base_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleBaseConfig;

	@Column("rule_content_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleContentConfig;

	@Column("rule_content_page_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleContentPageConfig;

	@Column("rule_comment_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleCommentConfig;

	@Column("rule_fields_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleFieldsConfig;

	@Column("rule_database_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleDataBaseConfig;

	@Column("rule_ftp_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleFtpConfig;

	@Column("rule_imagesetting_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleImageSettingConfig;

	@Column("rule_local_config")
	@ColDefine(type = ColType.TEXT)
	private String ruleLocalConfig;

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public List<Integer> getRuleIdList() {
		return ruleIdList;
	}

	public void setRuleIdList(List<Integer> ruleIdList) {
		this.ruleIdList = ruleIdList;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	/**
	 * @return the ruleType
	 */
	public String getRuleType() {
		return ruleType;
	}

	/**
	 * @param ruleType
	 *            the ruleType to set
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	public String getRuleBaseConfig() {
		return ruleBaseConfig;
	}

	public void setRuleBaseConfig(String ruleBaseConfig) {
		this.ruleBaseConfig = ruleBaseConfig;
	}

	public String getRuleContentConfig() {
		return ruleContentConfig;
	}

	public void setRuleContentConfig(String ruleContentConfig) {
		this.ruleContentConfig = ruleContentConfig;
	}

	public String getRuleContentPageConfig() {
		return ruleContentPageConfig;
	}

	public void setRuleContentPageConfig(String ruleContentPageConfig) {
		this.ruleContentPageConfig = ruleContentPageConfig;
	}

	public String getRuleCommentConfig() {
		return ruleCommentConfig;
	}

	public void setRuleCommentConfig(String ruleCommentConfig) {
		this.ruleCommentConfig = ruleCommentConfig;
	}

	public String getRuleFieldsConfig() {
		return ruleFieldsConfig;
	}

	public void setRuleFieldsConfig(String ruleFieldsConfig) {
		this.ruleFieldsConfig = ruleFieldsConfig;
	}

	public String getRuleDataBaseConfig() {
		return ruleDataBaseConfig;
	}

	public void setRuleDataBaseConfig(String ruleDataBaseConfig) {
		this.ruleDataBaseConfig = ruleDataBaseConfig;
	}

	/**
	 * @return the ruleFtpConfig
	 */
	public String getRuleFtpConfig() {
		return ruleFtpConfig;
	}

	/**
	 * @param ruleFtpConfig
	 *            the ruleFtpConfig to set
	 */
	public void setRuleFtpConfig(String ruleFtpConfig) {
		this.ruleFtpConfig = ruleFtpConfig;
	}

	/**
	 * @return the ruleImageSettingConfig
	 */
	public String getRuleImageSettingConfig() {
		return ruleImageSettingConfig;
	}

	/**
	 * @param ruleImageSettingConfig
	 *            the ruleImageSettingConfig to set
	 */
	public void setRuleImageSettingConfig(String ruleImageSettingConfig) {
		this.ruleImageSettingConfig = ruleImageSettingConfig;
	}

	/**
	 * @return the ruleLocalConfig
	 */
	public String getRuleLocalConfig() {
		return ruleLocalConfig;
	}

	/**
	 * @param ruleLocalConfig
	 *            the ruleLocalConfig to set
	 */
	public void setRuleLocalConfig(String ruleLocalConfig) {
		this.ruleLocalConfig = ruleLocalConfig;
	}

}
