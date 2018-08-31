package org.foxconn.tencent.sendComponent.entity.parseTestResult;

public class OsTestResultJsonModel {
	private String ssn;
	private String json;
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