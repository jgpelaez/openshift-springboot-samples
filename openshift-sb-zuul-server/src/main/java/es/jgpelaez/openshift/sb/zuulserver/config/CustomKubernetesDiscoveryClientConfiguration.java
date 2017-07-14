package es.jgpelaez.openshift.sb.zuulserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.spring.cloud.discovery.KubernetesDiscoveryClient;
import io.fabric8.spring.cloud.discovery.KubernetesDiscoveryClientConfiguration;
import io.fabric8.spring.cloud.discovery.KubernetesDiscoveryProperties;

@Configuration
public class CustomKubernetesDiscoveryClientConfiguration extends KubernetesDiscoveryClientConfiguration {

	@Bean
	@Primary
	public KubernetesDiscoveryClient kubernetesDiscoveryClient(KubernetesClient client,
			KubernetesDiscoveryProperties properties) {
		return new KubernetesDiscoveryClient(client, properties);
	}

}
