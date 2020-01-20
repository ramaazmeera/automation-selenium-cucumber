AUTOMATIONPRACTICE-FUNCTIONAL-TEST
This is a sample implementation of functional test using Cucumber and Selenium framework.

Framework used:
Cucumber
Junit
Java
Selenium
##For Reporting used:

cluecumber

To get the pie chart reports, navigate into target and to generated-report and access report (index.html) in any of the displayed browsers on top right hand corner.

headless mode - optional

Running tests:
Running using maven target in terminal:
To run in chrome use this target(default browser): mvn clean verify

To run in firefox use this target: mvn clean verify -Dbrowser=firefox

All possible profiles are listed under pom.xml.

This test can be executed in both windows and mac, in order to do this, this framework used webdriver manager to get the latest drivers for windows and mac.