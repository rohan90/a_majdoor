package com.rohan90.majdoor.api.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class AppDetails {
    private String name;
    private String version;
    private int versionCode;
    private String environment;
    
    
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	@Override
	public String toString() {
		return "AppDetails [name=" + name + ", version=" + version + ", versionCode=" + versionCode + ", environment="
				+ environment + "]";
	}
	
}
