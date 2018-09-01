package org.foxconn.tencent.sendComponent.entity.log;

import java.util.Date;

public class EfoxLogModel {
	public static final String RECEIVE="receive";
	public static final String RETURN="return";
	public static final String REQUEST="request";
	public static final String RESPONSE="response";
	private Date logDate;
	private String apiname;
	private String msgId;
	private String msgType;//recevie/return/request/response
	private String data;
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	public String getApiname() {
		return apiname;
	}
	public void setApiname(String apiname) {
		this.apiname = apiname;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
