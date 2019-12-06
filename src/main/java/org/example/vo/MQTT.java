package org.example.vo;

public class MQTT {
	
	String instanceId;
	String port;
	String transport;
    String cleanStart;
    String sessionExpiry;
    String keepAlive;
    String caFile;
    
    
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getTransport() {
		return transport;
	}
	public void setTransport(String transport) {
		this.transport = transport;
	}
		public String getCleanStart() {
		return cleanStart;
	}
	public void setCleanStart(String cleanStart) {
		this.cleanStart = cleanStart;
	}
	public String getSessionExpiry() {
		return sessionExpiry;
	}
	public void setSessionExpiry(String sessionExpiry) {
		this.sessionExpiry = sessionExpiry;
	}
	public String getKeepAlive() {
		return keepAlive;
	}
	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}
		public String getCaFile() {
		return caFile;
	}
	public void setCaFile(String caFile) {
		this.caFile = caFile;
	}
	

}
