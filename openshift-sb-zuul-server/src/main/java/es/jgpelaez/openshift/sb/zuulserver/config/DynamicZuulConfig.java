package es.jgpelaez.openshift.sb.zuulserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "dynamiczuul")
@Data
public class DynamicZuulConfig {

	@Autowired
	Environment env;
	private String appPrefix;
	private boolean localServices;
	private boolean removeAppPrefix;
	private Integer servicesPort;
	private String servicesPrefix;

	private boolean useEurekaServices;

	private String testConfig;

	public boolean isUseEurekaServices() {
		if ("false".equals(env.getProperty("spring.cloud.kubernetes.discovery.enabled"))) {
			return true;
		}

		return useEurekaServices;
	}

	public String getServiceId(String serviceId) {
		String propId = "dynamiczuul.customRoutes." + serviceId + ".serviceId";
		if (env.getProperty(propId) != null) {
			return env.getProperty(propId);
		}
		return serviceId;
	}
}
