package org.foxconn.tencent.sendComponent.schedule; 

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.foxconn.tencent.sendComponent.entity.EfoxApiRequest;
import org.foxconn.tencent.sendComponent.service.SendComponentService;
import org.springframework.stereotype.Component;

@Component
public class ScheduleRunner {
	
	@Resource 
	SendComponentService sendComponentService;
	
	Logger logger = Logger.getLogger(ScheduleRunner.class);
	ScheduledExecutorService taskService = Executors.newScheduledThreadPool(10);;
	List<Runnable> oneDayRunnables = new ArrayList<Runnable>();
	List<Runnable> oneHoursRunnables = new ArrayList<Runnable>();
	List<Runnable> twoHoursRunnables = new ArrayList<Runnable>();
	List<Runnable> oneWeekRunnables = new ArrayList<Runnable>();
	List<Runnable> oneFiveMinutesRunnables = new ArrayList<Runnable>();
	public void run() {
		addRunnables();
		for(Runnable runnable : oneDayRunnables){
			taskService.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.SECONDS);
		}
		for(Runnable runnable : oneFiveMinutesRunnables){
			taskService.scheduleAtFixedRate(runnable, 0, 60*15, TimeUnit.SECONDS);
		}
		for(Runnable runnable : oneHoursRunnables){
			taskService.scheduleAtFixedRate(runnable, 0, 3600, TimeUnit.SECONDS);
		}
		for(Runnable runnable : twoHoursRunnables){
			taskService.scheduleAtFixedRate(runnable, 2*3600, 2*3600, TimeUnit.SECONDS);
		}
		for(Runnable runnable : oneWeekRunnables){
			taskService.scheduleAtFixedRate(runnable, 0, 3600*24*7, TimeUnit.SECONDS);
		}
		
	}
	
	public void addRunnables(){
		addSendComponentRunnable();
		addupdatePnMapRunnable();
		addParseTestResult();
	}
	
	public void addParseTestResult(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				logger.info( "parse Test Result task Begin");
				try {
					sendComponentService.parseTestResult();
					
				} catch (Exception e) {
					logger.error("parse Test Result task Error",e);
				}
				logger.info("parse Test Result  task End");
			}
		};
		oneFiveMinutesRunnables.add(runnable);
		
	}
	
	
	public void addSendComponentRunnable(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				logger.info("send component task Begin");
				try {
					String msgId ="";
					String action ="";
					String pallent =sendComponentService.getSendPallents("SERVER");
					if(!"".equals(pallent)){
						action ="SupplierServerPartInfo_SEND";
						msgId = sendComponentService.sendMQMsgToB2B(action,pallent);
						sendComponentService.sendLogMsgToB2B(msgId, "SupplierServerPartInfo");
					}
					pallent=sendComponentService.getSendPallents("ODM");
					if(!"".equals(pallent)){
						action ="SupplierOdmPartInfo_SEND";
						msgId = sendComponentService.sendMQMsgToB2B(action,pallent);
						sendComponentService.sendLogMsgToB2B(msgId, "SupplierOdmPartInfo");
					}
					
				} catch (Exception e) {
					logger.error("send component task Error",e);
				}
				logger.info("send component task End");
			}
		};
		twoHoursRunnables.add(runnable);
	}
	
	
	public void addupdatePnMapRunnable(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				logger.info("update pn Map task Begin");
				sendComponentService.updateMMprodmaster();
				logger.info("update pn Map task End");
			}
		};
		oneHoursRunnables.add(runnable);
	}
}
