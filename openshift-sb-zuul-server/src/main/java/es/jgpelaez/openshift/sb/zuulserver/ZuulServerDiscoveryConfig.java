package es.jgpelaez.openshift.sb.zuulserver;

import java.util.LinkedHashMap;
import java.util.Set;

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

import es.jgpelaez.openshift.sb.zuulserver.config.DynamicZuulConfig;
import es.jgpelaez.openshift.sb.zuulserver.k8s.ServiceDiscovery;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * @author Juan Carlos García Peláez
 *
 */
@Configuration
@Import({ ZuulServerApplication.class })
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
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

			if (!config.getUseEurekaServices()) {
				for (String serviceId : serviceDiscovery.getServices()) {
					String serviceName = serviceId;
					if (config.getRemoveAppPrefix()) {
						serviceName = serviceName.replaceAll(this.config.getAppPrefix(), "");
					}
					String path = "/" + config.getServicesPrefix() + "/" + serviceName + "/**";
					CustomZuulRoute customZuulRoute = new CustomZuulRoute(config, serviceId, serviceName, serviceName,
							path, null, null, true, false, null);
					routes.put(path, customZuulRoute);
				}
			}
			return routes;
		}
	}

	public static class CustomZuulRoute extends ZuulRoute {

		String customServiceId;
		String customServiceName;
		DynamicZuulConfig config;

		public CustomZuulRoute(DynamicZuulConfig config, String customServiceId, String customServiceName, String id,
				String path, String serviceId, String url, boolean stripPrefix, Boolean retryable,
				Set<String> sensitiveHeaders) {
			super(id, path, serviceId, url, stripPrefix, retryable, sensitiveHeaders);
			this.config = config;
			this.customServiceId = customServiceId;
			this.customServiceName = customServiceName;
		}

		public String getCustomServiceId() {
			return customServiceId;
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

		public void setCustomServiceId(String customServiceId) {
			this.customServiceId = customServiceId;
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
