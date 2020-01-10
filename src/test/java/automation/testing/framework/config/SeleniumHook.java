package automation.testing.framework.config;

import automation.testing.framework.selenium.WebDriverHandler;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.ScreenshotException;

import java.net.MalformedURLException;


public class SeleniumHook {

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
            webDriverHandler.getDriver().manage().window().maximize();

            attachScreenShot(scenario);
        }
        closeDriver();
    }

    public byte[] attachScreenShot(Scenario scenario)  {
        byte[] bytes = new byte[0];
        TakesScreenshot ts = (TakesScreenshot) webDriverHandler.getDriver();
        try {
            bytes = ts.getScreenshotAs(OutputType.BYTES);
            scenario.embed(bytes, "image/png");
        } catch (ScreenshotException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public void closeDriver() {
        webDriverHandler.closeDriver();
    }
}