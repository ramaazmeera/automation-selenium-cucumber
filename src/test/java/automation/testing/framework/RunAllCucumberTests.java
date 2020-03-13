package automation.testing.framework;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {
      "pretty",
      "json:target/cucumber-report/cucumber.json",
      "html:target/cucumber-report/cucumber.html",
    },
    features = "classpath:cucumber.features",
    tags = {"@Api"})
public class RunAllCucumberTests {}
