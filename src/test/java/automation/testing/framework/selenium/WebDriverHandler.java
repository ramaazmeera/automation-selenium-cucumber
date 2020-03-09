package automation.testing.framework.selenium;

import automation.testing.framework.config.EnvironmentHandler;
import automation.testing.framework.config.SeleniumHook;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverHandler {
  private static final Logger LOG = LoggerFactory.getLogger(WebDriverHandler.class);

  private final EnvironmentHandler env;

  private WebDriver driver;
  private static final long PAGE_LOAD_TIMEOUT = 10;
  private static final long IMPLICIT_WAIT = 5;

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
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
        FirefoxOptions options = new FirefoxOptions();
        if (StringUtils.equalsIgnoreCase(env.valueOf("headless.mode"), "false")) {
          options.setHeadless(false);
        }
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(options);
        break;

      case "ie":
        WebDriverManager.iedriver().setup();
        InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
        internetExplorerOptions.setCapability("EnableNativeEvents", false);
        internetExplorerOptions.setCapability("ignoreZoomSetting", true);
        internetExplorerOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        internetExplorerOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        driver = new InternetExplorerDriver(internetExplorerOptions);
        break;

      default:
        driver = getChromeDriver();
    }
    driver.manage().deleteAllCookies();
    driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
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

    WebDriverManager.chromedriver().setup();

    return new ChromeDriver(options);
  }

  public List<byte[]> takeFullPageScreenShot() {
    List<byte[]> screenshots = new ArrayList<byte[]>();

    try {
      JavascriptExecutor jsExec = (JavascriptExecutor) driver;

      Long windowHeight = (Long) jsExec.executeScript("return window.innerHeight;");
      Long pageHeight = (Long) jsExec.executeScript("return document.body.scrollHeight;");
      Long currentWindowScroll = 0L;

      while (currentWindowScroll <= pageHeight) {
        jsExec.executeScript("window.scrollTo(0, " + currentWindowScroll + ");");
        screenshots.add(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));

        currentWindowScroll = currentWindowScroll + windowHeight;
      }
    } catch (WebDriverException e) {
      LOG.error("ERROR: {}", e.getMessage());
    }

    return screenshots;
  }

  public void destroyDriver() {
    LOG.info("Quitting driver");
    if (driver != null) {
      driver.quit();
      driver = null;
    }
  }
}
