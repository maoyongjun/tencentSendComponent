package org.foxconn.tencent.sendComponent.entity;

public class ResultMsg {
	private Data   data;
	private String failure_code;
	private String flag;
	private String logs;
	private String message;
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getFailure_code() {
		return failure_code;
	}

	public void setFailure_code(String failure_code) {
		this.failure_code = failure_code;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLogs() {
		return logs;
	}

	public void setLogs(String logs) {
		this.logs = logs;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public class Data{
		private String massage_id;

		public String getMassage_id() {
			return massage_id;
		}

		public void setMassage_id(String massage_id) {
			this.massage_id = massage_id;
		}

		@Override
		public String toString() {
			return "Data [massage_id=" + massage_id + "]";
		}
		
		
		
	}

	@Override
	public String toString() {
		return "ResultMsg [data=" + data + ", failure_code=" + failure_code + ", flag=" + flag + ", logs=" + logs
				+ ", message=" + message + "]";
	}
	
}
