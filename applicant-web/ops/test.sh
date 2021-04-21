#!/usr/bin/env bash

CONTEXT=openshift

oc project functional
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/functional.env | oc apply -f -

oc project perf
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/perf.env | oc apply -f -
