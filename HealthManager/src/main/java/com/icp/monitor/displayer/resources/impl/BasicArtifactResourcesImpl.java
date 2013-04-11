package com.icp.monitor.displayer.resources.impl;


import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.otherbean.BasicArtifact;
import com.icp.monitor.commons.otherbean.Cluster;
import com.icp.monitor.commons.otherdao.BasicArtifactDao;
import com.icp.monitor.commons.otherdao.ClusterDao;
import com.icp.monitor.commons.statistics.ArtifactInfo;
import com.icp.monitor.commons.util.AccountUtil;
import com.icp.monitor.displayer.resources.BasicArtifactResources;

public class BasicArtifactResourcesImpl implements BasicArtifactResources,ApplicationContextAware{

	private ApplicationContext applicationContext=null;
	private BasicArtifactDao basicArtifactDao = null;
	private ClusterDao clusterDao = null;
	private Logger log = Logger.getLogger(BasicArtifactResourcesImpl.class);
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		
		this.applicationContext = arg0;
		basicArtifactDao = (BasicArtifactDao) applicationContext.getBean("BasicArtifactDao");
		clusterDao = (ClusterDao)applicationContext.getBean("ClusterDao");
	}
	
	private String ClusterCategoryToString(int key){
		
		switch (key) {
		case 1:
		    return "公有环境";
		case 2:
			return "私有环境";
		default:
			return "未知环境";
		}
	}
	
	private String DeployStatusToString(int key){
		
		switch (key) {
		case 29:
		    return "测试中";
		case 4:
			return "运行中";
		default:
			return "未知状态";
		}
		
	}
	
	private String CategoryToString(int key){
		
		switch (key) {
		case 1:
		    return "Jar包";
		case 2:
			return "应用程序";
		default:
			return "未知类型";
		}
		
	}
	
	private String buildUrl(int clusterCate,int clusterid,int appid,String domain){
		
		switch (clusterCate) {
			case 1:
			    return "PUB_"+clusterid+"_"+appid+"_"+domain;
			case 2:
				 return "PRI_"+clusterid+"_"+domain;
			default:
				return "未知";
		}
		
	}

	public List<BasicArtifact> listAll(HttpServletRequest request) {
		
		return basicArtifactDao.list("from BasicArtifact");
		
	}

	@SuppressWarnings("unchecked")
	public List<ArtifactInfo> listDeploySuccess(HttpServletRequest request) {
		
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account="guest@guest.com";
		}
		
		String tenantName = AccountUtil.getTenantName(account);
		
		@SuppressWarnings("rawtypes")
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("deployStatus", 4);
		eqProperties.put("tenantName", tenantName);
		@SuppressWarnings("rawtypes")
		Hashtable likeProperties= new Hashtable();
		List<BasicArtifact> artifactList = basicArtifactDao.getList(eqProperties, likeProperties,null);
		List<ArtifactInfo> result = new ArrayList<ArtifactInfo>();
		
		for(int i=0;i<artifactList.size();i++){
			
			BasicArtifact artifact = artifactList.get(i);
			int clusterId = artifact.getClusterId();
			Cluster cluster = clusterDao.get(clusterId);
			if(cluster!=null){
				
				ArtifactInfo info = new ArtifactInfo();
				info.setCreaterName(artifact.getCreatorName());
				info.setAppid(artifact.getId());
				info.setAppCategory(CategoryToString(artifact.getCategory()));
				info.setAppname(artifact.getName());
				info.setAppStatus(DeployStatusToString(artifact.getDeployStatus()));
				info.setClusterId(clusterId);
				info.setClusterName(cluster.getName());
				info.setDesc(artifact.getDescription());
				info.setDomain(cluster.getPxname());
				info.setDomainType(ClusterCategoryToString(cluster.getCategory()));
				info.setUrl(buildUrl(cluster.getCategory(),cluster.getId(),artifact.getId(),cluster.getPxname()));
				result.add(info);
			}
			
			
		}
		return result;
	}
	

	@SuppressWarnings("unchecked")
	public List<ArtifactInfo> listTestingSuccess(HttpServletRequest request) {
		
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account="guest@guest.com";
		}
		
		String tenantName = AccountUtil.getTenantName(account);
		
		@SuppressWarnings("rawtypes")
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("deployStatus", 29);
		eqProperties.put("tenantName", tenantName);
		@SuppressWarnings("rawtypes")
		Hashtable likeProperties= new Hashtable();
		List<BasicArtifact> artifactList = basicArtifactDao.getList(eqProperties, likeProperties,null);
		
		List<ArtifactInfo> result = new ArrayList<ArtifactInfo>();
		for(int i=0;i<artifactList.size();i++){
			
			BasicArtifact artifact = artifactList.get(i);
			int clusterId = artifact.getClusterId();
			Cluster cluster = clusterDao.get(clusterId);
			
			if(cluster!=null){
				
				ArtifactInfo info = new ArtifactInfo();
				info.setAppid(artifact.getId());
				info.setAppCategory(CategoryToString(artifact.getCategory()));
				info.setAppname(artifact.getName());
				info.setAppStatus(DeployStatusToString(artifact.getDeployStatus()));
				info.setClusterId(clusterId);
				info.setClusterName(cluster.getName());
				info.setCreaterName(artifact.getCreatorName());
				info.setDesc(artifact.getDescription());
				info.setDomain(cluster.getPxname());
				info.setDomainType(ClusterCategoryToString(cluster.getCategory()));
				info.setUrl(buildUrl(cluster.getCategory(),cluster.getId(),artifact.getId(),cluster.getPxname()));
				result.add(info);
				
			}
			
			
			
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<BasicArtifact> listTestByCluster(HttpServletRequest request,
			int clusterid) {
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account="guest@guest.com";
		}
		@SuppressWarnings("rawtypes")
		Hashtable eqProperties = new Hashtable();
		
		eqProperties.put("clusterId", clusterid);
		eqProperties.put("deployStatus", 29);
		@SuppressWarnings("rawtypes")
		Hashtable likeProperties= new Hashtable();
		return basicArtifactDao.getList(eqProperties, likeProperties,null);
		
	}

	@SuppressWarnings("unchecked")
	public List<BasicArtifact> listDeployByCluster(HttpServletRequest request,
			int clusterid) {
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account="guest@guest.com";
		}
		@SuppressWarnings("rawtypes")
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("clusterId", clusterid);
		eqProperties.put("deployStatus", 4);
		@SuppressWarnings("rawtypes")
		Hashtable likeProperties= new Hashtable();
		return basicArtifactDao.getList(eqProperties, likeProperties,null);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ArtifactInfo> listPublicOrPrivate(HttpServletRequest request,int type) {
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account="guest@guest.com";
		}
		
		String tenantName = AccountUtil.getTenantName(account);
		Hashtable eqProperties = new Hashtable();
		
		if(type!=1)
		{
			//私有环境
			eqProperties.put("tenantName", tenantName);
			
		}		
		eqProperties.put("category", type);
		Hashtable likeProperties = new Hashtable();
		
		//获取租户下所有集群
		List<Cluster> clusters = clusterDao.getList(eqProperties, likeProperties,null);
		List<ArtifactInfo> result = new ArrayList<ArtifactInfo>();
		
		
		System.out.println(clusters.size());
		for(int i=0;i<clusters.size();i++){
			
			/**
			 * 获取集群下属于该租户的应用
			 * */
			Cluster cluster = clusters.get(i);
			int clusterId = cluster.getId();
			Hashtable eqProperties2 = new Hashtable();
			eqProperties2.put("clusterId", clusterId);
			eqProperties2.put("tenantName", tenantName);
			eqProperties2.put("deployStatus", 4);
			Hashtable likeProperties2 = new Hashtable();
			List<BasicArtifact> deployapps = basicArtifactDao.getList(eqProperties2, likeProperties2,null);
			
			for(int m =0;m<deployapps.size();m++){
				
				BasicArtifact artifact = deployapps.get(m);
				ArtifactInfo info = new ArtifactInfo();
				info.setCreaterName(artifact.getCreatorName());
				info.setAppid(artifact.getId());
				info.setAppCategory(CategoryToString(artifact.getCategory()));
				info.setAppname(artifact.getName());
				info.setAppStatus(DeployStatusToString(artifact.getDeployStatus()));
				info.setClusterId(clusterId);
				info.setClusterName(cluster.getName());
				info.setDesc(artifact.getDescription());
				info.setDomain(cluster.getPxname());
				info.setDomainType(ClusterCategoryToString(cluster.getCategory()));
				info.setUrl(buildUrl(cluster.getCategory(),cluster.getId(),artifact.getId(),cluster.getPxname()));
				result.add(info);
				
			}
			
			Hashtable eqProperties3 = new Hashtable();
			eqProperties3.put("clusterId", clusterId);
			eqProperties3.put("deployStatus", 29);
			eqProperties3.put("tenantName", tenantName);
			Hashtable likeProperties3 = new Hashtable();
			List<BasicArtifact> testapps = basicArtifactDao.getList(eqProperties3, likeProperties3,null);
			
			
			for(int m =0;m<testapps.size();m++){
				
				BasicArtifact artifact = testapps.get(m);
				ArtifactInfo info = new ArtifactInfo();
				info.setCreaterName(artifact.getCreatorName());
				info.setAppid(artifact.getId());
				info.setAppCategory(CategoryToString(artifact.getCategory()));
				info.setAppname(artifact.getName());
				info.setAppStatus(DeployStatusToString(artifact.getDeployStatus()));
				info.setClusterId(clusterId);
				info.setClusterName(cluster.getName());
				info.setDesc(artifact.getDescription());
				info.setDomain(cluster.getPxname());
				info.setDomainType(ClusterCategoryToString(cluster.getCategory()));
				info.setUrl(buildUrl(cluster.getCategory(),cluster.getId(),artifact.getId(),cluster.getPxname()));
				result.add(info);
				
			}
			
		}
		return result;
	}

	

	public List<ArtifactInfo> searchArtifact(HttpServletRequest request,
			String appname) {
		
		List<ArtifactInfo> result = new ArrayList<ArtifactInfo>();
		List<ArtifactInfo> list1 = this.listDeploySuccess(request);
		List<ArtifactInfo> list2 = this.listTestingSuccess(request);
		
		for(int i=0;i<list1.size();i++){
			
			ArtifactInfo artifactInfo = list1.get(i);
			if(artifactInfo.getAppname().indexOf(appname)!=-1){
				
				result.add(artifactInfo);
			}
			
		}
		
		for(int i=0;i<list2.size();i++){
			
			ArtifactInfo artifactInfo = list2.get(i);
			if(artifactInfo.getAppname().indexOf(appname)!=-1){
				
				result.add(artifactInfo);
			}
			
		}
		
		return result;
	}
}
