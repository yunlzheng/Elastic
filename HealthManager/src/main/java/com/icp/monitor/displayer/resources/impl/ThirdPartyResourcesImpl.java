package com.icp.monitor.displayer.resources.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import au.com.bytecode.opencsv.CSVReader;

import com.icp.monitor.commons.bean.LBStatus;
import com.icp.monitor.commons.otherbean.Cluster;
import com.icp.monitor.commons.otherdao.ClusterDao;
import com.icp.monitor.displayer.resources.ThirdPartyResources;

public class ThirdPartyResourcesImpl implements ApplicationContextAware,
		ThirdPartyResources {

	private ApplicationContext context = null;

	private String url;
	private String lbserver;

	public String getLbserver() {
		return lbserver;
	}

	public String getUrl() {
		return url;
	}
	
	@Value("#{SystemConfig['lbserver']}")
	public void setLbserver(String lbserver) {
		this.lbserver = lbserver;
	}

	

	@Value("#{SystemConfig['iaasurl']}")
	public void setUrl(String url) {
		this.url = url;
	}

	public List<Cluster> list() {

		ClusterDao dao = (ClusterDao) context.getBean("ClusterDao");
		return dao.list("from Cluster");
	}

	public List<Cluster> checkList(String name, String check) {
		ClusterDao dao = (ClusterDao) context.getBean("ClusterDao");
		Map<String, String> eqProperties = new HashMap<String, String>();

		eqProperties.put("userName", name);
		Map<String, String> likeProperties = new HashMap<String, String>();
		likeProperties.put("name", check);

		return dao.getList(eqProperties, likeProperties,null);
	}

	public List<Cluster> checkAdminList(String check) {
		ClusterDao dao = (ClusterDao) context.getBean("ClusterDao");
		Map<String, String> eqProperties = new HashMap<String, String>();

		Map<String, String> likeProperties = new HashMap<String, String>();
		likeProperties.put("name", check);

		return dao.getList(eqProperties, likeProperties,null);
	}

	public List<Cluster> checkAppList(String name, String check) {
		ClusterDao dao = (ClusterDao) context.getBean("ClusterDao");
		Map<String, String> eqProperties = new HashMap<String, String>();

		eqProperties.put("userName", name);

		Map<String, String> likeProperties = new HashMap<String, String>();
		likeProperties.put("pxname", check);

		return dao.getList(eqProperties, likeProperties,null);
	}

	public Cluster get(int pk) {

		ClusterDao dao = (ClusterDao) context.getBean("ClusterDao");
		return dao.get(pk);
	}

	public List<Cluster> listByUser(String name) {

		ClusterDao dao = (ClusterDao) context.getBean("ClusterDao");
		Hashtable<String, String> eqProperties = new Hashtable<String, String>();
		if(name.indexOf("@")!=-1){
			eqProperties.put("userName", name);
		}
		Hashtable<String, String> likeProperties = new Hashtable<String, String>();
		List<Cluster> list = dao.getList(eqProperties, likeProperties,null);
		return list;
		
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;

	}

	public Response getVMListByCluster(String id) {

		try {
		
			url = this.getUrl();
			String res = "";
			WebClient client = WebClient.create(url+ "/veapi?cmd=GtAppServerVm&p1=" + id);
			res = client.invoke("GET", "", String.class);
			return Response.ok(res, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.noContent().build();

		}

	}

	public Response getVMStatus(String id) {

		try {
			
			url = this.getUrl();
			String res = "";
			WebClient client = WebClient.create(url + "/veapi?cmd=GtVmStat&p1="
					+ id);
			res = client.invoke("GET", "", String.class);
			return Response.ok(res, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.noContent().build();
		}

	}

	public ArrayList<LBStatus> getVMStatusList() {

		String query =this.getLbserver();
		ArrayList<LBStatus> list = new ArrayList<LBStatus>();
		URL url = null;
		try {
			url = new URL(query);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			
			CSVReader reader = new CSVReader(in);
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {

				LBStatus vm = new LBStatus(nextLine);
				list.add(vm);

			}

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

		return list;
	}

}
