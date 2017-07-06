package es.jgpelaez.openshift.sb.ms.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "configserverdata")
@Data
public class ConfigServerConfig {
	String myconfig;
}
