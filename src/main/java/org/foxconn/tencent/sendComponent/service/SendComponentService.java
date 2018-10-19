package org.foxconn.tencent.sendComponent.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.foxconn.tencent.sendComponent.dao.OsMsgDao;
import org.foxconn.tencent.sendComponent.entity.B2BLogMsgRequest;
import org.foxconn.tencent.sendComponent.entity.B2BMQMsgRequest;
import org.foxconn.tencent.sendComponent.entity.B2BMQMsgResponse;
import org.foxconn.tencent.sendComponent.entity.EfoxApiRequest;
import org.foxconn.tencent.sendComponent.entity.EfoxApiResponse;
import org.foxconn.tencent.sendComponent.entity.Config.B2BUrlEntity;
import org.foxconn.tencent.sendComponent.entity.efoxParno.MMprodmaster;
import org.foxconn.tencent.sendComponent.entity.efoxResult.EfoxComponent;
import org.foxconn.tencent.sendComponent.entity.efoxResult.OdmPartComponent;
import org.foxconn.tencent.sendComponent.entity.efoxResult.ServerComponent;
import org.foxconn.tencent.sendComponent.entity.log.EfoxLogModel;
import org.foxconn.tencent.sendComponent.entity.parseTestResult.OsTestComponent;
import org.foxconn.tencent.sendComponent.entity.parseTestResult.OsTestResultJsonModel;
import org.foxconn.tencent.sendComponent.factory.EfoxComponentFactorys;
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
import com.sap.conn.jco.JCoException;

@RestController
@Service
public class SendComponentService {
//	private String url ="https://tssp.tencent.com/open_api/logic_test";
//	private String serverIp="10.67.37.83";
//	private String serverPort="8082";
//	private String mqUrl ="http://"+serverIp+":"+serverPort+"/tencent/message";
//	private String logUrl="http://"+serverIp+":"+serverPort+"/logs";
	public static String SERVER_ACTION="SupplierWriteServerPartInfo";
	public static String ODM_ACTION="SupplierWriteOdmPartInfo";
	@Resource
	OsMsgDao osMsgDao;
	@Resource
	EfoxComponentFactorys factorys;
	@Resource
	B2BUrlEntity b2BUrlEntity;
	
	
	Logger logger = Logger.getLogger(SendComponentService.class);
	@PostMapping(path="/SupplierWriteServerPartInfo",consumes="application/json",produces="application/json")
	public String sendMsg(@RequestBody EfoxApiRequest request){
		return sendTencentComponent(request,SERVER_ACTION,ServerComponent.class); 
		
	}
	
	
	@PostMapping(path="/SupplierWriteOdmPartInfo",consumes="application/json",produces="application/json")
	public String sendOdmMsg(@RequestBody EfoxApiRequest request){
		return sendTencentComponent(request,ODM_ACTION,OdmPartComponent.class); 
	}
	
	private <T extends EfoxComponent> String sendTencentComponent(EfoxApiRequest request,String action,Class<T> t){
		// 回复给B2B的信息
		EfoxApiResponse response = new EfoxApiResponse();
		String msgid ="";
		try {
			
			msgid = request.getMessage_id();
			try {
				addLog(action, msgid, EfoxLogModel.RECEIVE, JSON.toJSONString(request));
			} catch (Exception e) {
				logger.error("record log Error: ", e);
			}
			String pallentStr = request.getData().getPallent();
			logger.info("msgid:" + msgid + "," + "pallent:" + pallentStr);

			// 创建回复信息 覆盖 请求腾讯接口锁需要的消息，给B2B请求腾讯接口的时候使用。
			EfoxApiResponse.TencentSendMsgRequest data = response.new TencentSendMsgRequest();
			response.setData(data);// 附加消息
			data.setAction(action);
			data.setMethod("run");
			data.setStartCompany("Foxconn");
			// 将要发送出去的附加信息
			EfoxApiResponse.TencentSendMsgRequest.Data<T> serverComponent = data.new Data<T>();
			data.setData(serverComponent);
			EfoxComponentFactorys.EfoxComponentFactory factory = factorys.new EfoxComponentFactory();
			List<? extends EfoxComponent> sendComponents =factory.getComponent(action,pallentStr);
			
			serverComponent.setPartInfo(sendComponents);
			response.setFailure_code("");
			response.setFlag("SUCCESS");
			response.setMessage("success");
		} catch (Exception e) {
			logger.error("get component error ", e);
			response.setFlag("FAIL");
			response.setMessage("get component error");
		}

		String json = JSON.toJSONString(response);
		logger.info(json);
		try {
			addLog(action, msgid, EfoxLogModel.RETURN, json);
		} catch (Exception e) {
			logger.error("record log Error: ", e);
		}
		return json;
		
	}
	public void parseTestResult(){
		
		List<OsTestResultJsonModel> list =  osMsgDao.getTestResult();
		
		for(OsTestResultJsonModel model : list){
			String parentId=UUID.randomUUID().toString();
			OsTestComponent result =  JSON.parseObject(model.getJson(),OsTestComponent.class);
			result= result.getSystem();
			result.setLasteditdt(model.getLasteditdt());
			result.setId(parentId);
			osMsgDao.addComponent(result);
			String type="";
			int id=1;
			for(OsTestComponent com :result.getComponent()){
				if(com.getType()!=null&&!"".equals(com.getType())){
					type=com.getType();
				}else{
					com.setType(type);
				}
				com.setParentId(parentId);
				com.setId(Integer.toString(id));
				com.setLasteditdt(model.getLasteditdt());
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
					logger.error("get pn from sap error",e);
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
	public String sendLogMsgToB2B(String msgId,String action){
		B2BLogMsgRequest logMsg = new B2BLogMsgRequest();
		logMsg.setLog_id("");
		logMsg.setMessage_id(msgId);
		logMsg.setCall_api_code("Worker-Message");
		logMsg.setMethod("POST");
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		logMsg.setStart_datetime(sdf.format(new Date()));
		logMsg.setEnd_datetime(sdf.format(new Date()));
		logMsg.setResult("SUCCESS");
		logMsg.setFailure_code("");
		logMsg.setLog_info("success");
		logMsg.setParent_api_code("SFCjob_"+action);
		logMsg.setCall_api_ip(b2BUrlEntity.getServerIp());
		String json = JSON.toJSONString(logMsg);
		logger.info("send to b2b logApi request-->"+json);
		try {
			addLog("SFCjob_"+action, msgId, EfoxLogModel.REQUEST, json);
		} catch (Exception e) {
			logger.error("recode b2b logApi log Error: ", e);
		}
		String resultMsg=null;
		try {
			resultMsg = sendFormData(b2BUrlEntity.getLogUrl(),json);
		} catch (Exception e) {
			logger.error(e.toString());
			logger.error("get mesId From B2B Error");
		}
		if(null==resultMsg){
			return "";
		}
		logger.info("send to b2b logApi response-->"+resultMsg);
		try {
			addLog("SFCjob_"+action, msgId, EfoxLogModel.RESPONSE, resultMsg);
		} catch (Exception e) {
			logger.error("recode b2b logApi log Error: ", e);
		}
        B2BMQMsgResponse result = JSON.parseObject(resultMsg, B2BMQMsgResponse.class);
        logger.info(result);
        B2BMQMsgResponse.Data resultData =  result.getData();
        if(resultData!=null){
        	return resultData.getMassage_id();
        }else{
        	return "";
        }
	}
	
	public String sendMQMsgToB2B(String action,String pallent){
//		String pallent="PA10002707";
		String msgId="";
		InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			logger.error("addr can not get!");
		}
		B2BMQMsgRequest data = new B2BMQMsgRequest();
		data.setMessage_name(action+"_MSG");
		data.setMessage_type("Timer");
		data.setSource_client_ip(addr.getHostAddress());
		data.setSource_system("SFC");
		data.setBiz_code(action);
		data.setMessage_id("");
		B2BMQMsgRequest.Append_data datadetail =  data.new Append_data();
		datadetail.setPallent(pallent);
		data.setAppend_data(datadetail);
		String json = JSON.toJSONString(data);
		logger.info("send to b2b MQApi  request-->"+json);
		try {
			addLog("B2Blog_"+action, msgId, EfoxLogModel.REQUEST, json);
		} catch (Exception e) {
			logger.error("send b2b MQApi log Error: ", e);
		}
		String resultMsg=null;
		try {
			resultMsg = sendFormData(b2BUrlEntity.getMqUrl(),json);
		} catch (Exception e) {
			logger.error(e.toString());
			logger.error("get mesId From B2B Error");
		}
		if(null==resultMsg){
			return "";
		}
		logger.info("send to b2b MQApi  response-->"+resultMsg);
		
        B2BMQMsgResponse result = JSON.parseObject(resultMsg, B2BMQMsgResponse.class);
        logger.info(result);
        B2BMQMsgResponse.Data resultData =  result.getData();
        
        if(resultData!=null){
        	msgId= resultData.getMassage_id();
        }else{
        	msgId= "";
        }
        try {
			addLog("B2Blog_"+action, msgId, EfoxLogModel.RESPONSE, resultMsg);
		} catch (Exception e) {
			logger.error("send b2b MQApi log Error: ", e);
		}
        return msgId;
        
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
//	private String sendFormDataTest(String postjson){
//		RestTemplate restTemplate = new RestTemplate();
//		HttpHeaders httpHeaders = new HttpHeaders(); 
//		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		Gson gson = new Gson();
//		Map<String,String> map = new HashMap<>();
//		map.put("data", postjson);
//		HttpEntity<String> httpEntity = new HttpEntity(gson.toJson(map), httpHeaders);
//		ResponseEntity responseEntity = restTemplate.exchange(b2BUrlEntity.getMqUrl(), HttpMethod.POST, httpEntity, Object.class); 
//		responseEntity.getBody();
//		return "";
//	}
//	
	private void addLog(String apiname,String msgId,String msgType,String data){
		EfoxLogModel log = new EfoxLogModel();
		log.setLogDate(new Date());
		log.setData(data);
		log.setApiname(apiname);
		log.setMsgId(msgId);
		log.setMsgType(msgType);
		try {
			this.osMsgDao.addLog(log);
		} catch (Exception e) {
			logger.error("recode log error:",e);
		}
	}
	public String getSendPallents(String transtype){
		String pallents="";
		try {
			pallents = this.osMsgDao.getSendPallents(transtype);
		} catch (Exception e) {
			logger.error("get Pallents  error:",e);
			addLog("fc_getSendPallents","",EfoxLogModel.REQUEST,"get Pallents  error");
		}
		return pallents;
	}
	
}
