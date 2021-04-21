# Start from a small, trusted base image with the version pinned down
FROM ruby:2.7.2-alpine

# Install system dependencies required both at runtime and build time
# The image uses Postgres but you can swap it with mariadb-dev (for MySQL) or sqlite-dev
# RUN apk add --update --no-cache build-base git nodejs tzdata libxml2-dev libxslt-dev
RUN apk add --update \
  build-base \
  libxml2-dev \
  libxslt-dev \
  sqlite-dev \
  tzdata

WORKDIR /app

COPY . /app

RUN bundle install

COPY ./entrypoint.sh /entrypoint.sh

RUN chmod +x /entrypoint.sh
RUN chown -R 1000 /app
### Containers should NOT run as root as a good practice
# Switch to this user
USER 1000
EXPOSE 3000

ENTRYPOINT [ "/entrypoint.sh"]
# Launch the server (or run some other Ruby command)
# CMD ["rails s"]
