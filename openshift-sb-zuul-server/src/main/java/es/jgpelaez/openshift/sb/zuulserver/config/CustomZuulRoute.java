package es.jgpelaez.openshift.sb.zuulserver.config;

import java.util.Set;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import lombok.Data;

@Data
public class CustomZuulRoute extends ZuulRoute {

	DynamicZuulConfig config;
	String customServiceId;
	String customServiceName;

	public CustomZuulRoute(DynamicZuulConfig config, String customServiceId, String customServiceName, String id,
			String path, String serviceId, String url, boolean stripPrefix, Boolean retryable,
			Set<String> sensitiveHeaders) {
		super(id, path, serviceId, url, stripPrefix, retryable, sensitiveHeaders);
		this.config = config;
		this.customServiceId = customServiceId;
		this.customServiceName = customServiceName;
	}

	@Override
	public String getLocation() {
		int port = config.getServicesPort();
		if (config.isLocalServices()) {

			return "http://localhost:" + port + "/";
		} else {
			return "http://" + customServiceId + ":" + port + "/";
		}

	}

}