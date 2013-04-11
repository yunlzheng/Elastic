package com.icp.monitor.displayer.resources.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.icp.monitor.commons.otherbean.Cluster;
import com.icp.monitor.commons.otherdao.ClusterDao;
import com.icp.monitor.commons.statistics.TreeNode;
import com.icp.monitor.commons.util.AccountUtil;
import com.icp.monitor.displayer.resources.ClusterResources;

public class ClusterResourcesImpl implements ClusterResources,ApplicationContextAware{

	private ApplicationContext applicationContext = null;
	private ClusterDao clusterDao = null;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		
		this.applicationContext = arg0;
		clusterDao = (ClusterDao) applicationContext.getBean("ClusterDao");
		
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Cluster> getPrivateClusters(String account){
		
		String tenantName = account;
		if(account.indexOf("@")!=-1){
			 tenantName = AccountUtil.getTenantName(account);
		}
		
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("category", 2);
		eqProperties.put("status", 9);
		eqProperties.put("tenantName", tenantName);
		Hashtable likeProperties = new Hashtable();
		List<Cluster> result = clusterDao.getList(eqProperties, likeProperties,null);
		
		return result; 
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Cluster> getPublicClusters(){
		
		Hashtable eqProperties = new Hashtable();
		eqProperties.put("category", 1);
		eqProperties.put("status", 9);
		Hashtable likeProperties = new Hashtable();
		List<Cluster> result = clusterDao.getList(eqProperties, likeProperties,null);
		return result; 
		
	}
	
	public Response getPrivateClustersTree(HttpServletRequest request) {
		
		String account = (String) request.getSession().getAttribute("user");
		if(account==null){
			account = "guest@guest.com";
		}
		List<Cluster> clusters = this.getPrivateClusters(account);
		
		List<TreeNode> tree = new ArrayList<TreeNode>();
		
		for(int i=0;i<clusters.size();i++){
			
			Cluster cluster = clusters.get(i);
			TreeNode node = new TreeNode();
			node.setId(cluster.getId());
			node.setName(cluster.getName());
			node.setIcon("../assets/images/icon_cluster.png");
			node.setOpen("false");
			if(i==0){
				node.setOpen("true");
			}
			node.setIsParent("true");
			List<TreeNode> childrens = new ArrayList<TreeNode>();
			TreeNode child1 = new TreeNode();
			child1.setName("详细信息");
			child1.setIcon("../assets/images/icon_info.png");
			child1.setIsParent("false");
			child1.setOpen("false");
			
			TreeNode child2 = new TreeNode();
			child2.setName("虚拟机列表");
			child2.setIcon("../assets/images/icon_list.png");
			child2.setIsParent("false");
			child2.setOpen("false");
			
			childrens.add(child1);
			childrens.add(child2);
			
			node.setChildren(childrens);
			tree.add(node);
		}
		
		return Response.ok(tree,MediaType.APPLICATION_JSON).build();
	}

	public Response getPrivateClusters(HttpServletRequest request) {
		
		return null;
	}

	public Response getPublicClustersTree(HttpServletRequest request) {
		
	
		List<Cluster> clusters = this.getPublicClusters();
		
		List<TreeNode> tree = new ArrayList<TreeNode>();
		
		for(int i=0;i<clusters.size();i++){
			
			Cluster cluster = clusters.get(i);
			TreeNode node = new TreeNode();
			node.setId(cluster.getId());
			node.setName(cluster.getName());
			node.setIcon("../assets/images/icon_cluster.png");
			node.setOpen("false");
			node.setIsParent("true");
			List<TreeNode> childrens = new ArrayList<TreeNode>();
			TreeNode child1 = new TreeNode();
			child1.setName("详细信息");
			child1.setIcon("../assets/images/icon_info.png");
			child1.setIsParent("false");
			child1.setOpen("false");
			
			TreeNode child2 = new TreeNode();
			child2.setName("虚拟机列表");
			child2.setIcon("../assets/images/icon_list.png");
			child2.setIsParent("false");
			child2.setOpen("false");
			
			childrens.add(child1);
			childrens.add(child2);
			
			node.setChildren(childrens);
			tree.add(node);
		}
		
		return Response.ok(tree,MediaType.APPLICATION_JSON).build();
		
	}

	public Response getPublicClusters(HttpServletRequest request) {
		
		return null;
	}


	public Response getPrivateClustersByUserTree(HttpServletRequest request) {
		
		TreeNode tree = new TreeNode();
		List<TreeNode> rootTree = new ArrayList<TreeNode>();
		List<String> tenants = clusterDao.getListDistinct("tenantName",String.class);
		
		for(String account:tenants){
			
			if(account!=null&&!account.equals("")){
				
				TreeNode node = new TreeNode();
				node.setName(account);
				node.setIcon("../assets/images/icon_user.png");
				node.setIsParent("true");
				node.setOpen("false");
				List<Cluster> clusters = this.getPrivateClusters(account);
			
				List<TreeNode> clusterChildrens = new ArrayList<TreeNode>();
				
				for(Cluster cluster:clusters){
					
					TreeNode clusterNode = new TreeNode();
					clusterNode.setId(cluster.getId());
					clusterNode.setIcon("../assets/images/icon_cluster.png");
					clusterNode.setName(cluster.getName());
					clusterNode.setOpen("false");
					clusterNode.setIsParent("true");
					
					List<TreeNode> childrens = new ArrayList<TreeNode>();
					TreeNode child1 = new TreeNode();
					child1.setName("详细信息");
					child1.setIcon("../assets/images/icon_info.png");
					child1.setIsParent("false");
					child1.setOpen("false");
					
					TreeNode child2 = new TreeNode();
					child2.setName("虚拟机列表");
					child2.setIcon("../assets/images/icon_list.png");
					child2.setIsParent("false");
					child2.setOpen("false");
					
					childrens.add(child1);
					childrens.add(child2);
					
					clusterNode.setChildren(childrens);
					
					clusterChildrens.add(clusterNode);
					
				}
				node.setChildren(clusterChildrens);
				
				rootTree.add(node);
			
			}
			
		}
		return 	Response.ok(rootTree,MediaType.APPLICATION_JSON).build();
	}

}
