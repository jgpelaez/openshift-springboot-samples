# Zuul Server

Run this app as a normal Spring Boot app. If you run from this project 
it will be on port 8765 (per the `application.yml`). Also run [eureka](https://github.com/spring-cloud-samples/eureka) and the
[stores](https://github.com/spring-cloud-samples/customers-stores/tree/master/rest-microservices-store) 
and [customers](https://github.com/spring-cloud-samples/customers-stores/tree/master/rest-microservices-customers) 
samples from the [customer-stores](https://github.com/spring-cloud-samples/customers-stores) 
sample.  

You should then be able to view json content from 
`http://localhost:8765/stores` and `http://localhost:8765/customers` which are
configured in `application.yml` as proxy routes.

## Hystrix Fallback Support
This sample also includes an example of how to configure Hystrix fallbacks for routes in Zuul.
If you hit `http://localhost:8765/self/timeout` you can see the fallback functionality in action.

## Configuration

The dynamic zuul can be configured in the application.yml (or the config server):

```
dynamiczuul:
# we can disable the kubernetes service discovery and use Eureka
  useEurekaServices: false
# For local development
  localServices: false
# Default services port( by now all services should be in the same port)
  servicesPort: ${SERVICES_PORT:9000}
# Prefix for the url, for example: http://localhost:8765/services/openshift-sb-ms-store
  servicesPrefix: services
# If we want the url's to remove a prefix, for example for using http://localhost:8765/services/ms-store
  removeAppPrefix: false   
  appPrefix: openshift-sb-
# Custom routes, if we want to use a different name for a service, for example to use a blue green deployment
# can be specified here
  customRoutes:
    lg-ms-customersblue:
        serviceId: lg-ms-customers
# Service black list, all services matching with the regexp servicesBlackList expressions will be banned
# http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
  servicesBlacklist:
    - postgresql
    - broker.*
```