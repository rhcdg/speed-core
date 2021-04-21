###################################
# COMMON
###################################

build-all:
	docker-compose build postgres mongo api-gateway accounts-svc forms-svc cases-svc adjudicator-web forms-web

up-all:
	docker-compose up -d postgres mongo api-gateway accounts-svc forms-svc cases-svc adjudicator-web forms-web

up-hub:
	docker-compose up -d hub chrome firefox

test:
	$(MAKE) rebuild-hub
	docker-compose scale chrome=5
	docker-compose run --rm functional
	docker-compose stop hub chrome firefox

ps:
	docker-compose ps

stop:
	docker-compose stop

###################################
# DB
###################################

psql:
	docker-compose exec postgres psql -U postgres -h localhost


###################################
# REBUILDING
###################################

rebuild-all:
	$(MAKE) rebuild-postgres
	$(MAKE) rebuild-mongo
	$(MAKE) rebuild-adjudicator-web
	$(MAKE) rebuild-accounts-svc
	$(MAKE) rebuild-forms-svc
	$(MAKE) rebuild-cases-svc
	$(MAKE) rebuild-forms-web
	$(MAKE) rebuild-api-gateway

rebuild-mongo:
	docker-compose stop mongo
	docker-compose rm -f mongo
	docker-compose build mongo
	docker-compose up -d mongo

rebuild-postgres:
	docker-compose stop postgres
	docker-compose rm -f postgres
	docker-compose build postgres
	docker-compose up -d postgres

rebuild-forms-svc:
	docker-compose stop forms-svc
	docker-compose rm -f forms-svc
	docker-compose build forms-svc
	docker-compose up -d forms-svc

rebuild-cases-svc:
	docker-compose stop cases-svc
	docker-compose rm -f cases-svc
	docker-compose build cases-svc
	docker-compose up -d cases-svc

rebuild-adjudicator-web:
	docker-compose stop adjudicator-web
	docker-compose rm -f adjudicator-web
	docker-compose build adjudicator-web
	docker-compose up -d adjudicator-web

rebuild-forms-web:
	docker-compose stop forms-web
	docker-compose rm -f forms-web
	docker-compose build forms-web
	docker-compose up -d forms-web

rebuild-accounts-svc:
	docker-compose stop accounts-svc
	docker-compose rm -f accounts-svc
	docker-compose build accounts-svc
	docker-compose up -d accounts-svc

rebuild-api-gateway:
	docker-compose stop api-gateway
	docker-compose rm -f api-gateway
	docker-compose build api-gateway
	docker-compose up -d api-gateway

rebuild-hub:
	docker-compose stop hub chrome firefox
	docker-compose rm -f hub chrome firefox
	docker-compose up -d hub chrome firefox

rebuild-chrome:
	docker-compose stop chrome
	docker-compose rm -f chrome
	docker-compose up -d chrome

###################################
# GIT
###################################

git-fetch:
	git fetch origin
	git submodule foreach git fetch origin

git-branch:
	git branch
	git submodule foreach git branch

git-branch-all: git-fetch
	git branch -a
	git submodule foreach git branch -a

git-status: git-fetch
	git status
	git submodule foreach git status

git-checkout-develop: git-fetch
	git submodule foreach git fetch origin
	pushd postgres && git checkout develop && popd;
	pushd api-gateway && git checkout develop && popd;
	pushd accounts-svc && git checkout develop && popd;
	pushd forms-svc && git checkout develop && popd;
	pushd cases-svc && git checkout develop && popd;
	pushd adjudicator-web && git checkout develop && popd;
	pushd forms-web && git checkout develop && popd;
	pushd performance-test && git checkout develop && popd;
	pushd test-automation && git checkout develop && popd;
	
git-checkout-master: git-fetch
	git submodule foreach git fetch origin
	pushd postgres && git checkout master && popd;
	pushd api-gateway && git checkout master && popd;
	pushd accounts-svc && git checkout master && popd;
	pushd forms-svc && git checkout master && popd;
	pushd cases-svc && git checkout master && popd;
	pushd adjudicator-web && git checkout master && popd;
	pushd forms-web && git checkout master && popd;
	pushd performance-test && git checkout master && popd;
	pushd test-automation && git checkout master && popd;

git-pull-latest-develop: git-checkout-develop
	git pull origin master;
	pushd postgres && git pull origin && popd;
	pushd api-gateway && git pull origin && popd;
	pushd accounts-svc && git pull origin && popd;
	pushd forms-svc && git pull origin && popd;
	pushd cases-svc && git pull origin && popd;
	pushd adjudicator-web && git pull origin && popd;
	pushd forms-web && git pull origin && popd;
	pushd performance-test && git pull origin && popd;
	pushd test-automation && git pull origin && popd;
	
git-pull-latest-master: git-checkout-master
	git pull origin master;
	pushd postgres && git pull origin && popd;
	pushd api-gateway && git pull origin && popd;
	pushd accounts-svc && git pull origin && popd;
	pushd forms-svc && git pull origin && popd;
	pushd cases-svc && git pull origin && popd;
	pushd adjudicator-web && git pull origin && popd;
	pushd forms-web && git pull origin && popd;
	pushd performance-test && git pull origin && popd;
	pushd test-automation && git pull origin && popd;

git-submodule-update:
	git fetch origin
	git submodule update --init

git-submodule-remote:
	git fetch origin
	git submodule update --remote

git-diff:
	git submodule foreach git --no-pager diff --name-only origin/master

git-log-remote-changes:
	git submodule foreach git --no-pager log origin/master..HEAD --graph

git-log-local-changes:
	git submodule foreach git --no-pager log HEAD..origin/master --graph

git-checkout-master: git-fetch
	git submodule foreach git checkout master

git-merge-master: git-fetch
	git submodule foreach git checkout master
	git submodule foreach git merge origin master
