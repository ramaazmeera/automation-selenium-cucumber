# AUTOMATIONPRACTICE-FUNCTIONAL-TEST

This is a sample implementation of functional test using Cucumber and Selenium framework.

## Framework used:
* Cucumber
* Junit
* Java
* Selenium

##For Reporting used:
* cluecumber
* To get the pie chart reports, navigate into target and to generated-report and access report (index.html) in any of the displayed browsers on top right hand corner.

* headless mode - optional

## Running tests:

## Running using maven target in terminal:
mvn clean verify - this will run tests in chrome (default browser).

To run in firefox use this target: mvn clean verify -Dbrowser=firefox

* All possible profiles are listed under pom.xml.

## This test can be executed in both windows and mac, in order to do this, this framework used webdriver manager to get the latest drivers for windows and mac.



    Please UNCOMMENT the below line of code in "AddDressToCartPage class" if you want to the run this test in windows operating system using Chrorme browser as this website is using iframes when user clicks printed summer dress,

    Please COMMENT the below line of code if you want to the run this test in below environments and browsers:
    Windows with Firefox AND
    MAC with any browser

        //webDriverHandler.getDriver().switchTo().frame(0);


   


