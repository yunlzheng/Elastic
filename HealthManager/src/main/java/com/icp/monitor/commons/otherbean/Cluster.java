package com.icp.monitor.commons.otherbean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_cluster")
public class Cluster implements Serializable{
	/**
	 * 应用服务器集群唯一标识
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * 应用服务器集群名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 描述
	 */
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	/**
	 * 用户id
	 */
	@Column(name = "user_id")
	private int userId;

	/**
	 * 用户名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 租户id
	 */
	@Column(name = "tenantId")
	private String tenantId;

	/**
	 * 租户名
	 */
	@Column(name = "tenantName")
	private String tenantName;

	/**
	 * 操作系统镜像名
	 */
	@Column(name = "os_image")
	private String osImage;
	/**
	 * 模板类型
	 */
	@Column(name = "template")
	private String template;
	// ----------------------template attribute area------------------------//
	/**
	 * 内存容量
	 */
	@Column(name = "mem")
	private int mem;
	/**
	 * 磁盘容量
	 */
	@Column(name = "disk")
	private int disk;
	/**
	 * 外挂磁盘容量
	 */
	@Column(name = "xdisk")
	private int xdisk;
	/**
	 * cpu个数
	 */
	@Column(name = "cpu")
	private int cpu;
	// ---------------------------------------------------------------------//
	/**
	 * 容器类型
	 */
	@Column(name = "as_category")
	private String asCategory;

	/**
	 * 容器版本
	 */
	@Column(name = "as_serial")
	private String asSerial;

	/**
	 * 申请开放的http访问端口
	 */
	@Column(name = "http_port")
	private int httpPort;

	/**
	 * 申请开放的https访问端口
	 */
	@Column(name = "https_port")
	private int httpsPort;

	/**
	 * 集群主机最小数
	 */
	@Column(name = "min")
	private int min;

	/**
	 * 集群主机最大数
	 */
	@Column(name = "max")
	private int max;

	/**
	 * 集群状态 see Cluster.Status
	 */
	@Column(name = "status")
	private int status;

	/**
	 * 创建时间
	 */
	@Column(name = "create_datetime")
	private String createDateTime;

	/**
	 * 门户生成的订单号，用来关联门户子项目的数据
	 */
	@Column(name = "order_id")
	private String orderId;

	/**
	 * 内部域名
	 */
	@Column(name = "pxname")
	private String pxname;

	/**
	 * @see Cluster.Category.java
	 */
	@Column(name = "category", columnDefinition = "INT(2) default 0")
	private int category;

	
	/**
	 * @author wangxk
	 * 集群类型分类，标记是共有还是私有
	 *
	 */
	public enum Category {
		PUBLIC(1, "公共环境"),
		PRIVATE(2, "私有环境");
		private final int num;
        private final String msg;
        Category(int num, String msg) {
        	this.num = num;
        	this.msg = msg;
        }
		public int getNum() {
			return num;
		}
		public String getMsg() {
			return msg;
		}
	}
	
	public enum Status {
		INIT(0, "初始状态"),
		//创建应用服务是异步请求，有中间过程。参数正确就会返回"OK"，创建过程状态是后面通过消息返回的
		//状态4表示启动动作，具体步骤分成1、2、3这些过程状态(过程状态需要在页面上实时监控变化)，和-1、-2、-3、9这些启动结果状态，
		DO_START(4, "正在启动应用平台服务"),
		STARTING_VM(1, "正在启动虚拟机"), 
		START_VM_ERROR(-1, "启动虚拟机失败"), 
		DEPLOYING_APPSERVER(2, "正在部署应用"),
		DEPLOY_APPSERVER_ERROR(-2, "部署应用失败"), 
		STARTING_APPSERVER(3, "正在启动应用服务器"), 
		START_APPSERVER_ERROR(-3, "启动应用服务器失败"),
		STARTING_SUCCESS(9, "完成"),
		//停止和删除是同步请求，所以只记录最终状态
		DELETED_VM(-9, "已删除"),
		STOPPED_VM(5, "已停止");
		private int status;
		private String desc;
		private Status(int status, String desc) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getOsImage() {
		return osImage;
	}

	public void setOsImage(String osImage) {
		this.osImage = osImage;
	}

	public String getAsCategory() {
		return asCategory;
	}

	public void setAsCategory(String asCategory) {
		this.asCategory = asCategory;
	}

	public String getAsSerial() {
		return asSerial;
	}

	public void setAsSerial(String asSerial) {
		this.asSerial = asSerial;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public int getHttpsPort() {
		return httpsPort;
	}

	public void setHttpsPort(int httpsPort) {
		this.httpsPort = httpsPort;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	public int getXdisk() {
		return xdisk;
	}

	public void setXdisk(int xdisk) {
		this.xdisk = xdisk;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPxname() {
		return pxname;
	}

	public void setPxname(String pxname) {
		this.pxname = pxname;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
