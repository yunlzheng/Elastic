package com.icp.monitor.commons.snmp.model;

import java.io.Serializable;

/**
 * @author zheng
 * */
public class DiskInfo implements Serializable{
	private String pathWhereTheDiskIsMounted;
	private String pathOfTheDeviceForThePartition;
	private String totalSizeOfTheDisk;
	private String availableSpaceOnTheDisk;
	private String usedSpaceOnTheDisk;
	private String percentageOfSpaceUsedOnDisk;
	private String percentageOfInodesUsedOnDisk;
	private String systemUptime;
	private String time;
	public DiskInfo(){
		
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPathWhereTheDiskIsMounted() {
		return pathWhereTheDiskIsMounted;
	}
	public void setPathWhereTheDiskIsMounted(String pathWhereTheDiskIsMounted) {
		this.pathWhereTheDiskIsMounted = pathWhereTheDiskIsMounted;
	}
	public String getPathOfTheDeviceForThePartition() {
		return pathOfTheDeviceForThePartition;
	}
	public void setPathOfTheDeviceForThePartition(
			String pathOfTheDeviceForThePartition) {
		this.pathOfTheDeviceForThePartition = pathOfTheDeviceForThePartition;
	}
	public String getTotalSizeOfTheDisk() {
		return totalSizeOfTheDisk;
	}
	public void setTotalSizeOfTheDisk(String totalSizeOfTheDisk) {
		this.totalSizeOfTheDisk = totalSizeOfTheDisk;
	}
	public String getAvailableSpaceOnTheDisk() {
		return availableSpaceOnTheDisk;
	}
	public void setAvailableSpaceOnTheDisk(String availableSpaceOnTheDisk) {
		this.availableSpaceOnTheDisk = availableSpaceOnTheDisk;
	}
	public String getUsedSpaceOnTheDisk() {
		return usedSpaceOnTheDisk;
	}
	public void setUsedSpaceOnTheDisk(String usedSpaceOnTheDisk) {
		this.usedSpaceOnTheDisk = usedSpaceOnTheDisk;
	}
	public String getPercentageOfSpaceUsedOnDisk() {
		return percentageOfSpaceUsedOnDisk;
	}
	public void setPercentageOfSpaceUsedOnDisk(String percentageOfSpaceUsedOnDisk) {
		this.percentageOfSpaceUsedOnDisk = percentageOfSpaceUsedOnDisk;
	}
	public String getPercentageOfInodesUsedOnDisk() {
		return percentageOfInodesUsedOnDisk;
	}
	public void setPercentageOfInodesUsedOnDisk(String percentageOfInodesUsedOnDisk) {
		this.percentageOfInodesUsedOnDisk = percentageOfInodesUsedOnDisk;
	}
	public String getSystemUptime() {
		return systemUptime;
	}
	public void setSystemUptime(String systemUptime) {
		this.systemUptime = systemUptime;
	}
	
	
	
	
}
