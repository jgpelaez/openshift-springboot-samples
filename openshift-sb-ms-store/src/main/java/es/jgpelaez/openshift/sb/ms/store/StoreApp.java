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
package es.jgpelaez.openshift.sb.ms.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jmnarloch.spring.request.correlation.api.EnableRequestCorrelation;
import io.jmnarloch.spring.request.correlation.api.RequestCorrelationInterceptor;

/**
 * Spring configuration class main application bootstrap point.
 * 
 * @author Oliver Gierke
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableScheduling
@EnableRequestCorrelation
public class StoreApp {

	private static final Logger logger = LoggerFactory.getLogger(StoreApp.class);

	@RequestMapping("/")
	@ResponseBody
	String home() {
		logger.info("store Message");
		return "store";
	}

	public static void main(String[] args) {

		SpringApplication.run(StoreApp.class, args);
	}

	@Bean
	public RequestCorrelationInterceptor correlationLoggingInterceptor() {
		return new RequestCorrelationInterceptor() {
			@Override
			public void afterCorrelationIdSet(String correlationId) {
				MDC.put("correlationId", correlationId);
			}

			@Override
			public void cleanUp(String correlationId) {
				MDC.remove("correlationId");
				
			}
		};
	}
}
