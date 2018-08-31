package org.foxconn.tencent.sendComponent.dao;

import java.util.List;
import java.util.Map;

import org.foxconn.tencent.sendComponent.entity.Component;
import org.foxconn.tencent.sendComponent.entity.MMprodmaster;
import org.foxconn.tencent.sendComponent.entity.OdmPart;
import org.foxconn.tencent.sendComponent.entity.OsTestResultJsonModel;
import org.foxconn.tencent.sendComponent.entity.SendComponent;
import org.springframework.dao.DataAccessException;

public interface OsMsgDao{
	//发送excel的数据
	public OsTestResultJsonModel findAll(Map<String,Object> map)  throws DataAccessException;
	
	//解析测试传送过来的部件信息
	public List<OsTestResultJsonModel> getTestResult()  throws DataAccessException;
	
	//写入解析出来的部件信息
	public void addComponent(Component component) throws DataAccessException;
	
	//获取要发送的数据
	public List<SendComponent> getSendComponent(String pallents) throws DataAccessException;
	
	//更新供应商料号
	public void updateMMprocomponent(MMprodmaster mmprodmaster) throws DataAccessException;
	
	//查找需要更新供应商料号的所有料号
	public List<MMprodmaster> findSkunos() throws DataAccessException;
	
	//获取原物料出货的数据
	public List<OdmPart> getOdmComponent(String pallents) throws DataAccessException;
	
}
