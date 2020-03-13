Feature:Accessibility - Miscellaneous

  As a visually impaired user
  I want to be able to easily navigate through the application
  So that I can help claimants

  @ui1

  Scenario: Automation practice page has no accessibility violations

    Given I navigate to the automationpractice url
    Then the web page does not contain any accessibility violations