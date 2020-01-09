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
Chrome:
mvn clean verify
To run in firefox:
 mvn clean verify -Dbrowser=firefox

* All possible profiles are listed under pom.xml.

#### running for a particular set of scenarios
1. Tag the scenario by an unique annotation such as `@hello_me`. Tagging can be applied at feature level or at scenario level. 
2. To run scenarios with tag `@debug1` and `@debug2` use `mvn clean test -Dcucumber.options="--tags @debug1 --tags @debug2"`
3. To run scenarios with tag `@debug1` or `@debug2` use `mvn clean test -Dcucumber.options="--tags @debug1, @debug2"`
   


