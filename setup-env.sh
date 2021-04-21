#!/bin/bash

# Put the .env variables in the dev's environment

while read l; do
  name=$(cut -d'=' -f1 <<< $l)
  val=$(cut -d'=' -f2 <<< $l)
  if [[ ! -z ${name// } && ! $name == *"#"* ]]; then
    echo "export $name=$val"
    export $name=$val
  fi
done < .env-dev
