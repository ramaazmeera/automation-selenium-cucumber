@ui1
Feature: Api test example


  Scenario Outline: A basic referral's are submitted successfully

    When a "<RequestType>" request is submitted to the "<ServiceName>" "<Endpoint>"
    Then the response code must be 200
    And Response body contains below
    |forename|surname|
    |Rama    |Azmeera|
    |lilly   |lil    |

    Examples:

      | RequestType | ServiceName           | Endpoint                                    |

      | GET         | http://localhost:3000 | /v1/weather |