package org.foxconn.tencent.sendComponent.entity.Config;

public class B2BUrlEntity {
	private String serverIp;
	private String serverPort;
	private String logUrl;
	private String mqUrl;
	
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	public String getMqUrl() {
		return  "http://"+serverIp+":"+serverPort+"/tencent/message";
	}
	public void setMqUrl(String mqUrl) {
		this.mqUrl = mqUrl;
	}
	public String getLogUrl() {
		
		return logUrl="http://"+serverIp+":"+serverPort+"/logs";
	}
	public void setLogUrl(String logUrl) {
		this.logUrl = logUrl;
	}
	
}
