package es.jgpelaez.openshift.sb.zuulserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import com.github.mthizo247.cloud.netflix.zuul.web.socket.EnableZuulWebSocket;

@Configuration
@EnableZuulWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfiguration {

}
