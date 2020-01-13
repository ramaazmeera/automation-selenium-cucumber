package automation.testing.framework.selenium;

import automation.testing.framework.config.EnvironmentHandler;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


import java.util.concurrent.TimeUnit;

public class WebDriverHandler {

    private final EnvironmentHandler env;

    private WebDriver driver;

    public WebDriverHandler(EnvironmentHandler environmentHandler) {
        this.env = environmentHandler;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void initWebDriver() {

        String browserName = StringUtils.defaultIfBlank(env.valueOf("browser"), "chrome");

        System.out.println("Getting driver for " + browserName);

        WebDriver driver;
        switch (browserName) {
            case "firefox":
//                String firefoxdriverpath = env.valueOf("firefoxDriverLocation");
//                System.setProperty("webdriver.gecko.driver",firefoxdriverpath );

                System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
                FirefoxOptions options = new FirefoxOptions();
                if (StringUtils.equalsIgnoreCase(env.valueOf("headless.mode"), "false")) {
                    options.setHeadless(false);
                }
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(options);
                break;


            default:
                driver = getChromeDriver();
        }
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        this.driver = driver;
    }

    private WebDriver getChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (StringUtils.equalsIgnoreCase(env.valueOf("headless.mode"), "false")) {
            options.setHeadless(false);
            options.addArguments("no-sandbox");
            options.addArguments("disable-dev-shm-usage");
        }
        options.setAcceptInsecureCerts(true);

//        String chromedriverpath = env.valueOf("chromeDriverLocation");
//        System.setProperty("webdriver.chrome.driver",chromedriverpath);
        WebDriverManager.chromedriver().setup();

        return new ChromeDriver(options);


    }

    public void closeDriver() {
        driver.quit();
    }
}



