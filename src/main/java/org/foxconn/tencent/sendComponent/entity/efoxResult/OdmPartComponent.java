package org.foxconn.tencent.sendComponent.entity.efoxResult;

import com.alibaba.fastjson.annotation.JSONField;

public class OdmPartComponent extends EfoxComponent{
	@JSONField(name="Id")
	private String id;
	@JSONField(name="PartType")
	private String partType;
	@JSONField(name="PuchaseNumber")
	private String puchaseNumber;
	@JSONField(name="PartModel")
	private String partModel;
	@JSONField(name="PartSN")
	private String partSN;
	@JSONField(name="IdcpName")
	private String idcpName;
	@JSONField(name="IdcpAdress")
	private String idcpAdress;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public String getPuchaseNumber() {
		return puchaseNumber;
	}
	public void setPuchaseNumber(String puchaseNumber) {
		this.puchaseNumber = puchaseNumber;
	}
	public String getPartModel() {
		return partModel;
	}
	public void setPartModel(String partModel) {
		this.partModel = partModel;
	}
	public String getPartSN() {
		return partSN;
	}
	public void setPartSN(String partSN) {
		this.partSN = partSN;
	}
	public String getIdcpName() {
		return idcpName;
	}
	public void setIdcpName(String idcpName) {
		this.idcpName = idcpName;
	}
	public String getIdcpAdress() {
		return idcpAdress;
	}
	public void setIdcpAdress(String idcpAdress) {
		this.idcpAdress = idcpAdress;
	}
	

}
