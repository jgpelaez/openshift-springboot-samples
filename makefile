.PHONY: help
.DEFAULT_GOAL := help


export REDEPLOY_OPENSHIFT_TEMPLATE?=false
export APP_NAME?=openshift-sb
export ANSIBLE_PLAYBOOK?=main-openshift-deploy.yml

login-openshift:  ## login openshift-sb environment
	oc login ${OPENSHIFT_URL} -u ${OPENSHIFT_USER}
	oc whoami -t

create-project:  ## Creates openshift project
	oc new-project ${OS_PROJECT} --display-name="${OS_PROJECT}" --description="${OS_PROJECT}"
	oc project ${OS_PROJECT}

add-permissions:  ## add permissions
	oc policy add-role-to-user admin system:serviceaccount:${OS_PROJECT}:default -n ${OS_PROJECT}

set-app:  ## login
	cd devops/ansible; \
		ansible-playbook \
			${ANSIBLE_PLAYBOOK} \
			-e openshift_token=${OPENSHIFT_TOKEN} \
			-e openshift_url=${OPENSHIFT_URL}  \
			-e openshift_project_name=${OS_PROJECT} \
			-e app_name=${APP_NAME} \
			-e app_port=${APP_PORT} \
			-e build_namespace=${BUILD_NAMESPACE} \
			-e build_image=${BUILD_IMAGE} \
			-e eureka_uri=${EUREKA_URI} \
			-e config_server_uri=${CONFIG_SERVER_URI} \
			-e redeploy_openshift_template=${REDEPLOY_OPENSHIFT_TEMPLATE} \
			-e git_source_url=${GIT_SOURCE_URL} \
			-e url_sufix=${URL_SUFIX} \
			-i ./inventory/local
			
set-app-zuul:  ## login
	cd devops/ansible; \
		ansible-playbook \
			app-zuul-server-openshift-deploy.yml \
			-e openshift_token=${OPENSHIFT_TOKEN} \
			-e openshift_url=${OPENSHIFT_URL}  \
			-e openshift_project_name=${OS_PROJECT} \
			-e app_name=${APP_NAME} \
			-e app_port=${APP_PORT} \
			-e build_namespace=${BUILD_NAMESPACE} \
			-e build_image=${BUILD_IMAGE} \
			-e redeploy_openshift_template=${REDEPLOY_OPENSHIFT_TEMPLATE} \
			-e git_source_url=${GIT_SOURCE_URL} \
			-e url_sufix=${URL_SUFIX} \
			-i ./inventory/local

	
help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
	@echo -e "Arguments/env variables: \n \
				"