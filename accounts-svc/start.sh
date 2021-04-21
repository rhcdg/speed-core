#! /bin/bash
./wait-for-it.sh ${USERS_POSTGRES_ENDPOINT}:${USERS_POSTGRES_PORT} -t 15
java -Djava.security.egd=file:/dev/./urandom -jar app.jar
