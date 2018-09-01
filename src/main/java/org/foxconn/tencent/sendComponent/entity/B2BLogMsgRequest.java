package org.foxconn.tencent.sendComponent.entity;

public class B2BLogMsgRequest {
	private String log_id;
	private String message_id;
	private String call_api_code;
	private String call_api_ip;
	private String method;
	private String start_datetime;
	private String end_datetime;
	private String result;
	private String failure_code;
	private String log_info;
	private String parent_api_code;
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getCall_api_code() {
		return call_api_code;
	}
	public void setCall_api_code(String call_api_code) {
		this.call_api_code = call_api_code;
	}
	public String getCall_api_ip() {
		return call_api_ip;
	}
	public void setCall_api_ip(String call_api_ip) {
		this.call_api_ip = call_api_ip;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStart_datetime() {
		return start_datetime;
	}
	public void setStart_datetime(String start_datetime) {
		this.start_datetime = start_datetime;
	}
	public String getEnd_datetime() {
		return end_datetime;
	}
	public void setEnd_datetime(String end_datetime) {
		this.end_datetime = end_datetime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFailure_code() {
		return failure_code;
	}
	public void setFailure_code(String failure_code) {
		this.failure_code = failure_code;
	}
	public String getLog_info() {
		return log_info;
	}
	public void setLog_info(String log_info) {
		this.log_info = log_info;
	}
	public String getParent_api_code() {
		return parent_api_code;
	}
	public void setParent_api_code(String parent_api_code) {
		this.parent_api_code = parent_api_code;
	}
	
	
}
