![Logo](https://github.com/SteampunkFoundry/images/raw/master/steampunk_banner-white_pink_on_grey.jfif)
# td-mongo

This repository initiates a mongo database for use in the SPEED tech challenge.

## Solution Description

The I-131 Application System is comprised of several smaller applications built in a microservice architecture to demonstrate the full lifecycle of a I-131 travel document benefit from the digital application, through adjudication (including business rules and security rules), printing correspondence, and reporting and dashboarding to better understand the system health and metrics on the business processes around adjudication (such as processing time or submission and failure rates).

![Alt](https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/solution_overview.PNG "Solution Overview")

| Application | Use Case | Personas Mapped | Domains Addressed |
------ | ------- | -------- | ---------
| Applicant Portal | An interactive, responsive, web-based digital form that captures accurate I-131 data for different applicant types (re-entry, refugee, advanced parolee) and collects payment information that can be sent to a case adjudicator.  | Applicant | Intake, Account, Correspondence, Payment  |
| Case Management Portal | An interactive, responsive, web-based case management system that allows an adjudicator to 1) Accept an I-131 case 2) Be assigned an I-131 case 3) Validate an I-131 case against a set of business rules and security checks 4) Reject or Approve a case based on the business rules and security checks. | Adjudicator |  Case Management, Account, Correspondence |
| Security Check Application | A microservice that will take the case and its associated form data and perform a series of security checks on the applicant. | Adjudicator | Case Management, Security Services |
| Client Print Application | A desktop client that an internal USCIS adjudicator can use to output the I-131 case data to a local printer, including the applicant's Receipt and Fee Acceptance correspondence. | Adjudicator |  Case Management |
| Reporting and Dashboarding Portal | An interactive, responsive, web-based reporting and dashboarding application that an USCIS I-131 business owner can use to perform business intelligence operations, such as viewing page and workflow analytics, user metrics, case approvals and rejections, processing times, and the ability to create their own ad-hoc reporting capabilities. | Business Admin | Analytics, Infrastucture Services |
| Correspondence App | A microservice that will generate PDF correspondence to attach to cases, mail to applicants and to be printed and mailed.  | Adjudicator | Correspondence |
| External API | Using Swagger, an API inspector tool that allows for the external system user to query data sources that back the front end web application and the database supporting each microservice. | External System User | Case Management |

For more information on our solution for the I-131 Application System, please view
the solution details [here](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/SOLUTION.md).

-----------

## Instructions
All Team Steampunk Code Challenge [team members](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/TEAM-STEAMPUNK.md)
are expected to contribute to this repository through the use of pull requests from 
feature branches. 

As much as possible we utilize a **GitOps** approach to storing everything in Git to 
drive maximum collaboration and transparency between our team, our customer, and other
people who may collaborate or benefit from the collective knowledge and experience 
stored in Git.


## Using this Repository

## Dockerized
Spins up Mongo database, seeded with sample data

1. `docker-compose build mongo`
1. `docker-compose up mongo`

###
Expects these env variables to be set:
1. MONGO_INITDB_DATABASE
1. MONGO_INITDB_ROOT_PASSWORD
1. MONGO_INITDB_ROOT_USERNAME

### Useful commands


**docker container ls**

Yields listed of containers... look for 'mongo' under the 'NAMES' column.
```
CONTAINER ID  IMAGE COMMAND  CREATED STATUS  PORTS  NAMES
c85103e7d7fe  td-core_mongo "docker-entrypoint.s…" 27 minutes ago  Up 27 minutes 0.0.0.0:27017->27017/tcp td-core_mongo_1
```

**docker exec -it td-core_mongo_1 bash**

Yields a bash prompt in the container...
```
$ docker exec -it td-core_mongo_1 bash
   root@c85103e7d7fe:/#
```
**mongo --host mongo -u username -p password**

Log into database from command line
```
root@c85103e7d7fe:/# mongo --host mongo -u mongo -p password
MongoDB shell version v4.4.0
connecting to: mongodb://mongo:27017/?compressors=disabled&gssapiServiceName=mongodb
Implicit session: session { "id" : UUID("b82b3b94-364b-4437-b581-d91681400498") }
MongoDB server version: 4.4.0
Welcome to the MongoDB shell.
For interactive help, type "help".
For more comprehensive documentation, see
https://docs.mongodb.com/

Questions? Try the MongoDB Developer Community Forums
https://community.mongodb.com

The server generated these startup warnings when booting:
2020-08-13T14:45:09.045+00:00: Using the XFS filesystem is strongly recommended with the WiredTiger storage engine. See http://dochub.mongodb.org/core/prodnotes-filesystem

Enable MongoDB's free cloud-based monitoring service, which will then receive and display 
metrics about your deployment (disk utilization, CPU, operation statistics, etc).

The monitoring data will be available on a MongoDB website with a unique URL accessible to you
and anyone you share the URL with. MongoDB may use this information to make product
improvements and to suggest MongoDB products and deployment options to you.

To enable free monitoring, run the following command: db.enableFreeMonitoring()
To permanently disable this reminder, run the following command: db.disableFreeMonitoring()
>
```

##
These commands aren't useful until the database is seeding. These assume 'movie' data has been loaded.

| **use movie**        | switches to the 'movie' database                            |
|----------------------|-------------------------------------------------------------|
| **`show collections`** | shows any collections (aka tables) in the movie database... |
| **`db.movie.count()`** | shows # of records in the movie collection/table            |
| **`db.movie.find()`**  | shows records (or a subset) in the movie collection/table   |
| **`exit`**             | Logs out of database                                        |

-----------

## Maintainability 
The tools used to measure the maintainability of this code are the following:

* CODE REVIEWS: https://github.com/Steampunk-SPEED/speed-mongo/pulls?q=is%3Apr+is%3Aclosed

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Security 
The tools used to measure the security of this code are the following:

* DEEP-CODE-CI: https://www.deepcode.ai/app/gh/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Scalability 

This tool is primarily used for local development

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Other Considerations

### Effective Transition for Operational Use of this Repository

This container could easily be deployed to any USCIS openshift cluster.

-----------

***This repository is considered Steampunk proprietary information. Dissemination or copy of this information without the permission of Steampunk is strictly prohibited.**

