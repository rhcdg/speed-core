#!/bin/sh

set -e

rake db:create 
rake db:migrate 
rake db:seed 

rails s --binding=0.0.0.0