package com.icp.monitor.commons.otherbean;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_basic_artifact")
public class BasicArtifact implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "extension", nullable = false)
	private String extension;
	
	@Column(name = "serial", nullable = false)
	private String serial;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "repoUrl", columnDefinition = "TEXT")
	private String repoUrl;
	
	@Column(name = "creator_id", columnDefinition="INT(11) default 0")
	private int creatorId;
	
	@Column(name = "creator_name")
	private String creatorName;

	@Column(name = "tenant_id", columnDefinition="INT(11) default 0")
	private int tenantId;
	
	@Column(name = "tenant_name")
	private String tenantName;
	
	@Column(name = "create_date")
	private String createDate;
	
	/**
	 * 1为jar 2为app
	 */
	@Column(name = "category", columnDefinition="INT(2) default 0")
	private int category;
	
	@Column(name = "flow_status", columnDefinition="INT(2) default 0")
	private int flowStatus;
	
	/**
	 * @see DeployStatus.java
	 */
	@Column(name = "deploy_status", columnDefinition="INT(2) default 0")
	private int deployStatus;
	
	@Column(name = "cluster_id", columnDefinition="INT(11) default 0")
	private int clusterId;
	
	public enum DeployStatus {
		TESTABLE(0, "待测试"),
		//部署和卸载是异步操作
		//部署到测试环境
		TEST_START(20, "开始部署到测试环境"),
		TEST_DOWNLOADING(21, "开始下载资源到测试环境"),
		TEST_DOWNLOAD_ERROR(-21, "下载资源到测试环境失败"),
		TEST_RESOURCE_DEPLOYING(22, "开始同步资源到测试环境"),
		TEST_RESOURCE_DEPLOY_ERROT(-22, "同步资源到测试环境失败"),
		TEST_RESOURCE_STARTING(23, "开始启动资源（测试环境）"),
		TEST_RESOURCE_START_ERROR(-23, "启动资源失败（测试环境）"),
		TEST_DEPLOY_SUCCEED(29, "成功部署到测试环境"),
		
		APPLY_DEPLOY(7, "申请部署中"),
		
		DEPLOYABLE(2, "可部署"),
		//部署到运行环境
		DEPLOY_START(30, "开始部署到运行环境"),
		DEPLOY_DOWNLOADING(31, "开始下载资源到运行环境"),
		DEPLOY_DOWNLOAD_ERROT(-31, "下载资源到运行环境失败"),
		DEPLOY_RESOURCE_DEPLOYING(32, "开始同步资源到运行环境"),
		DEPLOY_RESOURCE_DEPLOY_ERROT(-32, "同步资源到运行环境失败"),
		DEPLOY_RESOURCE_STARTING(33, "开始启动资源（运行环境）"),
		DEPLOY_RESOURCE_START_ERROR(-33, "启动资源失败（运行环境）"),
		DEPLOY_SUCCEED(39, "成功部署到运行环境"),
		
		
		RUNNING(4, "运行中"),
		//卸载后状态恢复到2:可部署
		STOPPED(6, "停止"),
		DELETE(-1, "已删除");
		private int status;
		private String desc;
		private DeployStatus(int status, String desc) {
			this.status = status;
			this.desc = desc;
		}
		public int getStatus() {
			return status;
		}
		public String getDesc() {
			return desc;
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRepoUrl() {
		return repoUrl;
	}

	public void setRepoUrl(String repoUrl) {
		this.repoUrl = repoUrl;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}

	public int getDeployStatus() {
		return deployStatus;
	}

	public void setDeployStatus(int deployStatus) {
		this.deployStatus = deployStatus;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
}
