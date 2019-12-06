package org.example.vo;

public class Options {
	
	String domain;
	String logLevel;
	MQTT mqtt;
	Http http;
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public MQTT getMqtt() {
		return mqtt;
	}
	public void setMqtt(MQTT mqtt) {
		this.mqtt = mqtt;
	}
	public Http getHttp() {
		return http;
	}
	public void setHttp(Http http) {
		this.http = http;
	}

}
