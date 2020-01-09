package automation.testing.framework.config;

import automation.testing.framework.selenium.WebDriverHandler;
import cucumber.api.java.After;
import cucumber.api.java.Before;


public class SeleniumHook {

    private final WebDriverHandler webDriverHandler;

    public SeleniumHook(WebDriverHandler webDriverHandler) {
        this.webDriverHandler = webDriverHandler;
    }

    @Before("@ui")
    public void initWebDriver() {
        webDriverHandler.initWebDriver();
    }

    @After("@ui")
    public void closeDriver() {
        webDriverHandler.closeDriver();
    }
}
