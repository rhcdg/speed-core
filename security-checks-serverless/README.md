![Logo](https://github.com/SteampunkFoundry/images/raw/master/steampunk_banner-white_pink_on_grey.jfif)
# speed-security-checks service

This microservice is set up to perform security checks against case data using lambda functions.

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

## Run
```
$ docker build -t speed/security-checks .
$ docker run -p 9015:8080 -it speed/security-checks
```

### Testing with Severless
```
$ npm install
$ sls invoke local -f processSecurityChecks --data '{ "queryStringParameters": {"a_number":"A426707185", "last_name":"Lehner", "first_name":"Danilo"}, "headers": {"Authorization": "Bearer eyJraWQiOiJlMVNWOE9zOFpuUlZPelhsZlE3VmszbFk1WGloOW96cUZReUZPRHdwdFAwPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiIxOThhYjJjYi00M2E2LTQwMTAtYWQwNS1hOTQ4YzA5YmI2OGIiLCJjb2duaXRvOmdyb3VwcyI6WyJBZG1pbmlzdHJhdG9yIiwiQWRqdWRpY2F0b3IiXSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLWVhc3QtMi5hbWF6b25hd3MuY29tXC91cy1lYXN0LTJfQ0RSaDNma2MwIiwidmVyc2lvbiI6MiwiY2xpZW50X2lkIjoiMjc0Z212ZzE4aXUwM2NkbGlmaWk0dWRuZjIiLCJldmVudF9pZCI6IjhhMDRiYjc3LTRjMzEtNGI4NS1hZGZiLTZmN2YzZjlhZjNlMiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoib3BlbmlkIiwiYXV0aF90aW1lIjoxNjE3MjIzNTM4LCJleHAiOjE2MTcyMjcxMzgsImlhdCI6MTYxNzIyMzUzOCwianRpIjoiYWM5YmZmODQtOTIxYy00MWQ1LWI3MjItY2I0YzBlZDJjNGUxIiwidXNlcm5hbWUiOiIxOThhYjJjYi00M2E2LTQwMTAtYWQwNS1hOTQ4YzA5YmI2OGIifQ.POq3niP3wU8XXKY02CJwcV7ebE-2Oq80z7IGiPkOGx_O9BF3BQQX0_26XjeB-TU3T4rn9Oy6oTQekQ0t5BLUbWfNsR3SNoRiWgwvcTSFWoFQI-XtcDulIkJPu_r1TfUXSUObDfTNwzIlUaIcM5eIwnU7o6MVbCAHDLZuxslSzU34zF_3F3YUtopEEhC636SP9o65itfpA_zGigyC-M0FqTJHAq8thdf112aiDbHeAqotJQOx0-5E6RhC2dLgvY29ELBquUvugr_Tcj0LtxPDKNcYN2jKvtFImRETLXbXmcQfh5ZcdUJs72LQWvnZcB1L_pLP19zBcPnaN-VtUhAMWg"}}'
```

-----------

## Maintainability 
The tools used to measure the maintainability of this code are the following:

* CODE REVIEWS: https://github.com/Steampunk-SPEED/speed-security-checks/pulls?q=is%3Apr+is%3Aclosed
* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-security-checks/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Security 
The tools used to measure the security of this code are the following:

* DEEP-CODE-CI: https://www.deepcode.ai/app/gh/
* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-security-checks/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Scalability 

The tools used to measure the scalability of this code are the following:

* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-security-checks/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Other Considerations

We would like to add additional feature rich security checks, some are noted in our backlog.

### Effective Transition for Operational Use of this Repository

This is an example of a serverless implementation of a security check and could be used as a model for how they could be implemented within USCIS.

-----------

***This repository is considered Steampunk proprietary information. Dissemination or copy of this information without the permission of Steampunk is strictly prohibited.**
