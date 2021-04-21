#!/usr/bin/env bash

CONTEXT=openshift

oc project prod
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/green.env | oc apply -f -
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/blue.env | oc apply -f -
