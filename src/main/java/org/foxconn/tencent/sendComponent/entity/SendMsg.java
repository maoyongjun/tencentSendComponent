package org.foxconn.tencent.sendComponent.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;


public class SendMsg {
	@JSONField(name="Action")
	private String action;
	
	@JSONField(name="Data")
	private Data data;
	
	@JSONField(name="Method")
	private String method;
	
	@JSONField(name="StartCompany")
	private String startCompany;
	
	

	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	

	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}

	


	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}


	public String getStartCompany() {
		return startCompany;
	}


	public void setStartCompany(String startCompany) {
		this.startCompany = startCompany;
	}




	public class Data{
//		@JSONField(name="PartInfo")
		private List<SendComponent> partInfo = new ArrayList<SendComponent>();
		
		public List<SendComponent> getPartInfo() {
			return partInfo;
		}

		public void setPartInfo(List<SendComponent> partInfo) {
			this.partInfo = partInfo;
		}
		
		
		
	}
}
