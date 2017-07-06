/*
 * Copyright (C) 2016 to the original authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package es.jgpelaez.openshift.sb.ms.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.jgpelaez.openshift.sb.ms.store.config.AppConfig;
import es.jgpelaez.openshift.sb.ms.store.config.ConfigServerConfig;
import es.jgpelaez.openshift.sb.ms.store.config.MyConfig;
import es.jgpelaez.openshift.sb.ms.store.config.MySecrets;

@Component
@EnableConfigurationProperties
public class MyBean {

	private static final Logger logger = LoggerFactory.getLogger(MyBean.class);

	@Autowired
	Environment environment;
	@Autowired
	private MyConfig config;
	@Autowired
	private MySecrets secrets;
	@Autowired
	private ConfigServerConfig configServerConfig;
	@Autowired
	AppConfig appConfig;

	@Scheduled(fixedDelay = 10000)
	public void hello() {

		logger.info("Message");
		logger.info("The message is: " + config.getMessage());
		logger.info("The UserName is: " + secrets.getUsername());
		logger.info("The UserName is: " + environment.getProperty("secret.username"));
		logger.info("The appConfig.dbUser is: " + appConfig.getDbUser());
		logger.info("The password is: " + secrets.getPassword());

		logger.info("The password is: " + secrets.getPassword());
		logger.info("configServerConfig.getMyconfig: " + configServerConfig.getMyconfig());

	}

}
