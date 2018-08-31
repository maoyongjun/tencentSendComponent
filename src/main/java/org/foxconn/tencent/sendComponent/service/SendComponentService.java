package org.foxconn.tencent.sendComponent.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.foxconn.tencent.sendComponent.dao.OsMsgDao;
import org.foxconn.tencent.sendComponent.entity.B2BMsgHeader;
import org.foxconn.tencent.sendComponent.entity.Component;
import org.foxconn.tencent.sendComponent.entity.MMprodmaster;
import org.foxconn.tencent.sendComponent.entity.OsTestResultJsonModel;
import org.foxconn.tencent.sendComponent.entity.Pallents;
import org.foxconn.tencent.sendComponent.entity.ResultMsg;
import org.foxconn.tencent.sendComponent.entity.SendComponent;
import org.foxconn.tencent.sendComponent.entity.SendMsg;
import org.foxconn.tencent.sendComponent.sap.MMprodmasterSAPClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sap.conn.jco.JCoException;

@RestController
@Service
public class SendComponentService {
//	private String url ="https://tssp.tencent.com/open_api/logic_test";
	private String url ="http://10.67.37.83:8082/tencent/message";
	
	@Resource
	OsMsgDao osMsgDao;
	
	
	Logger logger = Logger.getLogger(SendComponentService.class);
	@PostMapping(path="/SupplierWriteServerPartInfo",consumes="application/json",produces="application/json")
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
		return json;
		
	}
	
	
	@PostMapping(path="/SupplierWriteOdmPartInfo",consumes="application/json",produces="application/json")
	public String sendOdmMsg(@RequestBody Pallents pallent){
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
				logger.info("key:"+key+",value:"+value);
				if(value!=null&&!"".equals(value)){
					master.setManufacturerpn(value);
					break;
				}
			}
				
			logger.info("update pn Map task End");
			
			osMsgDao.updateMMprocomponent(master);
		}
	}
	
	public String sendMsgToB2B(){
		String pallent="PA10002707";
		InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error("addr can not get!");
		}
		B2BMsgHeader data = new B2BMsgHeader();
		data.setMessage_name("SupplierOdmPartInfo_SEND_MSG");
		data.setMessage_type("Timer");
		data.setSource_client_ip(addr.getHostAddress());
		data.setSource_system("SFC");
		data.setBiz_code("SupplierOdmPartInfo_SEND");
		data.setMessage_id("");
		B2BMsgHeader.Append_data datadetail =  data.new Append_data();
		datadetail.setPallent(pallent);
		data.setAppend_data(datadetail);
		String json = JSON.toJSONString(data);
		logger.info("send to b2b:"+json);
		String resultMsg=null;
		try {
			resultMsg = sendFormData(url,json);
		} catch (Exception e) {
			logger.error(e.toString());
			logger.error("get mesId From B2B Error");
		}
		if(null==resultMsg){
			return "";
		}
        ResultMsg result = JSON.parseObject(resultMsg, ResultMsg.class);
        System.out.println(result);
        ResultMsg.Data resultData =  result.getData();
        
        if(resultData!=null){
        	return resultData.getMassage_id();
        }else{
        	return "";
        }
        
   }
	
	
	private String sendFormData(String url,String postjson){
		RestTemplate restTemplate = new RestTemplate();
		//请求头设置 
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //提交参数设置 
		MultiValueMap<String,String> p = new LinkedMultiValueMap<>();
		p.add("data",postjson);
		//提交请求
		HttpEntity< MultiValueMap<String,String>> entity = new HttpEntity< MultiValueMap<String,String>>(p,headers); 
		String result = restTemplate.postForObject(url,entity,String.class); 
		return result;
	}
	private String sendFormDataTest(String postjson){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders(); 
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		Gson gson = new Gson();
		Map<String,String> map = new HashMap<>();
		map.put("data", postjson);
		HttpEntity<String> httpEntity = new HttpEntity(gson.toJson(map), httpHeaders);
		ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class); 
		responseEntity.getBody();
		return "";
	}
	
	
	
}
