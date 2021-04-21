![Alt](https://github.com/SteampunkFoundry/images/raw/master/steampunk_banner-white_pink_on_grey.jfif "Steampunk Logo")

# Repository Description

This repository contains all delivery artifacts related to the [Team Steampunk](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/TEAM-STEAMPUNK.md) SPEED Code Challenge [technical solution](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/SOLUTION.md), [development guides](#instructions), [testing and security best practices](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/QUALITY.md), [agile process](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/AGILE.md) and integration of [user centered design](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/UCD.md) (UCD).

This repository serves as the authoritative source of documentation for [Team Steampunk's](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/TEAM-STEAMPUNK.md) processes, deliverables, and artifacts for the overall solution. In addition to serving as a guide for how to build effective markdown documentation for our solution, it provides a document repository of templates that
allow for more rapid, consistent development and use of quality UCD artifacts, which are employed throughout the software development
process to create a positive and consistent overall user experience.

## Table of Contents

* [Solution Description](#solution-description)
* [Instructions](#instructions)
  * [Setting Up Your Local Development Environment](#setting-up-your-local-development-environment)
  * [Testing the Applications](#testing-the-applications)
  * [Deploying the Applications](#deploying-the-applications)
  * [Accessing the Application and Other Services](#accessing-the-applications-and-other-services)
* [Maintainability](#maintainability)
* [Security Tools](#security-tools)
* [Scalability](#scalability)
* [Other Considerations](#other-considerations)
* [User-Centric Design Principles](#user-centric-design-principles)
* [DevSecOps, CI/CD Pipeline, Testing](#devsecops-cicd-pipeline-testing)
* [Reporting and Dashboarding](#reporting-and-dashboarding)
* [Benefits of Our Approach](#benefits-of-our-approach)
* [Effective Transition of Operational Use](#effective-transition-for-operational-use)
-----------

# Solution Description

This document provides a comprehensive architectural overview of the system and illustrates and describes several architectural views to depict different aspects of the system. It is intended to capture and convey the significant architectural decisions that have been made in our development of the I-131 Application System.

The I-131 Application System is comprised of several smaller applications built in a microservice architecture to demonstrate the full lifecycle of an I-131 travel document benefit from the digital application, through ajudication (including business rules and security rules), printing the fee correspondence, and reporting and dashboarding to better understand the system health and to report relevant metrics on the business processes around adjudication (such as processing time or submission and failure rates). 

![Alt](https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/solution_overview.PNG "Solution Overview")

<h2 align="center"><a href="https://github.com/Steampunk-SPEED/GettingStarted/blob/master/RTM.md">REQUIREMENTS TRACEABILITY MATRIX</a></h2><br>

| Application | Use Case | Personas Mapped | Domains Addressed |
------ | ------- | -------- | ---------
| Applicant Portal | An interactive, responsive, web-based digital form that captures accurate I-131 data for different applicant types (re-entry, refugee, advanced parolee) and collects payment information that can be sent to a case adjudicator.  | Applicant | Intake, Account, Correspondence, Payment  |
| Case Management Portal | An interactive, responsive, web-based case management system that allows an adjudicator to 1) Accept an I-131 case 2) Be assigned an I-131 case 3) Validate an I-131 case against a set of business rules and security checks 4) Reject or Approve a case based on the business rules and security checks. | Adjudicator |  Case Management, Account, Correspondence |
| Security Check Application | A microservice that will take the case and its associated form data and perform a series of security checks on the applicant. | Adjudicator | Case Management, Security Services |
| Client Print Application | A desktop client that an internal USCIS adjudicator can use to output the I-131 case data to a local printer, including the applicant's Receipt and Fee Acceptance correspondence. | Adjudicator |  Case Management |
| Reporting and Dashboarding Portal | An interactive, responsive, web-based reporting and dashboarding application that an USCIS I-131 business owner can use to perform business intelligence operations, such as viewing pages and workflow analytics, user metrics, case approvals and rejections, processing times, and the ability to create their own ad-hoc reporting capabilities. | Business Admin | Analytics, Infrastucture Services |
| Correspondence App | A microservice that will generate PDF correspondence to attach to cases, mail to applicants and to be printed and mailed.  | Adjudicator | Correspondence |
| External API | Using Swagger, an API inspector tool that allows for the external system user to query data sources that back the front end web application and the database supporting each microservice. | External System User | Case Management |

For more information on our solution for the I-131 Application System, please view
the solution details [here](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/SOLUTION.md).

### Microservices

Team Steampunk's I-131 Application System is made up of over 15 microservices, shown below.  For architectural understanding we have included a microservice architecture diagram and mapped each microservice to USCIS product domains, as well as provided UML diagrams depicting standard data flow within each app.

![Alt](https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/microservice_arch.PNG "Microservice Architecture")

For more information on our solution for the I-131 Application System, please view
the solution details [here](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/SOLUTION.md#microservices).

### Mock Databases

After analyzing the sample data provided in the USCIS API (Attachment 3) and comparing the data against the proto-personas and our own understanding of the I-131 use cases, we determined that the proto-personas were not in the mock data set and there was not a clear connection of any specific mock applicant to an I-131 form. The team automated the cis-data-api pipeline using the provided sqllite3 database. We recognized two immediated use cases to make effective use of the supplied Government Data to integrate into our solution.

1. **Add proto-persona data to the mock data set.** This allowed us to represent the actual proto-personas of Tomas Traveler and Ritu Ritarn developed by the government in our actual application, since both of these personas filed other forms with their packet (e.g. Tomas submitted a Green Card packet (Forms I-485, I-130, and I-765) in addintion to his I-131). We included additional test cases in the mock data set to establish a sufficent number of cases to allow us to succesfully test the system.

2. **A-Number validation security checks are an important component of case management and could be done using the mock data set.** Using AWS Lambda serverless components, we could create serverless security checks in the case management system by comparing submitted form data against existing data in the mock data set such as the applicant's A Number and name.

### API

We are only exposing API endpoints in our QA environments as our endpoints require role based authorization. Our endpoints are secured based on the user roles needed to access the API data. The backend databases for these API endpoints use either AWS' Postgres RDS or MongoDB, two databases that are in heavy use within the USCIS enterprise.

**NOTE: we have only tested the APIs using the Google Chrome browser. Please bring up the Google Chrome Developer Tools for all APIs**
1. Once you are in Google Chrome, Go to the View Menu Item, then the Developer Menu Item and then Open your Google Chrome Developer Tools
2. Within your Developer Tools, click on the Network tab

#### To login to the **Case Controller (cases-svc)** API, please do the following:
1. Go to the [Case Managment Portal (Case Management Solution)](https://speed-adjudicator-web-qa.speedc2c24.steampunkfoundry.net)
2. Open your Google Chrome Developer Tools per the instructions above
3. Click the **Accept and Login** button after the terms and conditions
4. Login with user id **adjudicator@test.com** and password **P@55word**
5. In your Google Chome Developer Tools Network tab, in the **Name** column, find the Network **token**
6. Click on **token**
7. Copy the **access_token** value(if you right click, there is a Copy Value option). (Ex: access_token: "fxKraWQiOiJlMVNWOE9zOFpuUlZPelhsZlE3VmszbFk1WGloOW96cUZReUZPRHdwdFAwPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI2NjEzZDBiMi1hYTYwLTQ3OGItOGRkNC01NTk2NmYwN2UwODUiLCJjb2duaXRvOmdyb3VwcyI6WyJBZGp1ZGljYXRvciJdLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0yLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMl9DRFJoM2ZrYzAiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiIyNzRnbXZnMThpdTAzY2RsaWZpaTR1ZG5mMiIsImV2ZW50X2lkIjoiZWRjZTlhMmMtMjk1Ni00MWU4LTlhZDctNWRkZjljY2QyYWY4IiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJvcGVuaWQiLCJhdXRoX3RpbWUiOjE2MTczNjcyMTEsImV4cCI6MTYxNzM3MDgxMSwiaWF0IjoxNjE3MzY3MjExLCJqdGkiOiI5NTU4ZTFkZS00NTRlLTQ5MmYtODVmNy1mMDlmNmU4NjdmMjkiLCJ1c2VybmFtZSI6IjY2MTNkMGIyLWFhNjAtNDc4Yi04ZGQ0LTU1OTY2ZjA3ZTA4NSJ9.BAb6DiQWSH5lacxvBpOFDF0QWjidfrCSjv7ELNPYNsfZ959hQtAnb1vSSrPFNq6Oi7VY-Ko2r0xXwfEdg9DMvFTUeLVVpZERrUfF8oQI-quDyiZSUzlrJ6KLY2FZgPnEuvYSMfE8yDAveYSHXoMeScV6KKlODcykr3ygAxr84gVE8ptF2r0mP9tGbpOLZomnziAXBw23ZIsq_K-p759y4trjAnrn6i-lPqOyhHF3cEg6zIJqKp_v1OQV1fqOeUkkMkYpRkM6Xb94GKUzGNqKQrgkDRRTnIvUhv_cYP7wTRTrnxT0gTudvT-1xBkDrGWX7ox0HLWmGcT2O3A45ewNxQ")
8. In a new tab, navigate to the [Case Controller Swagger inspector](https://speed-cases-svc-qa.speedc2c24.steampunkfoundry.net/swagger-ui.html)
9. Click on the green Authorize button
10. In the value field type **"bearer \<your access token\>"** (bearer,space,paste your token value)
11. Click the Authorize button
12. Click the Close button
13. Expand the **case-controller** to see the endpoints for use

You can now navigate to the **Case Controller** and run any of the underlying APIs. We suggest first running [/cases/findAll](https://speed-adjudicator-web-qa.speedc2c24.steampunkfoundry.net/service1/swagger-ui.html#/case-controller/findAllUsingGET) to see all of the **cases** in the QA cases table.

#### USPS API
The team created an API for our I-131 Applicant portal that allows for the address field to be validated. To login to the **forms-svc** where this is done (it would be best to do this in a new browser session with your cache cleared), please do the following:

1. Go to the [Applicant Portal (I-131 Digital Form)](https://speed-applicant-web-qa.speedc2c24.steampunkfoundry.net/)
2. Open your Google Chrome Developer Tools per the instructions above
3. Click the **Accept and Login** button after the terms and conditions
4. Login with user id **applicant@test.com** and password **P@55word**
5. In your Google Chome Developer Tools Network tab, in the **Name** column, find the Network **token**
6. Click on **token**
7. Copy the **access_token** value(if you right click, there is a Copy Value option). (Ex: access_token: "fxKraWQiOiJlMVNWOE9zOFpuUlZPelhsZlE3VmszbFk1WGloOW96cUZReUZPRHdwdFAwPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiI2NjEzZDBiMi1hYTYwLTQ3OGItOGRkNC01NTk2NmYwN2UwODUiLCJjb2duaXRvOmdyb3VwcyI6WyJBZGp1ZGljYXRvciJdLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0yLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMl9DRFJoM2ZrYzAiLCJ2ZXJzaW9uIjoyLCJjbGllbnRfaWQiOiIyNzRnbXZnMThpdTAzY2RsaWZpaTR1ZG5mMiIsImV2ZW50X2lkIjoiZWRjZTlhMmMtMjk1Ni00MWU4LTlhZDctNWRkZjljY2QyYWY4IiwidG9rZW5fdXNlIjoiYWNjZXNzIiwic2NvcGUiOiJvcGVuaWQiLCJhdXRoX3RpbWUiOjE2MTczNjcyMTEsImV4cCI6MTYxNzM3MDgxMSwiaWF0IjoxNjE3MzY3MjExLCJqdGkiOiI5NTU4ZTFkZS00NTRlLTQ5MmYtODVmNy1mMDlmNmU4NjdmMjkiLCJ1c2VybmFtZSI6IjY2MTNkMGIyLWFhNjAtNDc4Yi04ZGQ0LTU1OTY2ZjA3ZTA4NSJ9.BAb6DiQWSH5lacxvBpOFDF0QWjidfrCSjv7ELNPYNsfZ959hQtAnb1vSSrPFNq6Oi7VY-Ko2r0xXwfEdg9DMvFTUeLVVpZERrUfF8oQI-quDyiZSUzlrJ6KLY2FZgPnEuvYSMfE8yDAveYSHXoMeScV6KKlODcykr3ygAxr84gVE8ptF2r0mP9tGbpOLZomnziAXBw23ZIsq_K-p759y4trjAnrn6i-lPqOyhHF3cEg6zIJqKp_v1OQV1fqOeUkkMkYpRkM6Xb94GKUzGNqKQrgkDRRTnIvUhv_cYP7wTRTrnxT0gTudvT-1xBkDrGWX7ox0HLWmGcT2O3A45ewNxQ")
8. In a new tab, navigate to the [Address Validation Controller Swagger inspector](https://speed-forms-svc-qa.speedc2c24.steampunkfoundry.net/swagger-ui.html)
9. Click on the green Authorize button
10. In the value field type **"bearer \<your access token\>"** (bearer,space,paste your token value)
11. Click the Authorize button
12. Click the Close button
13. Expand the **Address Validation** section to see the end points for use

You can now navigate to the **Address Validation Conroller** and run any of the underlying APIs.

### Query Instructions

For each API, there are query instructions within our Swagger inspector. As mentioned in the API section, the backend databases for these API endpoints use either AWS' Postgres RDS or MongoDB, two databases that are in heavy use within the USCIS enterprise. To see the query instructions for the **cases-svc**, follow the instructions in the [API](#api) section above. The query instructions for each API will then be accessible at [case-controller Swagger inspector](https://speed-adjudicator-web-qa.speedc2c24.steampunkfoundry.net/service1/swagger-ui.html).

The query instructions for the **USPS API** can be found at [Address Validation Controller Swagger inspector](https://speed-applicant-web-qa.speedc2c24.steampunkfoundry.net/documents/swagger-ui.html) after following the instructions for the [USPS API](#api)

### Authentication and Authorization

[AWS Cognito](https://aws.amazon.com/cognito/) provides and authentication and authorization service for all applications in the *I-131 Application System.* It is a serverless, cloud-native solution that provides users with authentication providers in Cognito. “Identity Pools” are used. This federated authentication mechanism provides system Access via multiple external authentication providers, such as Facebook, Amazon, Google, OpenID connect providers and SAML Identity providers (such as ICAM), if desired in the future. In order to successfully authenticate a user, AWS Cognito needs an Identity Pool and provides an token.

Using Cognito and a corresponding accounts microservice (to map additional account details) the corresponding web service provides the approriate roles and priviledges based on the user's role in the Cognito user pool.

### User Roles and Priveledges
| User Role | Priviledges |
------------| ------------
| applicant | Applicant Portal |
| adjudicator | Case Management Portal<br>Client Print App |
| admin | Reporting Portal |

For more information on our solution for the I-131 Application System, please view
the solution details [here](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/SOLUTION.md#authentication-and-authorization).

-----------

# Instructions

The following subsections provide reference information for installing various software, accessing code for development and troubleshooting, testing, standing up and running the Code Challenge Application System.  All Team Steampunk Code Challenge [team members](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/TEAM-STEAMPUNK.md#teaming-structure) are expected to contribute to this repository through the use of pull requests (PR) from feature branches.

As much as possible, we utilize a **[GitOps](#gitops-)** approach to storing everything in Git to drive maximum collaboration and transparency between our team, our customer, and other people who may collaborate or benefit from the collective knowledge and experience stored in Git.

## Setting up Your Local Development Environment

### Installing Software

Be sure to install the following software before accessing source code:

Tool | URL
----- | -----
Docker Desktop | https://www.docker.com/products/docker-desktop
Git Bash | https://git-scm.com/downloads
Postgresql | https://www.postgresql.org/download/

### Accessing Source Code

Github Projects to clone: [git@github.com:Steampunk-SPEED/speed-core.git](https://github.com/Steampunk-SPEED/speed-core)

Paste the following command below (replacing your own email address) into Git Bash
or a terminal window.
```
$ ssh-keygen -t rsa -b 4096 -C "[YOUR EMAIL]"
```

Continue to press enter for every prompt until your SHA key has been created.

Paste the following command below into Git Bash or a terminal window and copy the full
SSH key to your clipboard for the next step.
```
$ cat ~/.ssh/id_rsa.pub
```

Add your SSH Key to your GitHub account by following [these instructions](https://docs.github.com/en/enterprise/2.15/user/articles/adding-a-new-ssh-key-to-your-github-account).


In your Git Bash or terminal window, navigate to the directory you want to download
the application code to.
```
$ cd [DESIRED APPLICATION LOCATION]
```


Next, clone the application from GitHub. If prompted, accept the authenticity of the host by typing `yes`.
```
$ git clone --recurse-submodules -j8 git@github.com:Steampunk-SPEED/speed-core.git
```


Finally, get the code on the correct branch (in this case we are using `develop`) and pull the latest code to make sure it is all up to date.
```
$ cd speed-core

$ git submodule foreach git checkout develop

$ git submodule foreach git pull
```

### Standing up the Web Applications

First, we need to set up some environment variables for our application.
```
$ cp .env.sample .env

$ cp .env.sample .env-dev

$ ./setup-env.sh
```

Depending on your system, the environment variables might or might not get properly set.
To be certain, copy the block of lines outputted by the above script starting with
`export` and paste those into your terminal and hit enter.

Finally, we can build and stand up our application.
```
$ docker-compose build

$ docker-compose up -d adjudicator-web applicant-web reports-web accounts-svc cases-svc forms-svc
```

Once those commands finish, your application will be up and running locally.

You can access the adjudicator site at http://localhost:8000, the applicant site at http://localhost:8001, and the reports site at http://localhost:8002. You can login with credentials [provided below](#product-access).

### Standup up the Client Print Application

In order to setup up the Client Print Application, you need to download and install the application to your Windows machine. Each new release of the Client Print Application is automatically published to Sonatype Nexus, and available for download. A list of each release version can be found in Nexus, and also at the [Github Client Print Application repository](https://github.com/Steampunk-SPEED/speed-print-client/releases).

Download the .zip file from Nexus, by specifying the version from a browser, for example: https://nexus-ci.speedc2c24.steampunkfoundry.net/repository/maven-releases/net/steampunkfoundry/techdemo/client/print-client/Prod-1.0.0.20/print-client-Prod-1.0.0.20.zip will download the release version 1.0.0.20. Credentials for the Adjudicator Role in Nexus can be [found below](#product-access). Once downloaded, unzip the folder, and double-click the `run.bat` file to launch. Note that Java Run Time 11 (JRE 11) must be installed on the machine.

## Testing the Applications

### Testing the Web Applications

To execute functional tests, you need gradle and a browser installed.
You need to be in the `test-automation` directory (via the `cd test-automation` command), and then `gradle itest` will run the tests locally (on localhost) on Chrome, in parallel, two tests at once.
```
$ gradle itest
```

The execution of these tests is configurable, as paramters can be provided either from
the `gradle.properties` file, or from the command line. If using the `gradle.properties`
file, simply update the values you desire from the provided ones. From the commandline,
you'll want to pass these in as profile parameters, using the gradle `-P` notation.

#### Parameters
To change the browser, provide the parameter `browser`. To change how many
threads provide the parameter `threads`. By default the tests run aginast the
docker-compose instance of the app stood up on localhost:8000, localhost:8001,
and localhost:8002; adjudicator, applicant, and reports, respectively.
To change that, provide a different site parameters with `adjudicatorSite`,
`applicantSite`, and `reportsSite`. For example:
```
$ gradle itest -Pbrowser=firefox
```

If you want to run your tests against out QA environment, you can change the site, as so
```
$ gradle itest -Pthreads=5 -PadjudicatorSite=https://td-adjudicator-web-qa.speedc2c24.steampunkfoundry.net/ -PapplicantSite=https://td-applicant-web-qa.speedc2c24.steampunkfoundry.net/ -PreportsSite=https://td-reports-web-qa.speedc2c24.steampunkfoundry.net/
```

If you want to run a subset of the tests, you can provide tags to run using the `tags`
parameter. Be sure to include the `@` with your tags. Any supported cucumber logic is
supported, including `and`, `or`, and `not`. For example:
```
$ gradle itest -Ptags=@smoke

$ gradle itest -Ptags='@smoke and not @login'
```

More information about additional parameters, running remotely, and running not functional tests can be found in the [Test Automation README](https://github.com/Steampunk-SPEED/speed-automation#readme)

### Testing the Client Application

To test the client application, launch the `run.bat` file as [specified above](#standup-up-the-client-print-application). Alternatively, a version of the appliction pointing to Staging can be download by selecting the `Stage` version instead of `Prod`. Login as an admin with the [credentials below](#product-access). A list of documents will be displayed, which can be sorted by several options, with the option to logout and print. As long as printer drivers are installed, and printer is hooked up to your machine, clicking the `Print` button should print the selected document.

## Deploying the Applications

### Deploying the Applications through the CI/CD Pipeline

Our application deployment is managed via Jenkins, which is tied to GitHub,
and it follows our [expanded GitFlow pattern](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/CICD.md#gitflow-and-container-promotion).
Each PR will build the application, and each time we merge the PR into develop, that
container gets promoted to our QA environment. Because each container is a separate
microservice, our QA environment is updated piece by piece with each new merge to
develop. Our pipeline waits for our testers to indicate a pass or fail, after which
the code is either promoted to Staging for further tests, or it is rejected, and the old
version of the microservice is redeployed back into QA. This manual trigger allows
us to promote multiple containers into QA at once for different microservices, so that
non-backwards compatible breaking changes can be fully integrated and tested, which is
a significant benefit of our approach.

Our application goes through a multitude of quality checks throughout the pipeline,
both automated and manual, as outlined in our [Quality documentation](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/QUALITY.md).
Using these checks and manual triggers allows us to make informed decisions about
when and how to promote our containers.

Staging has several automated and manual triggers to pass, and once they all pass,
that container gets promoted to Production. Following the pattern for merging PRs
to get compatible containers into QA (and similar to Staging), we can coordinate
deploying containers into production if needed.

## Accessing the Applications and Other Services

* Credentials for services are included in the GettingStarted README, as the only
  RFP-compliant method to share those credentials for the code challenge. We do not
  recommend storing credentials in source control as a practice.
* Some of our REACT components have transient security vulnerabilities. We made a
  design decision to include those components and provided remediations in the
  back-end services that would prevent exploitation. These vulnerabilities might still be
  identified as HIGH/CRITICAL "false positives."

:warning: **IMPORTANT** :warning:
**NOTE:** Due to the requirements of the tech challenge, we had to provide user
access to systems by providing a username/password in this repository. This is not
a standard practice we would perform as it breaks security best practices.

#### Communication and Collaboration

Tool | URL | Username | Password
----- | ----- | ----- | -----
Slack | https://uscisspeedtec-vwm5036.slack.com/ |  github_bot@steampunk.com | St3ampunk!!

#### Design and Project Planning

Tool | URL | Username | Password
----- | ----- | ----- | -----
JIRA | https://steampunkfoundry.atlassian.net/secure/RapidBoard.jspa | github_bot@steampunk.com | St3ampunk!!
Design Resources | https://github.com/Steampunk-SPEED/GettingStarted/tree/master/ucd |  |

#### DevSecOps Pipeline

Tool | URL | Username | Password
----- | ----- | ----- | -----
Jenkins | https://jenkins-ci.speedc2c24.steampunkfoundry.net/ | admin<br />viewer | St3ampunk<br />Steampunk!
SonarQube | https://sonarqube.speedc2c24.steampunkfoundry.net/projects | evaluator | St3ampunk
Nexus | https://nexus-ci.speedc2c24.steampunkfoundry.net/ | admin | St3ampunk
Openshift Console | https://speedc2c24.steampunkfoundry.net:8443/ | admin<br />viewer | St3ampunk<br />Steampunk!

#### AWS Cloud Services

This allows read-only access to our AWS infrastucture for viewing the SPEED CC cloud environment.

Tool | URL | Username | Password
----- | ----- | ----- | -----
AWS Console |https://steampunkfoundry.signin.aws.amazon.com/console | viewer | #97{il}Vn'0tGTq

#### Splunk 

Log monitoring of our applications

Tool | URL | Username | Password
----- | ----- | ----- | -----
AWS Console | https://splunk.speedc2c24.steampunkfoundry.net/en-US/account/login?return_to=%2Fen-US%2F | admin | npbuJtttagy3SEbd2vA4Tk73 
#### Product Access

##### Production (PROD)

Application | URL | Username | Password
----- | ----- | ----- | -----
Applicant Portal (I-131 Digital Form) | https://speed-applicant-web-prod.speedc2c24.steampunkfoundry.net/ | applicant@test.com | P@55word
Case Management Portal (Case Management Solution) |  https://speed-adjudicator-web-prod.speedc2c24.steampunkfoundry.net/ | adjudicator@test.com | P@55word
Client Print Application (Client Application) | https://nexus-ci.speedc2c24.steampunkfoundry.net/repository/maven-releases/net/steampunkfoundry/techdemo/client/print-client/Prod-1.0.0.19/print-client-Prod-1.0.0.19.zip | adjudicator@test.com | P@55word
Reports Portal (Reporting and Dashboarding) | https://speed-reports-web-prod.speedc2c24.steampunkfoundry.net/ | admin@test.com | P@55word


##### Staging (STAGE)

Application | URL | Username | Password
----- | ----- | ----- | -----
Applicant Portal (I-131 Digital Form) | https://speed-applicant-web-stage.speedc2c24.steampunkfoundry.net/ | applicant@test.com | P@55word
Case Management Portal (Case Management Solution) | https://speed-adjudicator-web-stage.speedc2c24.steampunkfoundry.net/ | adjudicator@test.com | P@55word
Reports Portal (Reporting and Dashboarding) | https://speed-reports-web-stage.speedc2c24.steampunkfoundry.net/ | admin@test.com | P@55word


##### Quality (QA)

Application | URL | Username | Password
----- | ----- | ----- | -----
Applicant Portal (I-131 Digital Form) |https://speed-applicant-web-qa.speedc2c24.steampunkfoundry.net/ | applicant@test.com | P@55word
Case Management Portal (Case Management Solution) | https://speed-adjudicator-web-qa.speedc2c24.steampunkfoundry.net/| adjudicator@test.com | P@55word
Reports Portal (Reporting and Dashboarding) | https://speed-reports-web-qa.speedc2c24.steampunkfoundry.net/ | admin@test.com | P@55word

-----------

# Maintainability

<p align="left">
<img src="https://playbook.cio.gov/assets/images/usds-logo-seal.png" align="middle" title="USDS Logo" width=50 height =50>
<strong>Applying <a href="https://playbook.cio.gov/#play10">Play 10</a>, <a href="https://playbook.cio.gov/#play11">Play 11</a>, and <a href="https://playbook.cio.gov/#play12">Play  12</a></strong>
</p>

Team Steampunk places a high focus on quality, at all levels of the application. Application quality is more important in the DevSecOps methodology than before, as we need to ensure we understand we are repeatably releasing our application with a high known quality, as opposed to just releasing rapidly.

To this effect, Team Steampunk takes a Continuous Testing approach, testing at all levels of application development, and throughout the entire release cycle. This includes relying heavily on a shift left approach, to get testing performed early and often, and building out feedback cycles throughout the process to provide information to the entire team on the quality of the application at each stage.

By collecting information from testing done throughout the entire development and release process, our team can build a quality profile for each release candidate. This means that when it comes time to release the software, we can make quantitative and qualitative assessments of each release candidate, and decide what to release. This becomes extremely important within the DevSecOps processes, to ensure that we are releasing high quality software rapidly. These quality standards are integrated with Github to ensure quality is a key component of **every pull request.**

Our Quality Profile incorporates many areas, including:

* Unit Test Results
* Branch Code Coverage
* Issues from Static Code Analysis
* Technical Debt
* Integration Test Results
* API Test Results
* Functional Test Results
* User Story/Feature Coverage
* Security Test Results
* Performance Test Results
* Data Migration Compatibility

Lastly, it is our belief that a well documented system ensures a system is more maintainable over its lifetime, so we have ensured that all system documentation is up to date with features and provides sufficent details to laymen, as well as developers through our GitOps process.

#### [Click here for more detailed information](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/QUALITY.md)

### Tooling

Tools used to measure quality and maintainability of the code include:

* SonarQube
* Selenium
* Jacoco
* SauceLabs
* Postman

### Metrics

We leverage a variety of measures and metrics in comprehensive dashboards to determine and enforce quality through our CI/CD Pipeline.

* JENKINS QUALITY GATES: https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/
* SONARQUBE DASHBOARD: https://sonarqube.speedc2c24.steampunkfoundry.net/projects
* DEEPCODE-CI:https://www.deepcode.ai/app/gh/

**A full listing of these measures is defined in the [pipeline section](#our-pipeline) of this README.**

-----------

# Security Tools

<p align="left">
<img src="https://playbook.cio.gov/assets/images/usds-logo-seal.png" align="middle" title="USDS Logo" width=50 height =50>
<strong>Applying <a href="https://playbook.cio.gov/#play11">Play 11</a></strong>
</p>
Steampunk believes that security is paramount when dealing with our clients and ensures that the most up-to-date practices are incorporated and designed from each project’s inception and through the design, development and deployment phases. We strongly commit to the incorporation of security within code reviews, security testing, binary analysis, SCA, and security remediation processes. In an effort to reduce the attack surface of applications and their infrastructure, we provide a variety of security testing throughout the CI/CD pipeline. As much as feasible, we shift security left and attempt to (where appropriate) run security testing as early as possible to detect and resolve the vulnerabilities while they are being developed.

![Alt](https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/security_tools.PNG "Security Tools")

### Tooling

#### Binary Analysis

Binary analysis is used to determine the origins of all components and libraries that are utilized within the software to identify any vulnerabilities that are found in popular and commonly used components. These open-source components and libraries are compared to a list of recently updated and documented vulnerabilities that will advise any potential issues or out of date warnings and if any patches are available. We leverage [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/) to identify vulnerable libraries at compile time, allowing us to make early changes or patch vulnerable libraries with the "next release" or as a hotfix.

#### SCA

SCA tools are run on compiled code to detect defects such as input validation, pointers and references, numerical errors and path traversals that might be exploited by potential vulnerabilities. The reason for implementing a binary analysis includes discovering vulnerabilities through decomposition and disassembly of the binary in order to recognize vulnerability patterns. Discovering these weaknesses furthers the commitment to security that is expected from our high standards. We leverage security plugins within [SonarQube](https://www.sonarqube.org/) to identify vulnerabilities in our software. In addition, we have leveraged plugins within Git to provide quick feedback to our developers at the point they are leveraging pull requests (such as [deepcode-ci-bot](https://github.com/apps/deepcode-ci-bot)).

#### Container Scanning

We utilize [Anchore](https://anchore.com/) to perform security scanning on each of our containers as a gated part of our CI/CD pipeline.  This allows us to quickly generate a list of known vulnerabilities that exist within a container image and provide rapid feedback to a developer to being remediation without exposing any of our test environments or production.

#### Security Remediation Process

Critical and high defects discovered in the development process should be identified and resolved prior to release. Defects originating from architecture decisions or those involving zero-day defects should be triaged and added as a bug and prioritized with user stories for remediation.


### Metrics

We leverage a variety of measures and metrics in comprehensive dashboards to determine and enforce quality through our CI/CD Pipeline.

* JENKINS QUALITY GATES: https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/  
* SONARQUBE DASHBOARD: https://sonarqube.speedc2c24.steampunkfoundry.net/projects  
* DEEPCODE-CI:https://www.deepcode.ai/app/gh/

**A full listing of these measures is defined in the [pipeline section](#our-pipeline) of this README.**

-----------

# Scalability

Our team takes our functional and non-functional testing beyond simple feature and performance testing, and focuses on reliability of the application. This means examining up-times, failover capabilities, and disaster recovery metrics. We use modeling, measurements from our quality analysis and improvement benchmarks to determine our reliability. To achieve this, our team follows the standard procedure of:

- Establishing reliability goals
- Developing am (or many) operational profile(s)
- Planning and executing tests
- Using the test results to drive decisions

We follow the three segment approach of:

- Model
- Measure
- Improve

For modeling, we have experts on staff to help build both prediction and estimation models, taking into account data reference, development cycle timelines, and application time frames. For measuring, our teams look at four key metrics: product (such as size, complexity, and coverage), project management (such as key Agile metrics), process, and fault and failure (such as MTTF and MTTR). For improvement, we roll our plans into our backlog, prioritizing them as needed and pulling them into sprints as potential technical debt.

### Tooling
Tools used to measure scalability include:

* Jmeter

### Metrics

JENKINS QUALITY GATES: https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/

**A full listing of these measures is defined in the [pipeline section](#our-pipeline) of this README.**

-----------

# Other Considerations

## KNOWN BUG

**1) Please type in valid date fields in the Applicant Portal. Invalid dates will cause database validation issues when submitted to the Case Management Portal.**

## Assumptions

1. Responsive Web v. Mobile-Friendly: Our web applications were built to be interactive, responsive, and web-based. For the Application Portal, we designed it using a Mobile First approach since this was an external application that would be used by applicants worldwide, on any device. This is why the form is broken out into sections that can easily be submitted using a mobile device. The Case Management Portal, Client Print Application, and Reports Portal were built with a desktop in mind as this would be an internal applicaiton used by USCIS adjudicatiors.
2. Demonstrating in MVP: We employed our Design Intelligence process when analyzing the digitizing of the I-131 form and building the Case Management portal for adjudication. In a compressed time period for the coding challenge, the team had to carefully prioritize the backlog to consider what parts of the user journey could be built ensuring code quality, sound security practices, and a comprehensive workflow of the system. We built our MVP using the Tomas Traveler persona as his user journey best represented a way for us to complete a comprehensive user journey from form submission->receipt of his fee payment->case creation, assignment, validation, and approval->receipt of his approval notice. Our DI process is repeatable, and our next iteration to add onto this MVP would be to add Ritu Ritarn given more sprints.
3. Client Application for Printing: The team built the client application for printing using Java FX. The desktop application authenticates an adjudicator or admin and allows them to see a list of cases that can be selected to be printed and allows for the printing of the PDF that was generated for the Receipt-Fee Acceptance. For our MVP, we were able to successfully able to print to a local HP Officejet Pro X451dn printer to validate the requirements.
4. User Personas: While the team built an MVP for Tomas Traveler, we created user personas for Casey Manager, the USCIS adjudicator, and Norm Burr-Cruncher, a USCIS I-131 business owner. Casey's journey drove the backlog for our Case Management Portal and Client Print Application. Norm's journey drove the backlog for the Reports/Dashboard portal. These personas can be found in <<INSERT URL>>.
5. Mock Database Use Cases: The team was able to take the Attachment 3 - USCIS API and create an automated pipeline to create a mock database. This database is the backbone for the Security Checks microservice application we built. We use the mock database to run two security checks, the first being a validation that the A Number exists, and the second being that the First and Last name match the applicant. We have created APIs for our queries that can be accessed through our Swagger urls.
6. No Registration Built: We purposely descoped registration of users from the MVP to focus on the highest value components of the problem statement. We recognize intake would be an important addition for
7. No IE Testing - Our compatibility and interoperability testing includes major browsers (Chrome, Firefox, and Edge) latest version aon the most common operating systems (Windows 10, Windows 8.1, WIndows 8, Windows 7, Mac OS Mojave, Mac OS High Sierra). IE and Safari and not included in this testing set due to their low usage, however may be included in future builds if our business intelligence metrics indicate a non insignificant usage by our end users.
8. Security Vulnerability Triage - We evaluated each potential security vulnerability identified in our security testing for exploitability.  We did mark some as false positive in SonarQube which would align with standard development practices.

Other considerations are listed in our JIRA backlog.

-----------

# User-Centric Design Principles

We are a design-led team bringing the next evolution of UCD and DevSecOps Delivery to the Federal Government. At the core of what we do is Design Intelligence, our integrated approach to problem solving that puts USCIS stakeholders at the center of everything. Design Intelligence integrates Agile, DevSecOps, Cyber Security, Data Exploitation, and Human Centered Design in a way that allows those techniques and practices to achieve more together than any could do on their own...working
collaboratively from the beginning through the end. Our focus on Design Intelligence is supported by our corporate investment in building, operating, and
maintaining our dedicated Design Intelligence studio. In addition to our physical space, we have virtual Design Intelligence workspaces, technologies and tools that enable and support our designers, developers, clients, and stakeholders to meet to build integrated solutions that can be applied within the constraints of our client environments. This virtual Design Intelligence workspace is where Team Steampunk has developed our design artifacts and collaborated with our development and engineering team for the I-131 Application System.

### Design Intelligence

Design Intelligence (DI) puts our users at the core of all our activities and enables each member of our teams to proactively and continually engage critical
stakeholders to build better products. We incorporate design in everything we do, *even this markdown!*

<p align="left">
<img src="https://playbook.cio.gov/assets/images/usds-logo-seal.png" align="middle" title="USDS Logo" width=50 height =50>
<strong>Applying <a href="https://playbook.cio.gov/#play1">Play 1</a>, <a href="https://playbook.cio.gov/#play2">Play 2</a>, and <a href="https://playbook.cio.gov/#play3">Play 3</a></strong>
</p>

Steampunk’s CMMI Level 3 appraised **Design Intelligence** (DI) framework integrates our Agile process management, UCD methods, and DevSecOps capabilities into a tightly orchestrated approach for delivering all requirements within the Performance Work Statement. It puts the customer at the center of all our activities and enables our teams to engage critical stakeholders proactively and continually across data, security, infrastructure, and mission organizations. We apply our DI principles – Appreciate, Innovate, Iterate, Activate — to collaborate virtually and in person with the stakeholder and users who must be included in the challenge definition and co-creating solutions as we assess functions for potential innovation. While human-centered design is not a new capability, DI is a one-of-a-kind integrated capability that is distinctive to Steampunk. It's cental to all of our engagements and is being used to deliver Agile programs for the Department of Defense (DoD), CBP, DHS, United States Agency for International Development (USAID), and United States Department of Agriculture (USDA).   

Our DI framework emphasizes the integration of UCD and HCD techniques throughout any project lifecycle. Other potential providers that use UCD and HCD in the
Federal space tend to do so in a non-integrated fashion. When design activities occur principally at the beginning of product development, it mimics a waterfall approach to building a solution and development teams tend to quickly overlook user insights and the design concepts they inform, relying on developers’ own assumptions of requirements and users’ needs.  

In Steampunk’s iterative approach, our designers consistently engaged with the proxy product owner and development team throughout the I-131 application system’s 
evolution. They led activities such as stakeholder and user journey workshops, user interviews, persona development, card-sorting exercises, wireframing, and usabilty testing to ensure the product being developed meets the business and mission performance requirements of the program's stakeholders as well as meeting or exceeding expectations of the users. All while driving increased adoption through fast feedback loops for continuous improvement.

#### [Click here for more information](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/UCD.md)

<p align="center">
 <img src="https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/Design_Dev_Sec_Ops_D%20%26%20DSO.png" title="Design Intelligence">
</p>

### User Research

We approach each project by fully understanding the problem space from a holistic and human perspective. We start with a solid research plan to outline the objective, goals and success criteria upfront. Through activities such as user interviews, stakeholder and journey maps, we work to uncover the unique challenges each user faces. The results are assets like service blueprints, user journeys, and personas that are referenced throughout the solution development, ensuring that the final outcome not only meets the needs of users and stakeholders, but improves their experience.


To view our design artifacts for the code challenge and I-131 Application
System, please go [here](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/UCD.md).

* User Research
  + [Research Plan](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Research_Plan_CC.pdf)
  + [Stakeholder Map Workshop](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Stakeholder_Map_CC_Mural_Workshop.pdf)
  + [Empathy Map Workshop](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Empathy_Map_CC_Mural_Workshop.pdf)
  + [User Journey Workshop](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Journey_Map_CC_Mural_Workshop.pdf)

* User Interviews
  + [Applicant](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/User_Interview_CC_Applicant.pdf)
  + [Case Manager](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/User_Interview_CC_Case_Manager.pdf)
  + [Business Administrator](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/User_Interview_CC_Business_Administrator.pdf)

* Personas
  + [Applicant](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Persona_CC_Applicant.pdf)
  + [Case Manager](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Persona_CC_Case_Manager.pdf)
+ [Business Administrator](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Persona_CC_Business_Administrator.pdf)

* Journey Maps
  + [Applicant](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Journey_Map_CC_Applicant.pdf)
  + [Case Manager](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Journey_Map_CC_Case_Manager.pdf)
  + [Business Administrator](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Journey_Map_CC_Business_Administrator.pdf)
  + [Service Blueprint](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Service_Blueprint_CC_I-131_Process.pdf)

### Product Design

The key to our product design process is the integration of cross-functional teams—inviting diverse perspectives to foster creativity and innovation while driving towards solutions that are feasible, scalable, and sustainable. We employ established UX practices like card sorting, information architecture, and plain language to build foundational wireframes that we can test with users and improve upon. We incorporate responsive design best practices, the [United States Web Design System](https://designsystem.digital.gov/) (USWDS) including 508 compliant components, interaction patterns, and templates to make sure the interface is not only accessible and available on any device but also visually consistent throughout the larger mission ecosystem.

* [Product Vision](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/PRODUCT_VISION.md)
* [Card Sort](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Card_Sort_CC_Applicant_Application_Information_Architecture.pdf)
* [Visual Design Style Guide](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Visual_Design_CC_USWDS_Styles.pdf)

* Wireframes
  * [Applicant Desktop](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Wireframes_CC_Applicant_Desktop.pdf)
  * [Applicant iPad](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Wireframes_CC_Applicant_ipad.pdf)
  * [Applicant iPhone](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Wireframes_CC_Applicant_iphone.pdf)
  * [Case Manager](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Wireframes_CC_Case_Manager.pdf)
  * [Business Administrator](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Wireframes_CC_Business_Administrator.pdf)

* Quality Assurance Tests
  * [Applicant - Desktop Application](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Wireframes_CC_Applicant_QA_Desktop_Application.pdf)
  * [Applicant - Desktop Instructions](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Wireframes_CC_Applicant_QA_Desktop_Instructions.pdf)
  * [Business Administrator](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Wireframes_CC_Business_Administrator_QA.pdf)

### Usability Testing

We build validation and trust by listening to and learning from users early and often. We perform A/B tests to understand which interactions will help someone achieve a specific function as well as broader evaluations to make sure that users can accomplish a series of tasks in the simplest and most intuitive way possible. This approach not only improves usability and accessibility but ultimately adoption and satisfaction. It also reduces technical debt by ensuring the experience meets both user needs and technical feasibility instead of spending months in production before the end user ever gets to see it or touch it.

* Usability Tests
  + [A/B Test Case Manager](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Usability_Test_CC_AB_Test_Case_Manager.pdf)
  + [Script Applicant](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Usability_Test_CC_Script_Applicant_Desktop.pdf)
  + [Script Business Administrator](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Usability_Test_CC_Script_Business_Administrator.pdf)
  + [Script Case Manager](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Usability_Test_CC_Script_Case_Manager.pdf)
  + [UX Scorecard](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/ucd/CC-Artifacts/Usability_Test_CC_UX_Scorecard.pdf)

-----------
# DevSecOps, CI/CD Pipeline, Testing

### Our DevSecOps Practices

<p align="left">
<img src="https://playbook.cio.gov/assets/images/usds-logo-seal.png" align="middle" title="USDS Logo" width=50 height =50>
<strong>Applying <a href="https://playbook.cio.gov/#play8">Play 8</a>, <a href="https://playbook.cio.gov/#play9">Play 9</a>, <a href="https://playbook.cio.gov/#play10">Play 10</a>, <a href="https://playbook.cio.gov/#play11">Play 11</a>, and <a href="https://playbook.cio.gov/#play12">Play 12</a></strong>
</p>
At Steampunk, we take a different approach to DevSecOps that focuses not only on
the latest technology capabilities in the DevSecOps ecosystem, but equally on the
human resources, organizational, and culture change required to enable the people,
the processes and the technology to get aligned on a common outcome – the mission
of their organization. We believe DevSecOps has something to offer to every
mission. While DevSecOps does provide technical excellence – continuous improvement
in areas like continuous integration, continuous delivery, continuous deployment,
infrastructure as code, automated testing, and the like – its primary outcome is
providing communication and collaboration between all aspects of the organization
(infrastructure, quality, security, etc.) to meet the shared mission.

We leverage continuous integration and continuous delivery for every microservice we build. It is important that quality gates are implemented within this process to ensure quality and security are not something that are tacked on at the end but rather integrated early and used to enforce team standards.

##### [Click here for more detailed information](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/CICD.md)

#### Alignment on Outcomes

Our teams begin each engagement by understanding the constraints in your current
software delivery process (from development to operations) to identify short- and
long-term outcomes. This assessment in the Discovery Sprint identifies a long-term
strategy, definition of done, and outlining short-term outcomes to meet the
long-term strategy. Steampunk then builds a backlog of capabilities and features
for the team. Using our Design Intellignece, our CMMI-Level 3 certified approach
that integrates HCD, Agile and DevSecOps, we empower teams to collaborate with
stakeholders (across the business, Dev, Sec and IT Ops) to accomplish those
outcomes using service design techniques, as experts in their work and context.
We find that by focusing on designing and executing short-term outcomes that support
a long-term strategy, teams will adjust to emergent needs. While there is no “one
size fits all” approach to DevSecOps for every program, our experience has taught
us that by building DevSecOps capabilities using an iterative approach leads to
greater adoption and fosters a culture of continuous improvement.

#### Design with Flexibility and Scalability

Steampunk uses a microservice-based development approach and containerized
deployment infrastructure to maximize flexibility, reuse, quality, performance,
and maintainability of our product development. Our delivery and deployment
approach provides programs with a flexible deployment architecture that allows
the organization to respond to changes in the business environment, reduce the
reintroduction of flaws, and provides maximum interoperability and reuse by other
products and teams.

Our focus on building reusable artifacts such as infrastructure as code
images/scripts, microservices, and pre-accredited components of the architecture
provides fully tested, secure, scalable, quality reusable services that can be
rolled back to a previous state for any number of reasons from backing out
production changes to disaster recovery. These reusable components also maximize
efficiency in the risk management process through NIST control inheritance of common
control providers and ensure platform and networks are compliant with USCIS
guidance for security, reliability, and availability. This speeds delivery to the
mission while maximizing reuse and minimizing rework.

#### Measuring Progress using Quantitative Measures

Steampunk captures the effectiveness of the development and delivery process by
measuring four key metrics of throughput, stability, and quality aligned with the
DORA State of DevSecOps Report. We measure the throughput of the software delivery
process using **lead time** of code changes from check-in to release along with
**deployment frequency**. Stability is measured using **time to restore** — the
time it takes from detecting a user impacting incident to having it remediated —
and **change fail rate**, a measure of the quality of the release process. Our
teams identify additional program specific metrics as defined by short- and long-term
outcomes and integrate those quantitative measures from automated processes in the
CI/CD pipeline to provide metrics that measure mission success for each program’s
unique requirements.

#### Adopted DevSecOps Processes

Steampunk’s DevSecOps approach uses the following tools and techniques, listed
in the table below. Although we may use tools listed in an initial transition-in,
our philosophy is to use the best of breed tools for any given situation. That
simply means, while we might utilize Jenkins or even a language like Ruby today,
we are always looking for ways to improve and leverage new tools to provide
better value to our customers.

DevSecOps Activities and Techniques |	Goals, Benefits | Tools
----- | ----- | -----
Pipeline Orchestration |	Automates collection, compilation, build, deployment of software. Affords the team comprehensive automation. Significantly reduces or eliminates human error while increasing speed and efficiency in the build and deployment process. We use these tools and principles throughout development, test, and production deployments. | **Jenkins**
Source Code Control Repository	| Version control, single source of truth for code, scripts, configuration files, templates. Promotes reuse, guards against human error in code changes, etc. | **GitHub**
Artifact Repository |	Version control of libraries, promotes reuse and continuity of library and framework use across the organization, protects the organization from sprawl and security risks of open source or external dependencies, improves performance of resulting code deliverables. | **Nexus**
Automated Testing Framework |	Automated testing promotes clean coding principles, code modularity and isolation, and reliable code behavior. This increases the confidence in the code developers create, reduces risk when code is refactored, and significantly increases the ability to easily identify and track down bugs introduced into code over its life. | **Junit, Nunit, Selenium**
Code Coverage Testing | Validating that at least 75% of code is covered by automated unit/regression tests that run on every automated build. Failure to meet code coverage thresholds results in failed builds, which stops development until the test coverage can be met. | **Jacoco, Sonar**
Security Testing |	A subset of automated tests, focused on secure coding by ensuring dependencies are valid and secure, code meets quality and security standards, and both the code and running application have been scanned properly for potential security vulnerabilities. | **SOWASP Dependency Check, OWASP ZAP, Findbugs, PMD, Checkstyle**
Performance Testing Framework |	These tests profile code performance and indicate where issues exist in code that could cause scale and/or performance problems when deployed to larger user base with greater concurrency and load. | **JMeter**
Containerization, Virtualization	| Development teams deploy software to containers for deployment across all environments. Containers and other virtualization techniques abstract physical server resources and allow teams to automate provisioning, orchestration and management of workloads running in the wide variety of deployment architectures. | **OpenShift, Docker**
Provisioning, Configuration Management, and Deployment	| Development teams use infrastructure as code to automate the provisioning and configuration management of both infrastructure and platform resources. This provides rapid deployment through automation, reduces human error, and allows security teams to accredit infrastructure and platform environments for accelerated ATOs for subsequent workloads that inherit common controls provided by automated infrastructure.  | **Terraform, Ansible**
Automated Deployment	| Deployment of software is fully automated, such that once the organization has approved workload development for production deployment, teams can use tooling to “push button deploy” vs. having to manually move workloads onto production environments. These tools and principles are used throughout development, test, and production deployments for continuity and resilience. | **Jenkins, Openshift**

### Our Pipeline

As discussed in the [Quality](https://github.com/Steampunk-SPEED/GettingStarted/blob/master/QUALITY.md) documentation, Steampunk has built
out a full DevSecOps pipeline, going through multiple stages, and providing feedback
at multiple stages. Our pipeline hits six distinct phases, balancing the impact and
celerity of the results. Each phase is composed of one or more stages, with each
one gated based on the results produced.

<p align="center">
 <img src="https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/cicd_pipeline_overview.png" title="CI/CD Pipeline Overview">
</p>

#### Code Quality Standards

Phase | Actions | Gates | Frequency | Results
----- | ----- | ----- | ----- | -----
**Local Development** | Write Tests; Write Code; Execute Tests (locally) | Code Review (in Pull Request) |  Feature Branch  | Initiate pull request and code review
**Development** | Code Compilation and Build; Unit Testing; Code Coverage; Static Analysis | No build errors; All tests pass; 90% branch coverage; No [quality profile errors](QUALITY.md#code-quality) | Every commit and PR | Failures prevent merge, developer notified
**Integration** | Build Container; Deployment to ephemeral Integration Environment, Integration Testing; API Testing; Application Security Scan; API Tests; Feature Testing; SonarQube Quality Scan | All tests pass; 90% branch coveragel; No High or Medium severity issues, other issues have remediation plan | Every PR | Failures prevent merge, team notified
**QA** | Deployment to QA; Usability Testing; Traffic Monitoring Security Analysis; Exploratory Testing; 508 Testing | All tests pass; No High or Medium severity issues, other issues have remediation plan | Develop & Master | Failures notify team and release management
**Staging** | Deployment to Staging; Load Testing; Stress Testing; Fuzz Testing; Compatibility Testing | p>99% succeed rate at load thresholds; p>95% succeed rate at +50% load thresholds; No High or Medium severity issues, other issues have remediation plan; All tests pass | Develop & Master | Failures notify team and release management
**Production** | Deployment to Production |  | Develop & Master | Failures notify team, Product Owner, and release management


As a result of our usage of containerization, each time code is merged into Develop,
a docker container is built for the microservice, which is promoted through different
phases. This singular container provides confidence that the same application and
code that was tested at the Integration phase, is the same application and code
that is being promoted into production. This limits issues from configuration,
manual installation and changes, and other common deployment problems.

<p align="center">
 <strong>Example Pull Request Pipeline Run</strong>
 <img src="https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/cicd_completed_PR.png" title="CI/CD Completed PR">
</p>

<p align="center">
 <strong>Example Pipeline Failure on Manual Testing</strong>
 <img src="https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/cicd_maunal_test.png" title="CI/CD Manual Test Failure">
</p>

As mentioned in our description of [quality profiles](QUALITY.md#quality-profile)
a quality profile is built for each code change, providing insight into the code,
and all metrics against it. SonarQube provides a developer dashboard for deep
insight into any coding issues, including tests run, coverage obtained, security
issues, and technical debt tracking. Jenkins holds a team level dashboard providing
insight into each microservice, including tests run, stages executed, and feedback
obtained.

By virtue of building out the pipeline in this fashion, any code that makes it
through the Staging phase is clearly of high quality, but our dashboarding and
reporting makes it easy to determine what (if any) issues might exist, so our
team can make an easy go/no decision on the release of the software.

<p align="center">
 <strong>Example use of go/no-go approval process built into Jenkins</strong>
 <img src="https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/cicd_reporting_regression_test.png" title="Reporting Regression Test">
</p>

### GitFlow and Container Promotion


As the above illustrates, we are using a typical GitFlow pattern to promote our code, but we have expanded on the GitFlow model to better apply it for handling containers. Because we are not really promoting code through our process, but rather containers, each branch type (see frequency in the above table) performs different actions, sometimes at the code level and sometimes at the container level. This modernized look at the GitFlow process was arrived at through looking at a value steam mapping of our activities, and removing repetitive steps.

This means activities such as unit and integration tests are not run after the code is compiled and put into a container. Our containers are not ever rebuilt, but promoted through our artifact repository system (Nexus). This provides confidence that only code that has been thoroughly tested can make its way into production, while still streamlining the process.

-----------

# Reporting and Dashboarding

We utilize a variety of tools and techniques to measure quality, reliability, and security of everything we build, deploy and maintain. For this challenge we've utilized the following reports and dashboards as part of our software delivery process.

* Sonarqube
* Jenkins (BlueOcean View)
* JIRA
* AWS CloudTrails
* AWS CloudWatch
* Splunk
* OKD Dashboarding

-----------

# Benefits of Our Approach

### Innovative Techniques

We utilize a variety of innovative techniques in our approach that enable quality, reliability, and security without suffering cycle time. We have made a concious choice to leverage these innovations as a part of our design approach, and show we live and breathe these practices every day.

<p align="center">
 <img src="https://github.com/Steampunk-SPEED/GettingStarted/raw/master/img/innovations.PNG" title="Innovations">
</p>

#### GitOps 🌟
<p align="left">
<img src="https://playbook.cio.gov/assets/images/usds-logo-seal.png" align="middle" title="USDS Logo"  width=50 height =50>
<strong>Applying <a href="https://playbook.cio.gov/#play13">Play 13</a></strong>
</p>
As much as possible we utilize a GitOps approach to storing everything in Git to drive maximum collaboration and transparency between our team, our customer, and other people who might collaborate or benefit from the collective knowledge and experience stored in Git. This includes artifacts such as source code, design documents, architecture, automated tests, infrastructure as code, and pipeline as code. Team Steampunk recognizes the role proper documentation plays in the ability to collaborate and leverage others source code. By using markdown in the GitHub repository as documentation, it provides one simple place to support transparent collaboration for our customers.

#### Design Intelligence &trade; 🌟

We recognize Design Intelligence &trade; as an innovative way that we integrate Agile, DevSecOps and UCD best practices to drive better alignment on outcomes, a positive user experience, and an overall solution that has a positive impact on the mission. Design for us is not just a "one-and-done" or a "throw-it-over-the-wall" activity but one that is completely integrated throughout the software development process to gain material impacts on not just the application UI, but other products such as the repositories or pipeline.

#### Container Flow 🌟

Container Flow is a new modern approach that more readily aligns with microservice development by taking the premise of a branch-merge strategy like GitFlow but focusing on container promotion instead of the code. This means activities such as unit and integration tests are not run until after the code is compiled and put into a container. Our containers are not ever rebuilt, but promoted through our artifact repository system (Nexus). This provides confidence that only code that has been thoroughly tested can make its way into production, while still streamlining the process.

#### Shifting Left 🌟

By utilizing value-stream mapping activiites, we have shifted many of our development and testing activities as left as possible and relied on parallelism to really drive down our feedback cycle time. This not only leads to less development context shift but also produces much smaller faster times while still maintaining quality, reliability and security at speed.

#### Cloud-Native RBAC Solution (AWS Cognito) 🌟

We identified areas in the application where we could leverage cloud native services into our application. This provides an ability to reduce feature complexity while developing an application, increases long-term maintainability of the application, as well as reducing overall costs by leveraging inexpensive cloud-ready options. AWS Cognito provides a authentication service for role-based access controls for our application.

#### Reusable Design 🌟

We focused on getting the most out of our time as possible. Architecture, testing, UI/UX were all carefully planned with reusability in mind. A good example is our automated tests: a single front-end test using DDD is leveraged not only to test a front-end workflow, but reused in a Security Scanning Proxy tool to gain Dynamic Application Security Testing results, through JMeter for peformance and load testing, with SauceLabs to provide browser-compatibility and with our usability testing tools to provide 508 compliance testing results. In this way we do not exert additional effort or repeat work but think how a single test might provide value across the entire process from requirements to production.

##### Using Design Thinking to Guide Data Science
Approaching data science through the lens of Design Intelligence &trade; ensures that our solutions are user and mission centric. We lean on our UCD methodology to learn from users through interviews and workshops. The information gathered through this process begins to define user challenges and pain points, as well as potential optimizations in the mission space. In addition to known data sources residing in transactional systems, enterprise data warehouses, and data lakes, new data sources begin to come out of the woodwork: small databases on shared drives, .CSV files exported from CMS tools, ETL macros in Excel files on users' laptops - the list goes on. It is through these interviews and workshops that empathy and domain knowledge for users' processes and data can truly form. At Team Steampunk, we believe that empathy is critical to problem understanding, and thus effective data science solutions.

### Effective Transition for Operational Use
We believe our approach to building software utilizing USCIS's technical stack, microservice development best practices and GitOps allow for USCIS to transition our source code for operational use easily. By leveraging a technical stack USCIS has already approved, it does not require additional technology acquisitions to implement the solution. Our microservice architecture also allows for rapid deployment through Openshift, allowing USCIS to deploy and pilot the use of the software in any business domain. Lastly, by leveraging GitOps effectively, the source code is well documented and everything required to build, test, configure the microservice is locally stored in Git along with source code. This fosters collaboration and adoption of the source code by individuals other than our team and maximizes collaboration.


-----------
> ***This repository is considered Steampunk proprietary information. Dissemination or
> copy of this information without the permission of Steampunk is strictly prohibited.***
