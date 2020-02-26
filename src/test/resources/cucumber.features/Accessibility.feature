Feature:Accessibility - Miscellaneous

  @ui

  Scenario: Automation practice page has no accessibility violations

    Given I navigate to the automationpractice url
    Then the web page does not contain any accessibility violations