FROM pkosiec/mongo-seeding:latest

COPY ./data /import-data

ARG DB_HOST
ARG DB_NAME
ARG DB_PORT
ARG DROP_DATABASE
ARG REPLACE_ID
ARG DB_USERNAME
ARG DB_PASSWORD

ENV DB_HOST=$DB_HOST
ENV DB_NAME=$DB_NAME
ENV DB_PORT=$DB_PORT
ENV DROP_DATABASE=$DROP_DATABASE
ENV REPLACE_ID=$REPLACE_ID
ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD

WORKDIR /import-data
