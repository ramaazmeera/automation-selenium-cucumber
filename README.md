# AUTOMATIONPRACTICE-FUNCTIONAL-TEST
This is a sample implementation of functional test using Cucumber and Selenium framework.
## Framework used
* Cucumber
* Junit
* Java

Reporting:
cluecumber


## Current capability
* Browser based tests
  * Selenium
  * headless mode - optional

## Running tests

#### running from IDE
* Run individual tests such as RunAllCucumberTests.java or RunUICucumberTests.java. 
* By default it will run against `dev` environment. 


#### running from maven
Chrome: mvn clean verify

To run in firefox: mvn clean verify -Dbrowser=firefox


* All possible profiles are listed under pom.xml.

#### Tests will run in mac and windows

used webdriver manager for to get the latest drivers's for windows and mac


   


