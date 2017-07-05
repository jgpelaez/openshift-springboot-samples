package es.jgpelaez.openshift.sb.zuulserver;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "zuulserver")
public class Config {

	private Boolean useEurekaServices;

	private Boolean localServices;

	private Integer servicesPort;

	public Integer getServicesPort() {
		return servicesPort;
	}

	public void setServicesPort(Integer servicesPort) {
		this.servicesPort = servicesPort;
	}

	public Boolean isLocalServices() {
		return localServices;
	}

	public void setLocalServices(Boolean localServices) {
		this.localServices = localServices;
	}

	public Boolean getUseEurekaServices() {
		return useEurekaServices;
	}

	public void setUseEurekaServices(Boolean useEurekaServices) {
		this.useEurekaServices = useEurekaServices;
	}
}
