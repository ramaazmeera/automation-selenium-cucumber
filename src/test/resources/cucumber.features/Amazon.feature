Feature: This feature is to verify connecting to sqs and sns


  @test8
  Scenario: verify message is published and and sqs consumed the message
    Given I have connected to sns and sqs
    When I publish the message in sns topic
    Then I should see message transferred to sqs
    And verify message body contains below content
      |Type|
      |MessageId|
      |Message  |
      |Timestamp|
