This is a collection of examples for using Spring Boot in OpenShift 3.

# Installation

There is a makefile for the installation. We can easy doing the installation following the steps:

```
make login-openshift OPENSHIFT_URL=${OPENSHIFT_URL} OPENSHIFT_USER=${OPENSHIFT_USER}
```

From this command you'll obtain a TOKEN.

- Project creation:

```
export OS_PROJECT=<PROJECT_NAME>
make make create-project OS_PROJECT=${OS_PROJECT}
```

- Add permissions

Some of the running pods need access to the Kubernetes api, we add permissions to the default user.

```
make add-permissions OS_PROJECT=${OS_PROJECT}
```

- Set the application

This command will set all the setup in the project, creating deployment configs, routes, builds, etc.

```
	make set-app OS_PROJECT=${OS_PROJECT} \
		OPENSHIFT_URL=${OPENSHIFT_URL} \
		OPENSHIFT_TOKEN=${OPENSHIFT_TOKEN} \
		REDEPLOY_OPENSHIFT_TEMPLATE=${REDEPLOY_OPENSHIFT_TEMPLATE} \		
		GIT_SOURCE_URL=${GIT_SOURCE_URL}
```

Params:

- REDEPLOY_OPENSHIFT_TEMPLATE: if it is true will delete every component and will recreate it.
- GIT_SOURCE_URL: For example this git repo (https://github.com/jgpelaez/openshift-springboot-samples.git) 

# Example components:

All the example components use the same templates stored in ./devops/openshift/templates 

The installation of the components is done with ansible, with the playbook ./devops/ansible/app-openshift-deploy.yml
 
## Config server

The project openshift-sb-config-server is an spring boot server. It is configure to use a local file in ./src/main/resources/configserver.yml 

- Access: 

http://<FQDN>/configserver/default

## Service Discovery (Eureka)

Eureka service discovery 

- Access: 

http://<FQDN>/

## Example microservices

### Example microservice customers

Is a simple spring boot microservice.

- Access: 

http://<FQDN>/api/test

### Example microservice store

Is a simple spring boot microservice.

In this sample we check some features:

#### Obtain configuration from OpenShift ConfigMaps

First we need to add to the pom.xml 

```
<dependency>
	<groupId>io.fabric8</groupId>
	<artifactId>spring-cloud-kubernetes-core</artifactId>
	<version>${spring-cloud-starter-kubernetes.version}</version>
</dependency>
<dependency>
	<groupId>com.squareup.okhttp3</groupId>
	<artifactId>okhttp</artifactId>
	<version>${okhttp.version}</version>
</dependency>
```

This will activate the automatic configuration from a ConfigMap and Secrets, both had be called with the same name of the deployment config. 
Creation example:

```
apiVersion: v1
kind: Template
labels:
  template: app-springboot-cm-template
metadata:
  name: app-springboot-cm
objects: 
- apiVersion: v1
  kind: ConfigMap
  metadata:
    name: ${APP_NAME}-${SERVICE_NAME}
  data:
    application.properties: |-
      bean.message=Hello World!
      another.property=value
parameters:
- name: SERVICE_NAME
  value: eureka  
- name: APP_NAME
  value: openshift-sb
```

```
apiVersion: v1
kind: Template
labels:
  template: app-springboot-cm-template
metadata:
  name: app-springboot-cm
objects: 
- apiVersion: v1
  kind: Secret
  metadata:
    name: ${APP_NAME}-${SERVICE_NAME}
  type: Opaque
  data:
    secret.username: bXl1c2VyCg==
    secret.password: bXlwYXNzd29yZA==

parameters:
- name: SERVICE_NAME
  value: eureka  
- name: APP_NAME
  value: openshift-sb
```

Then we need to add the configuration to the bootstrap.yml
```
spring:
  application:
    name: <service_name>
  cloud:
    config:
      uri: ${CONFIG_SERVER_URI:http://localhost:8888/configserver/}
    kubernetes:
      discovery:
        enabled: ${SPRING_CLOUD_KUBERNETES_DISCOVERY_ENABLED:true}
      secrets:
        enabled: ${SPRING_CLOUD_KUBERNETES_DISCOVERY_ENABLED:true}
        enableApi: ${SPRING_CLOUD_KUBERNETES_DISCOVERY_ENABLED:true}
      reload:
        enabled: ${SPRING_CLOUD_KUBERNETES_DISCOVERY_ENABLED:true}
```

!!! This configuration should be added in the bootstrap, it doesn't work in the application.yml or in the config server (that has been checked)

Finally we can read the properties in the applicacion.yml:

```
store:
  dbUser: ${secret.username:test}
```

Or in the code:

```
@Configuration
@ConfigurationProperties(prefix = "secret")
public class MySecrets {
	private String password;
	private String username;

	public String getPassword() {
		return password;
	}
```

- Access: 

http://<FQDN>/api/stores

# Zuul services gateway


## Testing it


http://<FQDN>/services/openshift-sb-ms-store/api/stores

