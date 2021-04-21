#!/usr/bin/env bash

CONTEXT=openshift

ENV=$1

if [ "$1" = "pipeline" ]
then
oc project ci
oc process -f ${CONTEXT}/pipeline.yaml  | oc delete -f -
oc process -f ${CONTEXT}/integration.yaml --param-file=${CONTEXT}/integration.env | oc delete -f -
fi

if [ "$1" = "ci" ]
then
oc project ci
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/ci.env | oc delete -f -
fi

if [ "$1" = "test" ]
then
oc project functional
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/functional.env | oc delete -f -

oc project perf
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/perf.env | oc delete -f -
fi


if [ "$1" = "prod" ]
then
oc project prod
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/green.env | oc delete -f -
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/blue.env | oc delete -f -
fi
