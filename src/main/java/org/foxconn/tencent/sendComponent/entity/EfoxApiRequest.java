package org.foxconn.tencent.sendComponent.entity;

public class EfoxApiRequest {
	private String message_id;
	
	private Data data;
	
	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public class Data{
		
		private String pallent;
	
		public String getPallent() {
			return pallent;
		}
	
		public void setPallent(String pallent) {
			this.pallent = pallent;
		}
	}
	
	
	
}
