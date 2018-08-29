package org.foxconn.tencent.sendComponent.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.foxconn.tencent.sendComponent.dao.OsMsgDao;
import org.foxconn.tencent.sendComponent.entity.Component;
import org.foxconn.tencent.sendComponent.entity.OsTestResultJsonModel;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class SendComponentService {
	
	@Resource
	OsMsgDao osMsgDao;
	
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
