package com.icp.monitor.displayer.warn;

import java.util.Hashtable;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.icp.monitor.commons.bean.AlertMember;
import com.icp.monitor.commons.bean.AlertMessage;
import com.icp.monitor.commons.bean.ExceptionMessage;
import com.icp.monitor.commons.bean.Member;
import com.icp.monitor.commons.bean.MonitorLog;
import com.icp.monitor.commons.bean.MonitorThreshold;
import com.icp.monitor.commons.bean.ServerConfig;
import com.icp.monitor.commons.dao.AlertMemberDao;
import com.icp.monitor.commons.dao.AlertMessageDao;
import com.icp.monitor.commons.dao.ExceptionDao;
import com.icp.monitor.commons.dao.MemberDao;
import com.icp.monitor.commons.dao.MonitorThresholdDao;
import com.icp.monitor.commons.dao.ServerConfigDao;
import com.icp.monitor.commons.util.SystemUtil;
import com.icp.monitor.displayer.enums.ExceptionEmuns;
import com.icp.monitor.displayer.util.mail.MailFactory;

public class AlertAdapter extends Thread {

	private MonitorThresholdDao monitorThresholdDao = null;
	private ServerConfigDao serverConfigDao = null;
	private AlertMemberDao alertMemberDao = null;
	private MemberDao memberDao = null;
	private AlertMessageDao alertMessageDao = null;
	private ExceptionDao exceptionDao = null;

	private MonitorThreshold monitorThreshold = null;
	private ServerConfig serverConfig = null;
	private MonitorLog log = null;

	private String account = null;
	/**
	 * 报警处理项目
	 * */
	private boolean showcpu = false;
	private boolean showdisk = false;
	private boolean showIo = false;
	private boolean showMem = false;

	/**
	 * 服务器对应的监控人员
	 * */
	private List<AlertMember> alertMembers = null;
	// private ApplicationContext context = null;

	// 标明是否发送邮件
	private boolean flag = false;

	@SuppressWarnings("unchecked")
	public AlertAdapter(ApplicationContext context, MonitorLog log) {

		this.log = log;
		// 获取相关Dao对象
		serverConfigDao = (ServerConfigDao) context.getBean("ReceiverServerConfigDao");
		monitorThresholdDao = (MonitorThresholdDao) context.getBean("ReceiverMonitorThresholdDao");
		alertMemberDao = (AlertMemberDao) context.getBean("ReceiverAlertMemberDao");
		memberDao = (MemberDao) context.getBean("ReceiverMemberDao");
		alertMessageDao = (AlertMessageDao) context.getBean("ReceiverAlertMessageDao");
		exceptionDao = (ExceptionDao) context.getBean("ReceiverExceptionDao");
		monitorThreshold = monitorThresholdDao.get(log.getIp());
		serverConfig = serverConfigDao.get(log.getIp());

		account = serverConfig.getCreater();
		
		// 获取监控项目
		showcpu = serverConfig.isShowCpu();
		showdisk = serverConfig.isShowDisk();
		showIo = serverConfig.isShowIo();
		showMem = serverConfig.isShowMem();

		// 获取租户下 该服务器对应的预警人员
		@SuppressWarnings("rawtypes")
		Hashtable eqProperties = new Hashtable();
		Hashtable<String, String> likeProperties = new Hashtable<String, String>();
		eqProperties.put("ip", log.getIp());
		eqProperties.put("admin", false);
		alertMembers = alertMemberDao.getList(eqProperties, likeProperties,null);
		
		
	}

	@Override
	public void run() {

		StringBuilder alertinfo = new StringBuilder("您的联通云平台虚拟机[").append(log.getIp()).append("]出现以下异常情况\n");

		if(monitorThreshold!=null){
		
		if (monitorThreshold.getCpuThreshlod() <= Integer
				.parseInt(log.getCpu())) {

			if (showcpu) {
				alertinfo.append("Cpu占有率[").append(log.getCpu()).append("%]监控数据高于预警值;\n");
				flag = true;
			}

			ExceptionMessage message = new ExceptionMessage();
			message.setIdentifiekey(log.getIo());
			message.setEndTime(SystemUtil.getCurrentTime());
			message.setLevel("1");
			message.setCreater(account);
			message.setName(ExceptionEmuns.CPUSHARE.toString());
			message.setStartTime(SystemUtil.getCurrentTime());
			message.setType("1");
			message.setMessage("虚拟机-"+log.getIp()+"，Cpu占有率[" + log.getCpu() + "%]监控数据高于预警值");
			exceptionDao.add(message);

		}

		if (monitorThreshold.getDiskThreshlod() <= Integer.parseInt(log
				.getDisk())) {

			if (showdisk) {
				alertinfo.append("磁盘占有率[").append(log.getDisk()).append("%]监控数据高于预警值;\n");
				flag = true;
			}
			
			ExceptionMessage message = new ExceptionMessage();
			message.setIdentifiekey(log.getIo());
			message.setEndTime(SystemUtil.getCurrentTime());
			message.setLevel("1");
			message.setCreater(account);
			message.setName(ExceptionEmuns.DISKSHARE.toString());
			message.setStartTime(SystemUtil.getCurrentTime());
			message.setType("1");
			message.setMessage("虚拟机-"+log.getIp()+"，硬盘占有率[" + log.getDisk() + "%]监控数据高于预警值");
			exceptionDao.add(message);

		}

		if (monitorThreshold.getIoThreshlod() <= Integer.parseInt(log.getIo())) {
			if (showIo) {
				alertinfo.append("Io占有率[").append(log.getIo()).append("%]监控数据高于预警值;\n");
				flag = true;
			}
			
			ExceptionMessage message = new ExceptionMessage();
			message.setIdentifiekey(log.getIo());
			message.setEndTime(SystemUtil.getCurrentTime());
			message.setLevel("1");
			message.setCreater(account);
			message.setName(ExceptionEmuns.IOSHARE.toString());
			message.setStartTime(SystemUtil.getCurrentTime());
			message.setType("1");
			message.setMessage("虚拟机-"+log.getIp()+"，Io占有率[" + log.getIo() + "%]监控数据高于预警值");
			exceptionDao.add(message);

		}

		if (monitorThreshold.getMemThreshlod() <= Integer
				.parseInt(log.getMem())) {
			if (showMem) {
				alertinfo.append("内存占有率[").append(log.getMem()).append("%]监控数据高于预警值;\n");
				flag = true;
			}

			ExceptionMessage message = new ExceptionMessage();
			message.setIdentifiekey(log.getIo());
			message.setEndTime(SystemUtil.getCurrentTime());
			message.setLevel("1");
			message.setCreater(account);
			message.setName(ExceptionEmuns.MEMSHARE.toString());
			message.setStartTime(SystemUtil.getCurrentTime());
			message.setType("1");
			message.setMessage("虚拟机-"+log.getIp()+"，内存占有率[" + log.getMem() + "%]监控数据高于预警值");
			exceptionDao.add(message);
			
		}

		if (flag) {

			MailFactory mailFactory = MailFactory.newInstance("test_zangfeng@yun70.com.cn", "123456","mail.yun70.com.cn");
			
			try {

				for (AlertMember alertMember : alertMembers) {

					if (alertMember.isEmail() == true) {

						System.out.println("send Email");
						Member member = memberDao.get(alertMember.getMid());
						if (member != null) {
							int result = mailFactory.SendMails(
									member.getEmail(), "云计算综合管理平台 监控预警",
									"<h2>尊敬的用户" + member.getName() + "</h2>"
											+ alertinfo.toString());
							
							if (result == 0) {

								AlertMessage message = new AlertMessage();
								message.setKey(log.getIp());
								message.setJoinTime(SystemUtil.getCurrentTime());
								message.setMemname(member.getName());
								message.setMessage(alertinfo.toString());
								message.setType("2");
								message.setUsername(account);
								alertMessageDao.add(message);

							}
						}

					}

					if (alertMember.isTele() == true) {

						System.out.println("send SMS");

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {

		}

	}
	}
	
}
