package automation.testing.framework.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePageObject {
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(10);
    static Long maxWaitTime = 15L;

    private WebDriverHandler webDriverHandler;

    public BasePageObject(WebDriverHandler webDriverHandler) {
        this.webDriverHandler = webDriverHandler;
        PageFactory.initElements(webDriverHandler.getDriver(), this);
    }

    public WebDriver driver() {
        return this.webDriverHandler.getDriver();
    }

    public void openPage(String url) {
        System.out.println("Opening page: " + url);
        this.driver().get(url);
        System.out.println("Url opened: " + this.driver().getCurrentUrl());
    }

    protected String getText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            logError();
            throw e;
        }
    }


    protected void click(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            logError();
            throw e;
        }
    }


    protected void clickAndWait(WebElement element, final int timeoutInSeconds) {
        try {
            element.click();
            WebDriverWait wait = new WebDriverWait(this.driver(), timeoutInSeconds);
        } catch (Exception e) {
            logError();
            throw e;
        }
    }
    protected void waitAndClick(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver(), maxWaitTime);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            logError();
            throw e;
        }
    }
    public String waitAndgetText(WebElement element, final int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver(), timeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.getText();
        } catch (Exception e) {
            logError();
            throw e;
        }
    }

    private void logError() {
        System.out.println("Current URL: " + driver().getCurrentUrl());
        System.out.println("Page source: " + driver().getPageSource());
    }



    public  void waitForPageToLoad()  {
        wait(2);
        JavascriptExecutor js = (JavascriptExecutor)webDriverHandler.getDriver();

        for(String state = (String)js.executeScript("return document.readyState", new Object[0]); !state.equals("complete"); state = (String)js.executeScript("return document.readyState", new Object[0])) {
            wait(2);
        }

    }

    public  void wait(int timeToWaitInSec) {
        try {
            Thread.sleep((long)(timeToWaitInSec * 1000));
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        }

    }


}

