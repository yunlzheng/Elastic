package com.icp.monitor.commons.snmp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ContainCpuLoad implements Serializable{
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<CpuLoadInfo> list;
	public ContainCpuLoad(){
		
	}
	public ArrayList<CpuLoadInfo> getList() {
		return list;
	}
	public void setList(ArrayList<CpuLoadInfo> list) {
		this.list = list;
	}
	
}
