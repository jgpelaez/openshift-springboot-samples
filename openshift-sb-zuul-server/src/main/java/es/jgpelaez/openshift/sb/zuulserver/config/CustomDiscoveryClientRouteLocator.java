package es.jgpelaez.openshift.sb.zuulserver.config;

import java.util.LinkedHashMap;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;

import es.jgpelaez.openshift.sb.zuulserver.k8s.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomDiscoveryClientRouteLocator extends DiscoveryClientRouteLocator {

	DynamicZuulConfig config;
	ServiceDiscovery serviceDiscovery;

	public CustomDiscoveryClientRouteLocator(ServiceDiscovery serviceDiscovery, DynamicZuulConfig config,
			String servletPath, DiscoveryClient discovery, ZuulProperties properties,
			ServiceRouteMapper serviceRouteMapper) {
		super(servletPath, discovery, properties, serviceRouteMapper);
		this.serviceDiscovery = serviceDiscovery;
		this.config = config;
	}

	@Override
	protected LinkedHashMap<String, ZuulRoute> locateRoutes() {
		LinkedHashMap<String, ZuulRoute> routes = super.locateRoutes();
		log.debug("locate routes");
		log.debug("testconfig: " + config.getTestConfig());
		if (!config.isUseEurekaServices()) {
			for (String serviceId : serviceDiscovery.getServices()) {
				if (config.isBlacklisted(serviceId)) {
					log.info(serviceId + " is blacklisted, will be discarded from Zuul");
				} else {
					String serviceName = config.getServiceId(serviceId);
					String customServiceId = serviceId;
					if (config.isRemoveAppPrefix()) {
						serviceName = serviceName.replaceAll(this.config.getAppPrefix(), "");
					}
					String path = "/" + config.getServicesPrefix() + "/" + serviceName + "/**";
					CustomZuulRoute customZuulRoute = new CustomZuulRoute(config, customServiceId, serviceName,
							serviceName, path, null, null, true, false, null);
					if (routes.get(path) != null) {
						routes.remove(path);
					}
					routes.put(path, customZuulRoute);
				}
			}
		}
		return routes;
	}
}