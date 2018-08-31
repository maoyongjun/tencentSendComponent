package org.foxconn.tencent.sendComponent.entity;

public class B2BMsgResponse {
	private String 	flag;
	private String 	message;
	private String 	failure_code;
	private SendMsg data;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getFailure_code() {
		return failure_code;
	}
	public void setFailure_code(String failure_code) {
		this.failure_code = failure_code;
	}
	public SendMsg getData() {
		return data;
	}
	public void setData(SendMsg data) {
		this.data = data;
	}
	
	
}
