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
//		addupdatePnMapRunnable();
//		addClearPnMapRunnable();
	}
	
	
	public void addSendExcelRunnable(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				logger.info("send excel task Begin");
				try {
					String pallent ="PA10002707";
					String action ="SupplierServerPartInfo_SEND";
					String msgId = sendComponentService.sendMQMsgToB2B(action,pallent);
					sendComponentService.sendLogMsgToB2B(msgId, "SupplierServerPartInfo");
					
					pallent="QSP183327717";
					action ="SupplierOdmPartInfo_SEND";
					msgId = sendComponentService.sendMQMsgToB2B(action,pallent);
					sendComponentService.sendLogMsgToB2B(msgId, "SupplierOdmPartInfo");
					
//					sendComponentService.updateMMprodmaster();
//					EfoxApiRequest request = new EfoxApiRequest();
//					request.setData(request.new Data());
//					sendComponentService.sendMsg(request);
					
					
				} catch (Exception e) {
					logger.error("send excel task Error",e);
				}
				logger.info("send excel task End");
			}
		};
		oneDayRunnables.add(runnable);
	}
	
//	public void addupdatePnMapRunnable(){
//		Runnable runnable = new Runnable() {
//			@Override
//			public void run() {
//				logger.info("update pn Map task Begin");
//				MMprodmasterSAPClient client = new MMprodmasterSAPClient();
//				for(String key :org.foxconn.tencent.shipoutExcel.entity.Component.pnmap.keySet())
//				{
//					String value=null;
//					try {
//						value = client.downMMprodmastercalls(key);
//					} catch (JCoException e) {
//						logger.error(e.getCause().toString());
//					}
//					org.foxconn.tencent.shipoutExcel.entity.Component.pnmap.put(key, value);
//					logger.info("key:"+key+",value"+value);
//				}
//					
//				logger.info("update pn Map task End");
//			}
//		};
//		oneHoursRunnables.add(runnable);
//	}
//	
//	public void addClearPnMapRunnable(){
//		Runnable runnable = new Runnable() {
//			@Override
//			public void run() {
//				logger.info("clear pn Map task Begin");
//				org.foxconn.tencent.shipoutExcel.entity.Component.pnmap= new HashMap<>();
//				logger.info("clear pn Map task End");
//			}
//		};
//		oneWeekRunnables.add(runnable);
//	}
	
}
