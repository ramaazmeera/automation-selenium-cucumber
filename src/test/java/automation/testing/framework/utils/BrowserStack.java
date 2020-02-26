//package automation.testing.framework.utils;
//
//import automation.testing.framework.config.EnvironmentHandler;
//import org.apache.commons.lang3.StringUtils;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
//
//import java.net.URL;
//
//public class BrowserStack {
//
//    public static final String USERNAME = "tes1";
//    public static final String AUTOMATE_KEY = "xyz";
//    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
//
//    private final EnvironmentHandler env;
//
//    private WebDriver driver;
//
//    public BrowserStack(EnvironmentHandler environmentHandler) {
//        this.env = environmentHandler;
//    }
//
//    public WebDriver getDriver() {
//        return driver;
//    }
//
//    public void initWebDriver() throws Exception {
//
//        String browserName = StringUtils.defaultIfBlank(env.valueOf("browser"), "chrome");
//
//        System.out.println("Getting driver for " + browserName);
//
//        WebDriver driver;
//        switch (browserName) {
//
//            case "Firefox":
//                DesiredCapabilities caps = new DesiredCapabilities();
//                caps.setCapability("os", "Windows");
//                caps.setCapability("os_version", "10");
//                caps.setCapability("browser", "Firefox");
//                caps.setCapability("browser_version", "69.0");
//                caps.setCapability("browserstack.local", "false");
//                caps.setCapability("browserstack.debug", "true");
//                driver = new RemoteWebDriver(new URL(URL), caps);
//                driver.manage().window().maximize();
//
//                break;
//
//            case "IE":
//                DesiredCapabilities caps1 = new DesiredCapabilities();
//                caps1.setCapability("os", "Windows");
//                caps1.setCapability("os_version", "10");
//                caps1.setCapability("browser", "IE");
//                caps1.setCapability("browser_version", "11.0");
//                caps1.setCapability("browserstack.local", "false");
//                driver = new RemoteWebDriver(new URL(URL), caps1);
//                driver.manage().window().maximize();
//
//                break;
//
//            default:
//                DesiredCapabilities caps3 = new DesiredCapabilities();
//                caps3.setCapability("os", "Windows");
//                caps3.setCapability("os_version", "10");
//                caps3.setCapability("browser", "Chrome");
//                caps3.setCapability("browser_version", "62.0");
//                caps3.setCapability("browserstack.local", "false");
//                caps3.setCapability("browserstack.debug", "true");
//                driver = new RemoteWebDriver(new URL(URL), caps3);
//                driver.manage().window().maximize();
//
//        }
//    }
//
//
//
//    public void closeDriver() {
//        driver.quit();
//    }
//
//}
