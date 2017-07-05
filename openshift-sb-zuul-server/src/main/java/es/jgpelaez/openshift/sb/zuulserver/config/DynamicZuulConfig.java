package es.jgpelaez.openshift.sb.zuulserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dynamiczuul")
public class DynamicZuulConfig {

	private String appPrefix;
	private Boolean localServices;
	private Boolean removeAppPrefix;
	private Integer servicesPort;
	private String servicesPrefix;

	private Boolean useEurekaServices;

	public String getAppPrefix() {
		return appPrefix;
	}

	public Boolean getLocalServices() {
		return localServices;
	}

	public Boolean getRemoveAppPrefix() {
		return removeAppPrefix;
	}

	public Integer getServicesPort() {
		return servicesPort;
	}

	public String getServicesPrefix() {
		return servicesPrefix;
	}

	public Boolean getUseEurekaServices() {
		return useEurekaServices;
	}

	public Boolean isLocalServices() {
		return localServices;
	}

	public void setAppPrefix(String appPrefix) {
		this.appPrefix = appPrefix;
	}

	public void setLocalServices(Boolean localServices) {
		this.localServices = localServices;
	}

	public void setRemoveAppPrefix(Boolean removeAppPrefix) {
		this.removeAppPrefix = removeAppPrefix;
	}

	public void setServicesPort(Integer servicesPort) {
		this.servicesPort = servicesPort;
	}

	public void setServicesPrefix(String servicesPrefix) {
		this.servicesPrefix = servicesPrefix;
	}

	public void setUseEurekaServices(Boolean useEurekaServices) {
		this.useEurekaServices = useEurekaServices;
	}
}
