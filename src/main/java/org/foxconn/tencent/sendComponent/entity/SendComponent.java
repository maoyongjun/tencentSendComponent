package org.foxconn.tencent.sendComponent.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class SendComponent {
	@JSONField(name="Id")
	private String	id	;
	@JSONField(name="SvrAssetId")
	private String	svrAssetId	;
	@JSONField(name="SvrSN")
	private String	svrSN	;
	@JSONField(name="PartType")
	private String	partType	;
	@JSONField(name="OriPartPN")
	private String	oriPartPN	;
	@JSONField(name="OriPartSN")
	private String	oriPartSN	;
	@JSONField(name="FW")
	private String	fW	;
	@JSONField(name="ScanPartPN")
	private String	scanPartPN	;
	@JSONField(name="ScanPartSN")
	private String	scanPartSN	;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSvrAssetId() {
		return svrAssetId;
	}
	public void setSvrAssetId(String svrAssetId) {
		this.svrAssetId = svrAssetId;
	}
	public String getSvrSN() {
		return svrSN;
	}
	public void setSvrSN(String svrSN) {
		this.svrSN = svrSN;
	}
	public String getPartType() {
		return partType;
	}
	public void setPartType(String partType) {
		this.partType = partType;
	}
	public String getOriPartPN() {
		return oriPartPN;
	}
	public void setOriPartPN(String oriPartPN) {
		this.oriPartPN = oriPartPN;
	}
	public String getOriPartSN() {
		return oriPartSN;
	}
	public void setOriPartSN(String oriPartSN) {
		this.oriPartSN = oriPartSN;
	}
	public String getfW() {
		return fW;
	}
	public void setfW(String fW) {
		this.fW = fW;
	}
	public String getScanPartPN() {
		return scanPartPN;
	}
	public void setScanPartPN(String scanPartPN) {
		this.scanPartPN = scanPartPN;
	}
	public String getScanPartSN() {
		return scanPartSN;
	}
	public void setScanPartSN(String scanPartSN) {
		this.scanPartSN = scanPartSN;
	}
	
	
}
