#!/usr/bin/env bash

CONTEXT=openshift

oc project ci
oc process -f ${CONTEXT}/pipeline.yaml  | oc apply -f -
oc process -f ${CONTEXT}/integration.yaml --param-file=${CONTEXT}/integration.env | oc apply -f -
