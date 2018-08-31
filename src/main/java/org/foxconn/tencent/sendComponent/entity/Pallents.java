package org.foxconn.tencent.sendComponent.entity;

public class Pallents {
	private String message_id;
	
	private PallentData data;
	
	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public PallentData getData() {
		return data;
	}

	public void setData(PallentData data) {
		this.data = data;
	}

	public class PallentData{
		
		private String pallent;
	
		public String getPallent() {
			return pallent;
		}
	
		public void setPallent(String pallent) {
			this.pallent = pallent;
		}
	}
	
	
	
}
