/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.jgpelaez.openshift.sb.ms.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Oliver Gierke
 */
@SpringBootApplication
// @EnableCircuitBreaker
@EnableDiscoveryClient
@RestController
public class CustomerApp {
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
	@RequestMapping("/api/test")
	@ResponseBody
	String apiTest() {
		

		return "customers/api/test";
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerApp.class, args);
	}

}
