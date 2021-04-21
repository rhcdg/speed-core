#!/usr/bin/env bash

CONTEXT=openshift

oc project ci
oc process -f ${CONTEXT}/deploy.yaml --param-file=${CONTEXT}/ci.env | oc apply -f -
