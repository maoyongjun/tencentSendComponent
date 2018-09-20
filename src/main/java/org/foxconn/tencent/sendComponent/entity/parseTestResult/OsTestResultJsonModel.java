package org.foxconn.tencent.sendComponent.entity.parseTestResult;

import java.util.Date;

public class OsTestResultJsonModel {
	private String ssn;
	private String json;
	private Date lasteditdt;
	
	public Date getLasteditdt() {
		return lasteditdt;
	}
	public void setLasteditdt(Date lasteditdt) {
		this.lasteditdt = lasteditdt;
	}
	public String getJson() {
		return json;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public void setJson(String json) {
		if(json.indexOf("\"cpu\":{")!=-1){
			json = json.replace("\"cpu\":{", "\"cpu\":[{");
			json = json.replace("},\"hdd\"", "}],\"hdd\"");
		}
		//json = json.replace(",,", ",");
		this.json = json;
	}
	@Override
	public String toString() {
		return "OsMsgModel [ssn=" + ssn + ", json=" + json + "]";
	}
	
	
	
	
}