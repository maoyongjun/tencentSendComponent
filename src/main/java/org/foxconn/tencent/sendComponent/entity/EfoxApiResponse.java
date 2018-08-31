package org.foxconn.tencent.sendComponent.entity;

import java.util.ArrayList;
import java.util.List;

import org.foxconn.tencent.sendComponent.entity.efoxResult.EfoxComponent;

import com.alibaba.fastjson.annotation.JSONField;

public class EfoxApiResponse {
	private String 	flag;
	private String 	message;
	private String 	failure_code;
	private TencentSendMsgRequest data;
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
	public TencentSendMsgRequest getData() {
		return data;
	}
	public void setData(TencentSendMsgRequest data) {
		this.data = data;
	}
	public class TencentSendMsgRequest {
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




		public class Data<T extends EfoxComponent>{
//			@JSONField(name="PartInfo")
			private List<? extends EfoxComponent> partInfo = new ArrayList<T>();

			public List<? extends EfoxComponent> getPartInfo() {
				return partInfo;
			}

			public void setPartInfo(List<? extends EfoxComponent> sendComponents) {
				this.partInfo = sendComponents;
			}
			
			
		}
	}

	
}
