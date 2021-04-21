#!/usr/bin/env bash

set -e

if [[ -f "/opt/splunkforwarder/bin/splunk" ]]; then
    /opt/splunkforwarder/bin/splunk start --accept-license
fi

case $1 in
    start)
        TSC_WATCHFILE=UseFsEventsWithFallbackDynamicPolling npm start | cat
        ;;
    *)
        NAMESERVER_IP=$(cat /etc/resolv.conf | grep nameserver | awk '{print $2}');
        # Do a string replace for configuration files for the web root.
        if [[ "${API_GATEWAY_URL}" != "" ]]; then
            sed -i "s#<<API_GATEWAY_URL>>#${API_GATEWAY_URL}#" /etc/nginx/conf.d/default.conf
            sed -i "s#<<NAMESERVER_IP>>#${NAMESERVER_IP}#" /etc/nginx/conf.d/default.conf
        else
            #set a default if one is not present
            API_GATEWAY_URL="api-gateway"
            sed -i "s#<<API_GATEWAY_URL>>#${API_GATEWAY_URL}#" /etc/nginx/conf.d/default.conf
            sed -i "s#<<NAMESERVER_IP>>#${NAMESERVER_IP}#" /etc/nginx/conf.d/default.conf
        fi

        exec "$@"
        ;;
esac