package es.jgpelaez.openshift.sb.ms.store.config.resource;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import es.jgpelaez.openshift.sb.ms.store.StoreApp;
import es.jgpelaez.openshift.sb.ms.store.model.Store;

@RequestMapping("/api/stores")
@Component
public class StoreResource {
	private static final Logger logger = LoggerFactory.getLogger(StoreApp.class);

	@RequestMapping
	@ResponseBody
	List<Store> getStores() {
		List<Store> stores = new ArrayList<Store>();
		stores.add(new Store("store1"));

		return stores;
	}

}
