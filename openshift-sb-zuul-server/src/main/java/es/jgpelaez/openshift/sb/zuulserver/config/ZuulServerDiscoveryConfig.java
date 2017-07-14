package es.jgpelaez.openshift.sb.zuulserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
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
