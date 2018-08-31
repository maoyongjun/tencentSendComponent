package org.foxconn.tencent.sendComponent.factory;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.foxconn.tencent.sendComponent.dao.OsMsgDao;
import org.foxconn.tencent.sendComponent.entity.efoxResult.EfoxComponent;
import org.foxconn.tencent.sendComponent.service.SendComponentService;
import org.springframework.stereotype.Component;

@Component
public class EfoxComponentFactorys {
	@Resource
	OsMsgDao osMsgDao;
	
	public class EfoxComponentFactory{
		public List<? extends EfoxComponent> getComponent(String action,String pallents){
			List<? extends  EfoxComponent> list  =null;
			if(SendComponentService.SERVER_ACTION.equals(action)){
				list = osMsgDao.getSendComponent(pallents); 
			}else if(SendComponentService.ODM_ACTION.equals(action)){
				list = osMsgDao.getOdmComponent(pallents); 
			}
			
			return list;
		}
	}
}
