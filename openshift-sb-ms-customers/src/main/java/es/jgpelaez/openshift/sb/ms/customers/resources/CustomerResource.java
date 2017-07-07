package es.jgpelaez.openshift.sb.ms.customers.resources;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerResource {
	/*
	@RequestMapping("/")
	@ResponseBody
	String home(@RequestHeader(value = "User-Agent") String userAgent,
			@RequestHeader(name = "Test-Header", required = false) String testHeader) {
		// @RequestHeader("testHeader") String testHeader
		// String testHeader=headers.getFirst("testHeader");
		// String testHeader = "";
		String template = " [Header: Test-Header=%s]";
		StringBuffer message = new StringBuffer("customer");
		if (testHeader != null) {
			message.append(String.format(template, testHeader));
		}

		return message.toString();
	}
	*/

	@RequestMapping("/api/test")
	@ResponseBody
	String apiTest() {

		return "customers/api/test";
	}

}
