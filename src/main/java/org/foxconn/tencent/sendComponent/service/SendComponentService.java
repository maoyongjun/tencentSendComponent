package org.foxconn.tencent.sendComponent.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.foxconn.tencent.sendComponent.dao.OsMsgDao;
import org.foxconn.tencent.sendComponent.entity.Component;
import org.foxconn.tencent.sendComponent.entity.OsTestResultJsonModel;
import org.foxconn.tencent.sendComponent.entity.SendComponent;
import org.foxconn.tencent.sendComponent.entity.SendMsg;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class SendComponentService {
	private String url ="https://tssp.tencent.com/open_api/logic_test";
	@Resource
	OsMsgDao osMsgDao;
	
	public void sendMsg(){
		SendMsg msg = new SendMsg();
		msg.setAction("SupplierWriteServerPartInfo");
		msg.setMethod("run");
		msg.setStartCompany("Foxconn");
		SendMsg.Data data =  msg.new Data();
		List<SendComponent> sendComponents =osMsgDao.getSendComponent();
		data.setPartInfo(sendComponents);
		msg.setData(data);
		String json = JSON.toJSONString(msg);
		System.out.println(json);
		JSONObject postjson = JSON.parseObject(json);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity =
				restTemplate.postForEntity("http://localhost:2489/efoxsfcSSNSTATUS", postjson, String.class);
        String body = responseEntity.getBody();
        System.out.println(body);
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
}
