# SPEED Tech Demo

## PreReqs
- Docker 1.17+
- Docker-compose 1.15+ (Docker for Mac and Docker for Windows bundles this with the Docker binary)

## Setup
1. Git clone with submodules: `git clone --recurse-submodules -j8 git@github.com:Steampunk-SPEED/speed-core.git`
2. Copy `.env.sample` to a new file named `.env`
3. Change the values contained with `.env` for security

## Build
1. `docker-compose build postgres reservations web` (Make sure all images build successfully)
2. You can view the created images with `docker images`

## Run the application locally
1. Use: `http://localhost:8001`  
**NOTE:** Port 8000 is used for the functional test.

## Deploy services
1. Data tier: `docker-compose up -d postgres`
2. App tier 1: `docker-compose up -d accounts-svc`
3. App tier 2: `docker-compose up -d cases-web`
4. Web tier: `docker-compose up -d adjudicators-web`

## View running services
1. `docker-compose ps`

## Stop/Pause services
1. `docker-compose stop`

## Clean rebuild images
1. `docker-compose stop [service_name]`
2. `docker-compose rm [service_name]`
3. `docker-compose build --no-cache [service_name]`

## Execute Functional Tests
1. `docker-compose up -d hub firefox chrome zap`
2. `docker-compose up -d web`
3. `docker-compose run --rm tests`

## Destroy
- Stop and destroy all containers only: `docker-compose down`
- Stop, destroy all containers and volumes `docker-compose down -v`
- Stop, destroy all containers, volumes, and images: `docker-compose down -v --rmi local`


## Useful Git Submodules commands
- Recursively initialize additional submodules: `git submodule update --init --recursive`
- `git submodule foreach git status` (Shows the status of each submodule)
- `git submodule foreach git fetch origin` (Will fetch and show newer feature branches available)
- `git submodule foreach git branch` (View the currently checked-out branch in each submodule)
- `git submodule foreach git checkout develop` (Switch entire stack to `develop` branch)
- `git submodule foreach git pull origin develop` (Quickly merge and update local `develop` branches in all submodules)
- `git submodule foreach git branch -r` (View all remote branches in all submodules)
