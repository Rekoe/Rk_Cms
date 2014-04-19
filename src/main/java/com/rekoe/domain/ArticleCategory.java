package com.rekoe.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Lang;

@Table("article_category")
public class ArticleCategory {

	@Name
	@Prev(els = { @EL("uuid()") })
	// @Prev(els = { @EL("$me.uuid()") })
	private String id;

	@Column("create_date")
	@ColDefine(type = ColType.TIMESTAMP)
	private Date createDate;

	@Column("modify_date")
	@ColDefine(type = ColType.TIMESTAMP)
	private Date modifyDate;

	@Column
	private int grade;
	@Column
	@ColDefine(type = ColType.VARCHAR, width = 225)
	private String name;
	@Column("order_id")
	private int order;

	@Column("parent_id")
	@Default("")
	private String parentId;

	@Column("tree_path")
	private String treePath;

	@One(target = ArticleCategory.class, field = "parentId")
	private ArticleCategory parent;// 上级分类

	@Many(target = ArticleCategory.class, field = "id")
	private Set<ArticleCategory> children;// 下级分类

	@Many(target = Article.class, field = "articleCategoryId")
	private List<Article> articleSet;// 文章

	/*
	 * public String uuid() { return R.UU16(); }
	 */
	public ArticleCategory getParent() {
		return parent;
	}

	public void setParent(ArticleCategory parent) {
		this.parent = parent;
	}

	public Set<ArticleCategory> getChildren() {
		return children;
	}

	public void setChildren(Set<ArticleCategory> children) {
		this.children = children;
	}

	public List<Article> getArticleSet() {
		return articleSet;
	}

	public void setArticleSet(List<Article> articleSet) {
		this.articleSet = articleSet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleCategory other = (ArticleCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Readonly
	public String getUrl() {
		return "/art/list/" + this.getId() + "." + "htm";
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	@Readonly
	public List<String> getTreePaths() {
		String[] arrayOfString = StringUtils.split(getTreePath(), ",");
		if (!Lang.isEmptyArray(arrayOfString))
			return Lang.array2list(arrayOfString);
		return Collections.emptyList();
	}
}
