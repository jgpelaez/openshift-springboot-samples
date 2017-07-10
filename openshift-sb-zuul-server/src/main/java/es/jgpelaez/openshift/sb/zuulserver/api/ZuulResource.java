package es.jgpelaez.openshift.sb.zuulserver.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/api/zuul")
@Component
public class ZuulResource {
	private static final Logger logger = LoggerFactory.getLogger(ZuulResource.class);

	@RequestMapping("test")
	@ResponseBody
	String getTEst() {

		return "test";
	}

}
