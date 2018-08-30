package org.foxconn.tencent.sendComponent.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.foxconn.tencent.sendComponent.dao.OsMsgDao;
import org.foxconn.tencent.sendComponent.entity.Component;
import org.foxconn.tencent.sendComponent.entity.MMprodmaster;
import org.foxconn.tencent.sendComponent.entity.OsTestResultJsonModel;
import org.foxconn.tencent.sendComponent.entity.Pallents;
import org.foxconn.tencent.sendComponent.entity.SendComponent;
import org.foxconn.tencent.sendComponent.entity.SendMsg;
import org.foxconn.tencent.sendComponent.sap.MMprodmasterSAPClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sap.conn.jco.JCoException;

@RestController
@Service
public class SendComponentService {
	private String url ="https://tssp.tencent.com/open_api/logic_test";
	@Resource
	OsMsgDao osMsgDao;
	Logger logger = Logger.getLogger(SendComponentService.class);
	@PostMapping(path="/os/getData",consumes="application/json",produces="application/json")
	public String sendMsg(@RequestBody Pallents pallent){
		String pallentStr = pallent.getPallent();
		logger.info("pallent:"+pallentStr);
		SendMsg msg = new SendMsg();
		msg.setAction("SupplierWriteServerPartInfo");
		msg.setMethod("run");
		msg.setStartCompany("Foxconn");
		SendMsg.Data data =  msg.new Data();
		List<SendComponent> sendComponents =osMsgDao.getSendComponent(pallentStr);
		data.setPartInfo(sendComponents);
		msg.setData(data);
		String json = JSON.toJSONString(msg);
		System.out.println(json);
//		JSONObject postjson = JSON.parseObject(json);
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> responseEntity =
//				restTemplate.postForEntity("http://localhost:2489/efoxsfcSSNSTATUS", postjson, String.class);
//        String body = responseEntity.getBody();
//        System.out.println(body);
		return json;
		
	}
	
	public void parseTestResult(){
		
		List<OsTestResultJsonModel> list =  osMsgDao.getTestResult();
		
		for(OsTestResultJsonModel model : list){
			String parentId=UUID.randomUUID().toString();
			Component result =  JSON.parseObject(model.getJson(),Component.class);
			result= result.getSystem();
			result.setLasteditdt(new Date());
			result.setId(parentId);
			osMsgDao.addComponent(result);
			String type="";
			int id=1;
			for(Component com :result.getComponent()){
				if(com.getType()!=null&&!"".equals(com.getType())){
					type=com.getType();
				}else{
					com.setType(type);
				}
				com.setParentId(parentId);
				com.setId(Integer.toString(id));
				com.setLasteditdt(new Date());
				osMsgDao.addComponent(com);
				id++;
			}
			
			
		}
		
	}
	public void updateMMprodmaster(){
		
		List<MMprodmaster> findSkunos = osMsgDao.findSkunos();
		for(MMprodmaster master:findSkunos){
			logger.info("update pn Map task Begin");
			String key = master.getPartno();
			MMprodmasterSAPClient client = new MMprodmasterSAPClient();
			
			
			String value=null;
			for(int i=0;i<3;i++){
				try {
					value = client.downMMprodmastercalls(key);
				} catch (JCoException e) {
					logger.error(e.getCause().toString());
				}
				logger.info("key:"+key+",value"+value);
				if(value!=null&&!"".equals(value)){
					master.setManufacturerpn(value);
					break;
				}
			}
				
			logger.info("update pn Map task End");
			
			osMsgDao.updateMMprocomponent(master);
		}
	}
	
}
