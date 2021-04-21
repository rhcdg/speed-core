![Logo](https://github.com/SteampunkFoundry/images/raw/master/steampunk_banner-white_pink_on_grey.jfif)
# speed-forms service

This represents a microservice representing the Forms back-end for the Applicant portal.

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

### Building
Run the following gradle command
```
gradle build
```
or the Gradle Wrapper
```
./gradlew build
```

### Testing
Tests and Test Coverage are included in the build and located at: `build/reports`

### Running locally
1. Start a Mongo Database: git@github.com:Steampunk-SPEED/speed-mongo.git
    1. Starting the database seeds it with sample data. 
    2. From the speed-core parent project: `docker-compose build mongo && docker-compose up mongo`
2. Run the Spring Boot application
    1. From the speed-core parent project: `docker-compose build forms && docker-compose up forms`

-----------

## Maintainability 
The tools used to measure the maintainability of this code are the following:

* CODE REVIEWS: https://github.com/Steampunk-SPEED/speed-forms/pulls?q=is%3Apr+is%3Aclosed
* SONARQUBE METRICS: https://sonarqube.speedc2c24.steampunkfoundry.net/dashboard?id=net.steampunkfoundry.techdemo%3Aforms
* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-forms/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Security 
The tools used to measure the security of this code are the following:

* DEEP-CODE-CI: https://www.deepcode.ai/app/gh/
* SONARQUBE METRICS: https://sonarqube.speedc2c24.steampunkfoundry.net/dashboard?id=net.steampunkfoundry.techdemo%3Aforms
* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-forms/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Scalability 

The tools used to measure the scalability of this code are the following:

* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-forms/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Other Considerations

We descoped the preparer's information, age eligibility and several applicant type journeys in our MVP.  In the future we would like to pull those stories from the backlog.

### Effective Transition for Operational Use of this Repository

This repository serves as the backend forms microservice for the applicant portal. It could be used as a starting place for digitizing the I-131 form by building and deploying in the USCIS openshift cluster. It would require additional work to complete the entire microservice.

-----------

***This repository is considered Steampunk proprietary information. Dissemination or copy of this information without the permission of Steampunk is strictly prohibited.**
