package es.jgpelaez.openshift.sb.zuulserver.config;

import java.util.List;

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

	private List<String> servicesBlacklist;

	private boolean useEurekaServices;

	private String testConfig;

	public boolean isBlacklisted(String serviceId) {
		if (servicesBlacklist != null) {
			for (String bl : servicesBlacklist) {
				if (serviceId.matches(bl)) {
					return true;
				}
			}
		}
		return false;
	}

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
