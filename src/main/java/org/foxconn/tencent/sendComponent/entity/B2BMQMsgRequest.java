package org.foxconn.tencent.sendComponent.entity;

public class B2BMQMsgRequest {
		private String message_id;
		private String message_name;
		private String message_type;
		private String source_system;
		private String source_client_ip;
		private String biz_code;
		private Append_data append_data;

		public String getMessage_name() {
			return message_name;
		}

		public void setMessage_name(String message_name) {
			this.message_name = message_name;
		}

		public String getMessage_type() {
			return message_type;
		}

		public void setMessage_type(String message_type) {
			this.message_type = message_type;
		}

		public String getSource_system() {
			return source_system;
		}

		public void setSource_system(String source_system) {
			this.source_system = source_system;
		}

		public String getSource_client_ip() {
			return source_client_ip;
		}

		public void setSource_client_ip(String source_client_ip) {
			this.source_client_ip = source_client_ip;
		}

		public String getBiz_code() {
			return biz_code;
		}

		public void setBiz_code(String biz_code) {
			this.biz_code = biz_code;
		}

		public String getMessage_id() {
			return message_id;
		}

		public void setMessage_id(String message_id) {
			this.message_id = message_id;
		}

		public Append_data getAppend_data() {
			return append_data;
		}

		public void setAppend_data(Append_data append_data) {
			this.append_data = append_data;
		}

		public class Append_data {
			private String pallent;

			public String getPallent() {
				return pallent;
			}

			public void setPallent(String pallent) {
				this.pallent = pallent;
			}

		}

}