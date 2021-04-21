FROM node:14-alpine AS build

ARG authentication_token
ARG api_gateway_host
ARG cognito_client_id=274gmvg18iu03cdlifii4udnf2
ARG cognito_signing_key_kid_1=Ks6kqhffCRJq1ac6PGNfNeNYDnw5MvzJHYJ5OSiFJD0=
ARG cognito_signing_key_n_1=x07uKQ8TJ84NPa3zvz_y2GOTwwV6FUoQOLTO3pwQYo3BlKO3B9RfG7cNqM9yufx_FUZ4p9izBj-J8mM3weRdeTPRXLXCNuAzbrxn0t0bl-tOE5lXkXEHhJouOQSOjxhHrrzFxVpfn9EhCgkxwQ2jEmPpYtfhVOIhQ_AdjHIkaZZLSnHq48zWCnDsCfaI3w8ITxMnTjgiI7UFldfX-UQUhhlhIi2vxybW_sKJ2YzNlTYm2AgqDGrGk0DRI_uRPthsnqK-gg7dChlDSbS7mKst1amOubrDbEfChGAPwTei7UxUuafJ1vt_G978rao0WJgtjumkeKYz9k111dg2XPgTFQ
ARG cognito_signing_key_kid_2=e1SV8Os8ZnRVOzXlfQ7Vk3lY5Xih9ozqFQyFODwptP0=
ARG cognito_signing_key_n_2=nV5d_P9wR9W4WOAnz8hkUUZ9zLaPNyj8GlqUxPR8HS6wV4o5pNMrlSxjWc5TgfXw-lM6OBbRuYf7IVbA_GFfbWyGQfU_10EczVWd4FhQzptCLYnzFVEYMueC3SN66TYBdDiI1ACsS7Q5sHFKwi01QokrV9J_qjSnzko_gEKveOjaw4RpGlQRoGKTrwmWtaG6b3CxnWvLp3OZ77Aff1QdKkynKRgbL_LEmr3M8GmMCEjbBH1jxCSXQGcuDKLC1vESBy7F8rHUFjdPWnDuJZ_4sklUYCQ1dESGmSl2wEmHZL6tp3XedQmdDvpHALFs3FJLwW11_Mp_xLqbMRoSTW9vwQ
ARG analytics_measurement_id=UA-192995177-2

ENV REACT_APP_COGNITO_CLIENT_ID=$cognito_client_id
ENV REACT_APP_COGNITO_SIGNING_KEY_KID_1=$cognito_signing_key_kid_1
ENV REACT_APP_COGNITO_SIGNING_KEY_N_1=$cognito_signing_key_n_1
ENV REACT_APP_COGNITO_SIGNING_KEY_KID_2=$cognito_signing_key_kid_2
ENV REACT_APP_COGNITO_SIGNING_KEY_N_2=$cognito_signing_key_n_2
ENV REACT_APP_ANALYTICS_MEASUREMENT_ID=$analytics_measurement_id

WORKDIR /app

# copy package.json first and run install to use cached intermediate containers for faster build
COPY package.json /app/
RUN npm install

# copy rest of app
COPY config-overrides.js /app/
COPY tsconfig.json /app/
COPY public /app/public
COPY src /app/src

ARG webenv=ci

ENV INLINE_RUNTIME_CHUNK=false
RUN npm run build:$webenv -- --prod

### Build webserver with application
FROM nginx:latest

ARG splunk_forwarder_password=St3ampunk
ARG splunk_hostname=splunk-s1-standalone-headless.splunk-operator.svc
ARG splunk_port=9997

RUN apt-get update && apt-get upgrade -y
RUN apt-get install wget -y

# Install Splunk forwarder
RUN wget -O splunkforwarder-8.1.3-63079c59e632-linux-2.6-amd64.deb 'https://www.splunk.com/bin/splunk/DownloadActivityServlet?architecture=x86_64&platform=linux&version=8.1.3&product=universalforwarder&filename=splunkforwarder-8.1.3-63079c59e632-linux-2.6-amd64.deb&wget=true'
RUN dpkg -i splunkforwarder-8.1.3-63079c59e632-linux-2.6-amd64.deb

COPY ./splunk/inputs.conf ./splunk/outputs.conf ./splunk/user-seed.conf /opt/splunkforwarder/etc/system/local/
RUN echo "PASSWORD = ${splunk_forwarder_password}" >> /opt/splunkforwarder/etc/system/local/user-seed.conf
RUN sed -i "s/__splunk_hostname/${splunk_hostname}:${splunk_port}/g" /opt/splunkforwarder/etc/system/local/outputs.conf

COPY --from=build /app/build/ /usr/share/nginx/html
COPY ./nginx-cluster.conf /etc/nginx/conf.d/default.conf
# Need nginx to not go into a demaemon.
RUN echo "\ndaemon off;" >> /etc/nginx/nginx.conf
RUN chown -R nginx /usr/share/nginx/html
RUN chown -R 755 /usr/share/nginx/html

# Entrypoint for rewriting.
COPY ./entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Containers should NOT run as root as a good practice
USER 0

# Overwrite old configs, then start php-entrypoint. followed by starting apache2 in the foreground
ENTRYPOINT [ "/entrypoint.sh"]
CMD ["nginx"]