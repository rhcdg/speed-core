![Logo](https://github.com/SteampunkFoundry/images/raw/master/steampunk_banner-white_pink_on_grey.jfif)
# speed-cases service

This represents a microservice representing the Cases back-end for the Case Management portal.

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

### Development Notes
This project uses [Lombok](https://projectlombok.org) annotations, and your IDE must be set up for Lombok or you will get compile errors. Here are instructions for setting up your IDE: [intelliJ](https://projectlombok.org/setup/intellij) ... [Eclipse](https://www.baeldung.com/lombok-ide)

### Building
Requires a number of environment variable to be set. These can be found found in the .env file in the core repository: https://github.com/Steampunk-SPEED/speed-core.

Run the following gradle command
```
gradle build
```
or if you don't have Gradle installed, run the Gradle Wrapper
```
./gradlew build
```
### Testing
Tests and Test Coverage are included in the build and located at: `build/reports`

### Running Locally
1. Start a PostgreSQL Database : https://github.com/Steampunk-SPEED/speed-postgres
2. Run the Spring Boot application:
```
java -jar build/libs/cases-0.0.1-SNAPSHOT.jar
```
### Using this Service Locally
Refer to  [Spring Data REST](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#repository-resources) for more information.

Get all cases:
http://localhost:9016/cases/findAll

Get a single case by ID:
http://localhost:9016/cases/1

Get cases by Adjudicator:
http://localhost:9016/cases/findByAdjudicatorId?adjudicatorId=1

Get cases by Adjudicator and Status:
http://localhost:9016/cases/findByAdjudicatorIdAndStatus?adjudicatorId=1&status=ACCEPTING

Get cases by Status:
http://localhost:9016/cases/findByStatus?status=ACCEPTING

Get cases by Case Number:
http://localhost:9016/cases/findByCaseNumber?caseNumber=1

## Maintainability 
The tools used to measure the maintainability of this code are the following:

* CODE REVIEWS: https://github.com/Steampunk-SPEED/speed-cases/pulls?q=is%3Apr+is%3Aclosed
* SONARQUBE METRICS: https://sonarqube.speedc2c24.steampunkfoundry.net/dashboard?id=net.steampunkfoundry.techdemo%3Acases
* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-cases/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Security 
The tools used to measure the security of this code are the following:

* DEEP-CODE-CI: https://www.deepcode.ai/app/gh/
* SONARQUBE METRICS: https://sonarqube.speedc2c24.steampunkfoundry.net/dashboard?id=net.steampunkfoundry.techdemo%3Acases
* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-cases/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Scalability 

The tools used to measure the scalability of this code are the following:

* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-cases/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Other Considerations

### Effective Transition for Operational Use of this Repository

This repository serves as the backend microservice for case management system and could easily be built and deployed to USCIS openshift cluster.

-----------

***This repository is considered Steampunk proprietary information. Dissemination or copy of this information without the permission of Steampunk is strictly prohibited.**

