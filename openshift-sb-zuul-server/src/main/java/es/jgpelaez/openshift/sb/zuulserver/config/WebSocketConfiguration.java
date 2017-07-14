package es.jgpelaez.openshift.sb.zuulserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import com.github.mthizo247.cloud.netflix.zuul.web.socket.EnableZuulWebSocket;
import com.github.mthizo247.cloud.netflix.zuul.web.socket.ZuulPropertiesResolver;
import com.github.mthizo247.cloud.netflix.zuul.web.socket.ZuulWebSocketProperties;

@Configuration
@EnableZuulWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfiguration {
	@Autowired
	RouteLocator routeLocator;
	@Autowired
	DynamicZuulConfig config;

	@Bean
	@Primary
	public ZuulPropertiesResolver zuulPropertiesResolver(final ZuulProperties zuulProperties) {
		return new ZuulPropertiesResolver() {
			@Override
			public String getRouteHost(ZuulWebSocketProperties.WsBrokerage wsBrokerage) {
				for (Route route : routeLocator.getRoutes()) {
					if (wsBrokerage.getId().equals(route.getId())) {
						CustomZuulRoute newRoute = new CustomZuulRoute(config, route.getId(), route.getId(),
								route.getId(), null, route.getId(), null, false, false, null);
						return newRoute.getLocation();
					}
				}
				return zuulProperties.getRoutes().get(wsBrokerage.getId()).getUrl();
			}
		};
	}
}
