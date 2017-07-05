package es.jgpelaez.openshift.sb.zuulserver.k8s;

import java.util.List;
import java.util.stream.Collectors;

import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * @author Juan Carlos García Peláez
 *
 */
public class ServiceDiscovery {

	KubernetesClient client;

	public ServiceDiscovery(KubernetesClient client) {
		super();
		this.client = client;
	}

	public List<String> getServices() {
		return client.services().list().getItems().stream().map(s -> s.getMetadata().getName())
				.collect(Collectors.toList());
	}

}