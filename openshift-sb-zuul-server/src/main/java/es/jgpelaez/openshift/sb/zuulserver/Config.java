package es.jgpelaez.openshift.sb.zuulserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zuulserver")
public class Config {

	private Boolean localServices;
	private Integer servicesPort;
	private String servicesPrefix;
	private Boolean useEurekaServices;

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

	public void setLocalServices(Boolean localServices) {
		this.localServices = localServices;
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
