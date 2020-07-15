@api

Feature: post call to service

  Background:
    Given MongoDB is available
    And the test database collection is empty

  Scenario Outline: I perform a POST whilst sending a JSON body

    When a JSON body created with <emailAddress>, <name>, <dob>, <mobile>,<language>
    And a POST request is submitted to account manager with /v1/test/create service endpoint
    Then <status code> is returned
    And a new account created with <emailAddress>,<name>,<dob>,<mobile>,<language> in test database collection

    Examples:
      | emailAddress   | name | dob        | mobile      | language | status code |
      | test@gmail.com | Test | 1990-11-29 | 07777777777 | English       | 201         |