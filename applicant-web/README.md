![Logo](https://github.com/SteampunkFoundry/images/raw/master/steampunk_banner-white_pink_on_grey.jfif)
# speed-applicant-web service

This is the microservice representing the front end web interface for the Applicant Portal

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

### Available Scripts

In the project directory, you can run:

#### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.\
You will also see any lint errors in the console.

#### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

#### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

#### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

### Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

-----------

## Maintainability 
The tools used to measure the maintainability of this code are the following:

* CODE REVIEWS: https://github.com/Steampunk-SPEED/speed-applicant-web/pulls?q=is%3Apr+is%3Aclosed
* SONARQUBE METRICS: https://sonarqube.speedc2c24.steampunkfoundry.net/dashboard?id=speed%3Aapplicant-web
* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-applicant-web/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Security 
The tools used to measure the security of this code are the following:

* DEEP-CODE-CI: https://www.deepcode.ai/app/gh/
* SONARQUBE METRICS: https://sonarqube.speedc2c24.steampunkfoundry.net/dashboard?id=speed%3Aapplicant-web
* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-applicant-web/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Scalability 

The tools used to measure the scalability of this code are the following:

* JENKINS BUILDS (TEST EXECUTION): https://jenkins-ci.speedc2c24.steampunkfoundry.net/job/Microservices%20Pipeline/job/speed-applicant-web/

**Authentication can be found [here](https://github.com/Steampunk-SPEED/GettingStarted#devsecops-pipeline).**

-----------

## Other Considerations

We descoped the preparer's information, age eligibility and several applicant type journeys in our MVP. In the future we would like to pull those stories from the backlog.

### Effective Transition for Operational Use of this Repository


This repository serves as the backend forms microservice for the applicant portal. It could be used as a starting place for digitizing the I-131 form by building and deploying in the USCIS openshift cluster. It would require additional work to complete the entire microservice

-----------

***This repository is considered Steampunk proprietary information. Dissemination or copy of this information without the permission of Steampunk is strictly prohibited.**

