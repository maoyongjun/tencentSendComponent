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
	List<Runnable> oneWeekRunnables = new ArrayList<Runnable>();
	public void run() {
		addRunnables();
		for(Runnable runnable : oneDayRunnables){
			taskService.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.SECONDS);
		}
		for(Runnable runnable : oneHoursRunnables){
			taskService.scheduleAtFixedRate(runnable, 0, 3600, TimeUnit.SECONDS);
		}
		for(Runnable runnable : oneWeekRunnables){
			taskService.scheduleAtFixedRate(runnable, 0, 3600*24*7, TimeUnit.SECONDS);
		}
		
	}
	
	public void addRunnables(){
		addSendExcelRunnable();
		addupdatePnMapRunnable();
	}
	
	
	public void addSendExcelRunnable(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				logger.info("send excel task Begin");
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
					logger.error("send excel task Error",e);
				}
				logger.info("send excel task End");
			}
		};
		oneDayRunnables.add(runnable);
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
