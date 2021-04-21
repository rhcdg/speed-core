FROM gradle:6.5-jdk11 as build
ARG SPEED_REPO_URL
ARG SPEED_REPO_USER
ARG SPEED_REPO_PASSWORD
ENV SPEED_REPO_URL=$SPEED_REPO_URL
ENV SPEED_REPO_USER=$SPEED_REPO_USER
ENV SPEED_REPO_PASSWORD=$SPEED_REPO_PASSWORD
USER root
RUN mkdir -p /app
WORKDIR /app
COPY . /app/
RUN gradle build --no-daemon --console plain


FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/build/libs/cases-0.0.1-SNAPSHOT.jar /app/app.jar
COPY --from=build /app/start.sh /app/start.sh
COPY --from=build /app/wait-for-it.sh /app/wait-for-it.sh
RUN cat /app/start.sh
RUN sh -c 'touch /app.jar'
VOLUME ["/app"]

ENTRYPOINT ["./start.sh"]
