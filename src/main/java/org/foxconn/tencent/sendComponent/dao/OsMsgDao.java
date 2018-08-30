package org.foxconn.tencent.sendComponent.dao;

import java.util.List;
import java.util.Map;

import org.foxconn.tencent.sendComponent.entity.Component;
import org.foxconn.tencent.sendComponent.entity.MMprodmaster;
import org.foxconn.tencent.sendComponent.entity.OsTestResultJsonModel;
import org.foxconn.tencent.sendComponent.entity.SendComponent;
import org.springframework.dao.DataAccessException;

public interface OsMsgDao{
	public OsTestResultJsonModel findAll(Map<String,Object> map)  throws DataAccessException;
	
	public List<OsTestResultJsonModel> getTestResult()  throws DataAccessException;
	
	public void addComponent(Component component) throws DataAccessException;
	
	public List<SendComponent> getSendComponent(String pallents) throws DataAccessException;
	
	public void updateMMprocomponent(MMprodmaster mmprodmaster) throws DataAccessException;
	
	public List<MMprodmaster> findSkunos() throws DataAccessException;
	
}
