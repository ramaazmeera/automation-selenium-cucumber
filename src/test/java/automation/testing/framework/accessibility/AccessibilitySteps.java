package automation.testing.framework.accessibility;

import cucumber.api.java.en.Then;

import java.io.IOException;

public class AccessibilitySteps {

    private final AccessibilityHelper accessibilityHelper;

    public AccessibilitySteps(AccessibilityHelper accessibilityHelper){
        this.accessibilityHelper = accessibilityHelper;
    }

    @Then("the web page does not contain any accessibility violations")
    public void theWebPageDoesNotContainAnyAccessibilityViolations()throws IOException {
        accessibilityHelper.testAccessibility("home page");

    }
}
