# README

This application serves as a basic CIS database replacement for testing purposes.

There is swagger documentation that can be accessed at `{localhost}/api-docs/index.html` that explain the api.

The application is a standard rails api with a simple setup. 

## Setup

The application assumes you have ruby version `2.7.2` with bundler installed on your system.

To install all the dependencies and get the software running do the following:

`bundle install`
`rake db:create`
`rake db:migrate`
`rake db:seed`
`rails s`

After that navigate your browser to `localhost:3000/api-docs/index.html` to see the swagger documentation for the API.
