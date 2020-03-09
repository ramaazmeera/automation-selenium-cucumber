package automation.testing.framework.config;

import automation.testing.framework.selenium.WebDriverHandler;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ScreenshotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class SeleniumHook {
    private static final Logger LOG = LoggerFactory.getLogger(SeleniumHook.class);

    private final WebDriverHandler webDriverHandler;

    public SeleniumHook(WebDriverHandler webDriverHandler) {
        this.webDriverHandler = webDriverHandler;
    }

    @Before("@ui")
    public void initWebDriver() {
        webDriverHandler.initWebDriver();
        webDriverHandler.getDriver().manage().window().maximize();
    }

    @After("@ui")

    public void afterTestRun(Scenario scenario) throws MalformedURLException {
        if (scenario.isFailed()) {
            logTestFailure("UI", scenario.getName());
            for (byte[] screenshot : webDriverHandler.takeFullPageScreenShot()) {
                scenario.embed(screenshot, "image/png");
            }
        }
        webDriverHandler.destroyDriver();
    }

//    public byte[] attachScreenShot(Scenario scenario)  {
//        byte[] bytes = new byte[0];
//        TakesScreenshot ts = (TakesScreenshot) webDriverHandler.getDriver();
//        try {
//            bytes = ts.getScreenshotAs(OutputType.BYTES);
//            scenario.embed(bytes, "image/png");
//        } catch (ScreenshotException e) {
//            e.printStackTrace();
//        }
//        return bytes;
//    }

    private void logTestFailure(String testType, String scenarioName) {
        LOG.warn("*******************************************************");
        LOG.warn("{} SCENARIO FAILED: {}", testType, scenarioName);
        LOG.warn("*******************************************************");
    }

}