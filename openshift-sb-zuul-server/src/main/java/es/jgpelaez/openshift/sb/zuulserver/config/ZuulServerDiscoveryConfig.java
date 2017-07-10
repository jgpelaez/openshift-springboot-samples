package es.jgpelaez.openshift.sb.zuulserver.config;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

import es.jgpelaez.openshift.sb.zuulserver.ZuulServerApplication;
import es.jgpelaez.openshift.sb.zuulserver.k8s.ServiceDiscovery;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Carlos García Peláez
 *
 */
@Configuration
@Import({ ZuulServerApplication.class })
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Slf4j
public class ZuulServerDiscoveryConfig {

	public static class CustomDiscoveryClientRouteLocator extends DiscoveryClientRouteLocator {

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
					String serviceName = serviceId;
					String customServiceId = config.getServiceId(serviceId);
					if (config.isRemoveAppPrefix()) {
						serviceName = serviceName.replaceAll(this.config.getAppPrefix(), "");
					}
					String path = "/" + config.getServicesPrefix() + "/" + serviceName + "/**";
					CustomZuulRoute customZuulRoute = new CustomZuulRoute(config, customServiceId, serviceName, serviceName,
							path, null, null, true, false, null);
					routes.put(path, customZuulRoute);
				}
			}
			return routes;
		}
	}

	@Autowired
	private DynamicZuulConfig config;

	@Autowired
	private DiscoveryClient discovery;

	@Autowired
	protected ServerProperties server;

	@Autowired
	protected ServiceDiscovery serviceDiscovery;

	@Autowired
	private ServiceRouteMapper serviceRouteMapper;

	@Autowired
	protected ZuulProperties zuulProperties;

	@Bean
	@ConditionalOnMissingBean
	public ServiceDiscovery discoveryService(KubernetesClient client) {

		return new ServiceDiscovery(client);
	}

	@Bean
	public DiscoveryClientRouteLocator routeLocator() {
		return new CustomDiscoveryClientRouteLocator(this.serviceDiscovery, this.config, this.server.getServletPrefix(),
				this.discovery, this.zuulProperties, this.serviceRouteMapper);
	}

}
