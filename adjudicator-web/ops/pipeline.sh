#!/usr/bin/env bash

CONTEXT=openshift

oc project ci
oc process -f ${CONTEXT}/pipeline.yaml  | oc apply -f -
