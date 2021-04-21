#! /bin/bash
./wait-for-it.sh mongo:27017 -t 15
java -Djava.security.egd=file:/dev/./urandom -jar app.jar
