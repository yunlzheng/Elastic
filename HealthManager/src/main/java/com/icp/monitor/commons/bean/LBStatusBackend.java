package com.icp.monitor.commons.bean;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import com.icp.monitor.commons.util.SystemUtil;

@XmlRootElement(name = "this")
@Entity
@Table(name="t_log_lb_backend")
public class LBStatusBackend implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="cluster_id")
	private String clutsterId;
	
	@Column(name="app_id")
	private String appId;
	

	@Column(name="domain")
	private String domain;
	
	
	@Column(name="domain_type")
	private String domainType;
	
	
	@Column(name="pxname")
	private String pxname;
	
	private boolean validation;
	
	@Column(name="svnname")
	private String svname;
	
	@Column(name="qcur")
	private String qcur;
	
	@Column(name="qmax")
	private String qmax;
	
	@Column(name="scur")
	private String scur;
	
	@Column(name="smax")
	private String smax;
	
	@Column(name="slim")
	private String slim;
	
	@Column(name="stot")
	private String stot;
	
	@Column(name="bin")
	private String bin;
	
	@Column(name="bout")
	private String bout;
	
	@Column(name="dreq")
	private String dreq;
	
	@Column(name="dresp")
	private String dresp;
	
	@Column(name="ereq")
	private String ereq;
	
	@Column(name="econ")
	private String econ;
	
	@Column(name="eresp")
	private String eresp;
	
	@Column(name="wretr")
	private String wretr;

	@Column(name="wredis")
	private String wredis;
	
	@Column(name="status")
	private String status;
	
	@Column(name="weight")
	private String weight;
	
	@Column(name="act")
	private String act;
	
	@Column(name="bck")
	private String bck;
	
	@Column(name="chkfail")
	private String chkfail;
	
	@Column(name="chkdown")
	private String chkdown;
	
	@Column(name="lastchg")
	private String lastchg;
	
	@Column(name="downtime")
	private String downtime;
	
	@Column(name="qlimit")
	private String qlimit;
	
	@Column(name="pid")
	private String pid;
	
	@Column(name="iid")
	private String iid;
	
	@Column(name="sid")
	private String sid;

	@Column(name="throttle")
	private String throttle;
	
	@Column(name="lbtot")
	private String lbtot;
	
	@Column(name="tracked")
	private String tracked;
	
	@Column(name="type")
	private String type;
	
	@Column(name="rate")
	private String rate;
	
	@Column(name="ratelim")
	private String ratelim;
	
	@Column(name="ratemax")
	private String ratemax;

	@Column(name="checkstatus")
	private String checkstatus;
	
	@Column(name="checkcode")
	private String checkcode;
	
	@Column(name="checkduration")
	private String checkduration;
	
	@Column(name="hrsp1xx")
	private String hrsp1xx;
	
	@Column(name="hrsp2xx")
	private String hrsp2xx;
	
	@Column(name="hrsp3xx")
	private String hrsp3xx;

	@Column(name="hrsp4xx")
	private String hrsp4xx;
	
	@Column(name="hrsp5xx")
	private String hrsp5xx;
	
	@Column(name="hrspother")
	private String hrspother;
	
	@Column(name="hanafail")
	private String hanafail;
	
	@Column(name="reqrate")
	private String reqrate;
	
	@Column(name="reqratemax")
	private String reqratemax;
	
	@Column(name="reqtot")
	private String reqtot;
	
	@Column(name="cliabrt")
	private String cliabrt;
	
	@Column(name="srvabrt")
	private String srvabrt;

	@Column(name="joinTime")
	private String joinTime;

	@Column(name = "detailTime")
	private String detailTime;
	
	
	public String getDetailTime() {
		return detailTime;
	}

	public void setDetailTime(String detailTime) {
		this.detailTime = detailTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public String getPxname() {
		return pxname;
	}

	public void setPxname(String pxname) {
		this.pxname = pxname;
	}

	public String getSvname() {
		return svname;
	}

	public void setSvname(String svname) {
		this.svname = svname;
	}

	public String getQcur() {
		return qcur;
	}

	public void setQcur(String qcur) {
		this.qcur = qcur;
	}

	public String getQmax() {
		return qmax;
	}

	public void setQmax(String qmax) {
		this.qmax = qmax;
	}

	public String getScur() {
		return scur;
	}

	public void setScur(String scur) {
		this.scur = scur;
	}

	public String getSmax() {
		return smax;
	}

	public void setSmax(String smax) {
		this.smax = smax;
	}

	public String getSlim() {
		return slim;
	}

	public void setSlim(String slim) {
		this.slim = slim;
	}

	public String getStot() {
		return stot;
	}

	public void setStot(String stot) {
		this.stot = stot;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getBout() {
		return bout;
	}

	public void setBout(String bout) {
		this.bout = bout;
	}

	public String getDreq() {
		return dreq;
	}

	public void setDreq(String dreq) {
		this.dreq = dreq;
	}

	public String getDresp() {
		return dresp;
	}

	public void setDresp(String dresp) {
		this.dresp = dresp;
	}

	public String getEreq() {
		return ereq;
	}

	public void setEreq(String ereq) {
		this.ereq = ereq;
	}

	public String getEcon() {
		return econ;
	}

	public void setEcon(String econ) {
		this.econ = econ;
	}

	public String getEresp() {
		return eresp;
	}

	public void setEresp(String eresp) {
		this.eresp = eresp;
	}

	public String getWretr() {
		return wretr;
	}

	public void setWretr(String wretr) {
		this.wretr = wretr;
	}

	public String getWredis() {
		return wredis;
	}

	public void setWredis(String wredis) {
		this.wredis = wredis;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getBck() {
		return bck;
	}

	public void setBck(String bck) {
		this.bck = bck;
	}

	public String getChkfail() {
		return chkfail;
	}

	public void setChkfail(String chkfail) {
		this.chkfail = chkfail;
	}

	public String getChkdown() {
		return chkdown;
	}

	public void setChkdown(String chkdown) {
		this.chkdown = chkdown;
	}

	public String getLastchg() {
		return lastchg;
	}

	public void setLastchg(String lastchg) {
		this.lastchg = lastchg;
	}

	public String getDowntime() {
		return downtime;
	}

	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}

	public String getQlimit() {
		return qlimit;
	}

	public void setQlimit(String qlimit) {
		this.qlimit = qlimit;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getThrottle() {
		return throttle;
	}

	public void setThrottle(String throttle) {
		this.throttle = throttle;
	}

	public String getLbtot() {
		return lbtot;
	}

	public void setLbtot(String lbtot) {
		this.lbtot = lbtot;
	}

	public String getTracked() {
		return tracked;
	}

	public void setTracked(String tracked) {
		this.tracked = tracked;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRatelim() {
		return ratelim;
	}

	public void setRatelim(String ratelim) {
		this.ratelim = ratelim;
	}

	public String getRatemax() {
		return ratemax;
	}

	public void setRatemax(String ratemax) {
		this.ratemax = ratemax;
	}

	public String getCheckstatus() {
		return checkstatus;
	}

	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String getCheckduration() {
		return checkduration;
	}

	public void setCheckduration(String checkduration) {
		this.checkduration = checkduration;
	}

	public String getHrsp1xx() {
		return hrsp1xx;
	}

	public void setHrsp1xx(String hrsp1xx) {
		this.hrsp1xx = hrsp1xx;
	}

	public String getHrsp2xx() {
		return hrsp2xx;
	}

	public void setHrsp2xx(String hrsp2xx) {
		this.hrsp2xx = hrsp2xx;
	}

	public String getHrsp3xx() {
		return hrsp3xx;
	}

	public void setHrsp3xx(String hrsp3xx) {
		this.hrsp3xx = hrsp3xx;
	}

	public String getHrsp4xx() {
		return hrsp4xx;
	}

	public void setHrsp4xx(String hrsp4xx) {
		this.hrsp4xx = hrsp4xx;
	}

	public String getHrsp5xx() {
		return hrsp5xx;
	}

	public void setHrsp5xx(String hrsp5xx) {
		this.hrsp5xx = hrsp5xx;
	}

	public String getHrspother() {
		return hrspother;
	}

	public void setHrspother(String hrspother) {
		this.hrspother = hrspother;
	}

	public String getHanafail() {
		return hanafail;
	}

	public void setHanafail(String hanafail) {
		this.hanafail = hanafail;
	}

	public String getReqrate() {
		return reqrate;
	}

	public void setReqrate(String reqrate) {
		this.reqrate = reqrate;
	}

	public String getReqratemax() {
		return reqratemax;
	}

	public void setReqratemax(String reqratemax) {
		this.reqratemax = reqratemax;
	}

	public String getReqtot() {
		return reqtot;
	}

	public void setReqtot(String reqtot) {
		this.reqtot = reqtot;
	}

	public String getCliabrt() {
		return cliabrt;
	}

	public void setCliabrt(String cliabrt) {
		this.cliabrt = cliabrt;
	}

	public String getSrvabrt() {
		return srvabrt;
	}

	public void setSrvabrt(String srvabrt) {
		this.srvabrt = srvabrt;
	}

	public String getClutsterId() {
		return clutsterId;
	}

	public void setClutsterId(String clutsterId) {
		this.clutsterId = clutsterId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getDomainType() {
		return domainType;
	}

	public void setDomainType(String domainType) {
		this.domainType = domainType;
	}

	
	public boolean isValidation() {
		return validation;
	}

	public void setValidation(boolean validation) {
		this.validation = validation;
	}

	public LBStatusBackend(){
		
	}

	public LBStatusBackend(String[] nextLine){
		
		String pxname = nextLine[0];
		
		
		if(pxname.indexOf("PUB")!=-1){
			
		
			String[] datas = pxname.split("_");
			this.setDomainType(datas[0]);
			this.setClutsterId(datas[1]);
			this.setAppId(datas[2]);
			
			String domain = null;
			if(datas.length==4){
				
				domain = datas[3];
			
			}else{
				
				 int begin = datas[0].length()+datas[1].length()+datas[2].length()+3;
				 domain = pxname.substring(begin,pxname.length());
						 
			}
			
			this.setDomain(domain);
			
			this.validation = true;
			
		}else if(pxname.indexOf("PRI")!=-1){
			
			String[] datas = pxname.split("_");
			this.setDomainType(datas[0]);
			this.setClutsterId(datas[1]);
			String domain = null;
			if(datas.length==3){
				
				domain = datas[2];
			
			}else{
				
				 int begin = datas[0].length()+datas[1].length()+2;
				 domain = pxname.substring(begin,pxname.length());
						 
			}
			
			this.setDomain(domain);
			this.validation = true;
			
		}else{
			
			this.validation = false;
		
		}
		
		this.setPxname(pxname);
		this.setSvname(nextLine[1]);
		this.setQcur(nextLine[2]);
		this.setQmax(nextLine[3]);
		this.setScur(nextLine[4]);
		this.setSmax(nextLine[5]);
		this.setSlim(nextLine[6]);
		this.setStot(nextLine[7]);
		this.setBin(nextLine[8]);
		this.setBout(nextLine[9]);
		this.setDreq(nextLine[10]);
		this.setDresp(nextLine[11]);
		this.setEreq(nextLine[12]);
		this.setEcon(nextLine[13]);
		this.setEresp(nextLine[14]);
		this.setWretr(nextLine[15]);
		this.setWredis(nextLine[16]);
		this.setStatus(nextLine[17]);
		this.setWeight(nextLine[18]);
		this.setAct(nextLine[19]);
		this.setBck(nextLine[20]);
		this.setChkfail(nextLine[21]);
		this.setChkdown(nextLine[22]);
		this.setLastchg(nextLine[23]);
		this.setDowntime(nextLine[24]);
		this.setQlimit(nextLine[25]);
		this.setPid(nextLine[26]);
		this.setIid(nextLine[27]);
		this.setSid(nextLine[28]);
		this.setThrottle(nextLine[29]);
		this.setLbtot(nextLine[30]);
		this.setTracked(nextLine[31]);
		this.setType(nextLine[32]);
		this.setRate(nextLine[33]);
		this.setRatelim(nextLine[34]);
		this.setRatemax(nextLine[35]);
		this.setCheckstatus(nextLine[36]);
		this.setCheckcode(nextLine[37]);
		this.setCheckduration(nextLine[38]);
		this.setHrsp1xx(nextLine[39]);
		this.setHrsp2xx(nextLine[40]);
		this.setHrsp3xx(nextLine[41]);
		this.setHrsp4xx(nextLine[42]);
		this.setHrsp4xx(nextLine[43]);
		this.setHrspother(nextLine[44]);
		this.setHanafail(nextLine[45]);
		this.setReqrate(nextLine[46]);
		this.setReqratemax(nextLine[47]);
		this.setReqtot(nextLine[48]);
		this.setCliabrt(nextLine[49]);
		this.setSrvabrt(nextLine[50]);
		this.setJoinTime(SystemUtil.getToday());
		this.setDetailTime(SystemUtil.getCurrentTime());
		
	}
	
}
