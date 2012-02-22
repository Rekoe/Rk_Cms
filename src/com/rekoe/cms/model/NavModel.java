package com.rekoe.cms.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

/**
 * 网站导航模型
 * 
 * @author Administrator
 * 
 */
@Table("t_navModel")
public class NavModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7992631788015602548L;
	/*
	 * 数据库ID
	 */
	@Id
	private int id;
	/*
	 * 导航名称
	 */
	@Column
	private String navName;
	/*
	 * 父级导航，不存在则为空
	 */
	@One(target=NavModel.class,field="pid")
	private NavModel parent;
	/*
	 * 记录外键关系
	 */
	@Column
	private int pid;
	
	/*
	 * 列表显示风格
	 */
	@One(target=Templete.class,field="tid")
	private Templete template;
	/*
	 * 记录外键关系
	 */
	@Column
	private int tid; 
	
	/*
	 * 排序号码
	 */
	@Column
	private long sortNumber;
	/*
	 * 访问地址
	 */
	@Column
	private String url;
	/*
	 * 所有该导航的子导航
	 */
	@Many(target=NavModel.class,field="pid")
	private List<NavModel> children;
	
	/*
	 *设置是否为首页 
	 */
	@Column
	private boolean indexNav;
	/*
	 * 是否显示导航
	 * true 不显示
	 */
	@Column
	private boolean showNav;
	/**
	 * 获得深度
	 * 
	 * @return 第一层为0，第二层为1，以此类推。
	 */
	public int getDeep() {
		int deep = 0;
		NavModel parent = getParent();
		while (parent != null) {
			deep++;
			parent = parent.getParent();
		}
		return deep;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNavName() {
		return navName;
	}

	public void setNavName(String navName) {
		this.navName = navName;
	}

	public NavModel getParent() {
		return parent;
	}

	public void setParent(NavModel parent) {
		this.parent = parent;
	}

	public Templete getTemplate() {
		return template;
	}

	public void setTemplate(Templete template) {
		this.template = template;
	}

	public long getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(long sortNumber) {
		this.sortNumber = sortNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<NavModel> getChildren() {
		return children;
	}

	public void setChildren(List<NavModel> children) {
		
		if(children!=null&&children.size()>0){
			//把List进行排序处理
			Collections.sort(children, new Comparator<NavModel>() {

				@Override
				public int compare(NavModel o1, NavModel o2) {
					
					if(o1.getSortNumber()==o2.getSortNumber()){
						return 0;
					}
					if(o1.getSortNumber()>o2.getSortNumber()){
						return -1;
					}
					return 1 ;
				}
			
			});
		}
		this.children = children;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public boolean isIndexNav() {
		return indexNav;
	}

	public void setIndexNav(boolean indexNav) {
		this.indexNav = indexNav;
	}

	public boolean isShowNav() {
		return showNav;
	}

	public void setShowNav(boolean showNav) {
		this.showNav = showNav;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + id;
		result = prime * result + (indexNav ? 1231 : 1237);
		result = prime * result + ((navName == null) ? 0 : navName.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + pid;
		result = prime * result + (showNav ? 1231 : 1237);
		result = prime * result + (int) (sortNumber ^ (sortNumber >>> 32));
		result = prime * result
				+ ((template == null) ? 0 : template.hashCode());
		result = prime * result + tid;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		NavModel other = (NavModel) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (id != other.id)
			return false;
		if (indexNav != other.indexNav)
			return false;
		if (navName == null) {
			if (other.navName != null)
				return false;
		} else if (!navName.equals(other.navName))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (pid != other.pid)
			return false;
		if (showNav != other.showNav)
			return false;
		if (sortNumber != other.sortNumber)
			return false;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		if (tid != other.tid)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
