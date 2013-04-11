package com.icp.monitor.commons.statistics;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tree")
public class TreeNode {

	/**私有集群编号*/
	private int id;
	
	/**节点名称*/
	private String name;
	
	/**
	 * 节点是否打开
	 * */
	
	private String open="false";
	
	/**是否为父节点*/
	
	private String isParent;
	

	private String icon="../assets/images/icon_default.png";
	
	private List<TreeNode> children;
	
	/**
	 * 查询关键字
	 * */
	private String key;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	
	
}
