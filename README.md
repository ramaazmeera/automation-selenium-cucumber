# AUTOMATION-PRACTICE-FUNCTIONAL-TEST

This is a sample implementation of functional test using Cucumber and Selenium framework.

## Framework used:
* Cucumber
* Junit
* Java
* Selenium

## For reporting this framework used:
* cluecumber

* To get the pie chart reports, navigate into target and to generated-report and access report (index.html) in any of the displayed browsers on top right hand corner.

* headless mode - optional

## Running tests using maven target in terminal:

* To run in chrome use this target(default browser):  mvn clean verify

* To run in firefox use this target: mvn clean verify -Dbrowser=firefox

#### running from IDE
* Run individual tests such as RunAllCucumberTests.java or RunUICucumberTests.java.
* By default it will run against `dev` environment.
* To change the environment and/or browser; use VM argument as `-Denvironment=qa` in run configuration.

#### running from maven
* `mvn clean test -Denvironment=qa` runs all cucumber scenarios against QA environment.
* Cucumber tag and maven profile is used to run a set of tests. Example -
  * `mvn clean test -P uiOnly -Denvironment=dev` runs only UI based scenarios against dev environment.
* All possible profiles are listed under pom.xml.

#### running for a particular set of scenarios
1. Tag the scenario by an unique annotation such as `@hello_me`. Tagging can be applied at feature level or at scenario level.
2. To run scenarios with tag `@debug1` and `@debug2` use `mvn clean test -Dcucumber.options="--tags @debug1 --tags @debug2"`
3. To run scenarios with tag `@debug1` or `@debug2` use `mvn clean test -Dcucumber.options="--tags @debug1, @debug2"`


* All possible profiles are listed under pom.xml.

## This test can be executed in both windows and mac, in order to do this, this framework used webdriver manager to get the latest drivers for windows and mac.


### Do's
* Keep one to one mapping of Page Object and HTML page. It will keep the page concern modular and separate.
* Logically separate the step definitions into relevant files. Don't implement all steps for multiple pages in a single file.
* Do remove the redundant code /configuration ASAP.
* ***Must add `@ui` tag in selenium based feature or scenario.***

### Don'ts
* Don't add/initialise a web elements in Page Object which are not needed in any step. Follow YAGNI.
* Don't get the page element by bypassing the Page Object. Page Object should encapsulate the selenium web elements.
* Don't manipulate the dom from outside the Page Object.
* Page object shouldn't contain assertions.
* Don't write cucumber steps under uk.gov.dwp.sdp.functionaltest.selenium package.
* Don't write selenium based code under uk.gov.dwp.sdp.functionaltest.stepdef package.
* Don't Add `@ui` tag in non-gui or non-selenium based feature or scenario.













