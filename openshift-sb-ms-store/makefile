.PHONY: help
.DEFAULT_GOAL := help


export REDEPLOY_OPENSHIFT_TEMPLATE?=false

docker-build:  ## docker build
	docker build -t openshift-sb-ms-store .

docker-run:  ## docker run
	docker run --rm -P --name openshift-sb-ms-store -e "SPRING_CLOUD_KUBERNETES_DISCOVERY_ENABLED=false" openshift-sb-ms-store

help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
	@echo -e "Arguments/env variables: \n \
				"