@Api
Feature: Http GET verb using parameters

  Scenario: I perform a GET whilst sending a query parameter and I assert using a datatable
    Given a parameter 'name' with a value 'Rama'
    When a GET request is made
    Then status code 200 is returned
    And a response body should be returned
    And the following values should be returned
      | field   | value                             |
      | name    | Rama                              |
      | surname | Azmeera                           |
      | role    | Test Engineer                     |
      | project | cucumber-rest-assured             |
      | url     | https://www.bbc.co.uk/news/uk |

  Scenario: I perform a GET whilst sending headers and I assert using a datatable (as Map)
    Given the following headers
      | Header | Value |
      | name   | Rama  |
    When a GET request is made
    Then status code 200 is returned
    And a response body should be returned
    And the following values should be returned
      | field   | value                         |
      | name    | Rama                          |
      | surname | Azmeera                       |
      | role    | Test Engineer                 |
      | project | cucumber-rest-assured         |
      | url     | https://www.bbc.co.uk/news/uk |

  Scenario: I perform a GET whilst sending headers and I assert using a data file
    Given the following headers
      | Header | Value |
      | name   | Rama  |
    When a GET request is made
    Then status code 200 is returned
    And a response body should be returned
    And the response should match the json responseFile1.json

  Scenario: I perform a GET whilst sending headers and I assert using individual steps
    Given the following headers
      | Header | Value |
      | name   | Rama  |
    When a GET request is made
    Then status code 200 is returned
    And a response body should be returned
    And the field 'name' should equal value 'Rama'
    And the field 'surname' should equal value 'Azmeera'
    And the field 'role' should equal value 'Test Engineer'
    And the field 'project' should equal value 'cucumber-rest-assured'
    And the field 'url' should equal value 'https://www.bbc.co.uk/news/uk'

  Scenario: I perform a GET whilst sending headers and I assert using a datatable (as String)
    Given the following list of strings
      | name | Rama |
    When a GET request is made
    Then status code 200 is returned
    And a response body should be returned
    And the following values should be returned
      | field   | value                         |
      | name    | Rama                          |
      | surname | Azmeera                       |
      | role    | Test Engineer                 |
      | project | cucumber-rest-assured         |
      | url     | https://www.bbc.co.uk/news/uk |

  Scenario: I perform a POST whilst sending a JSON body file
    Given a JSON body 'post.json'
    When a POST request is made
    Then status code 200 is returned
    And a response body should be returned
    And the following values should be returned
      | field            | value           |
      | status           | 200             |
      | response-message | POST Successful |

  Scenario: I perform a POST whilst sending a JSON body using a datatable
    Given a JSON body with the following:
      | name    | Rama                          |
      | surname | Azmeera                       |
      | role    | Test Engineer                 |
      | project | cucumber-rest-assured         |
      | url     | https://www.bbc.co.uk/news/uk |
    When a POST request is made
    Then status code 200 is returned
    And a response body should be returned
    And the following values should be returned
      | field            | value           |
      | status           | 200             |
      | response-message | POST Successful |