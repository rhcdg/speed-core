#! /bin/bash
./wait-for-it.sh ${POSTGRES_ENDPOINT}:${POSTGRES_PORT} -t 15
java -Djava.security.egd=file:/dev/./urandom -jar app.jar
