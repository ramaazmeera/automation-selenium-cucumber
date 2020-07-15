package automation.testing.framework.selenium;

import automation.testing.framework.utils.TextIsntPresentException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Duration;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public abstract class BasePageObject {
    protected String pageHeader;
    protected static final Logger LOG = LoggerFactory.getLogger(BasePageObject.class);
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(10);
    static Long maxWaitTime = 15L;

    private WebDriverHandler webDriverHandler;

    public BasePageObject(WebDriverHandler webDriverHandler) {
        this.webDriverHandler = webDriverHandler;
    }

    public WebDriver driver() {
        return this.webDriverHandler.getDriver();
    }

    public void openPage(String url) {
        LOG.info("Navigating to URL: {}", url);
        this.driver().get(url);
        LOG.info("Url opened: " + this.driver().getCurrentUrl());
    }

    public String getTitle() {
        try {
            return driver().getTitle();
        } catch (Exception e) {
            logError();
            throw e;
        }
    }

    public String getUrl() {
        try {
            return driver().getCurrentUrl();
        } catch (Exception e) {
            logError();
            throw e;
        }
    }

    protected String getText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            logError();
            throw e;
        }
    }

    protected String getAttribute(WebElement element, String attributeName) {
        try {
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            logError();
            throw e;
        }
    }

    private String getElementValue(WebElement element) {
        return element.getAttribute("value");
    }

    protected void assertElementValueEquals(WebElement element, String expectedValue) {
        String actualText = getElementValue(element);
        assertThat(actualText, is(equalTo(expectedValue)));
    }

    private int getElementTextAsNumber(WebElement element) {
        String text = element.getText().replaceAll("[^\\d]", "");
        return Integer.parseInt(text);
    }

    protected void selectOption(WebElement element, String value) {
        try {
            element.sendKeys(value);
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

    protected void sendKey(WebElement element, String value) {
        try {
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            logError();
            throw e;
        }
    }

    protected void clickAndWait(WebElement element, WebElement expectecElement) {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver(), maxWaitTime);
            element.click();
            wait.until(ExpectedConditions.visibilityOf(expectecElement));
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

    public String waitAndgetText(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver(), maxWaitTime);
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

    public void waitForPageToLoad() {
        wait(1);
        JavascriptExecutor js = (JavascriptExecutor) webDriverHandler.getDriver();

        for (String state = (String) js.executeScript("return document.readyState", new Object[0]);
             !state.equals("complete");
             state = (String) js.executeScript("return document.readyState", new Object[0])) {

        }
    }

    public void wait(int timeToWaitInSec) {
        try {
            Thread.sleep(timeToWaitInSec * 1000L);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error(e.getClass().getName(), e);
        }
    }

    public void waitforpage() {
        WebDriverWait wait = new WebDriverWait(webDriverHandler.getDriver(), maxWaitTime);
        wait.until(
                (ExpectedCondition<Boolean>)
                        wd ->
                                ((JavascriptExecutor) wd)
                                        .executeScript("return document.readyState")
                                        .equals("complete"));
    }


    public void refreshPage() {
        LOG.info("Refreshing page");
        this.driver().navigate().refresh();
    }

    public void untilTextisPresent(String text) throws TextIsntPresentException {
        if (!isTextPresent(text)) {
            throw new TextIsntPresentException(
                    String.format("[ERROR] This text isnt present on this page: %s", text));
        }
    }

    public boolean isTextPresent(String text) {
        boolean testIsPresent = true;

        try {
            WebDriverWait wait = new WebDriverWait(this.driver(), maxWaitTime);
            wait.until(
                    ExpectedConditions.visibilityOf(
                            this.driver()
                                    .findElement(By.xpath(String.format("//*[contains(text(),\"%s\")]", text)))));
            return testIsPresent;
        } catch (Exception var4) {
            testIsPresent = false;
            return testIsPresent;
        }
    }

    protected boolean isTextPresent(WebElement element, String expectedTextKey) {
        String actualText = element.getText().trim();
        String expectedText = (expectedTextKey);
        if (actualText.equals(expectedText)) return true;
        else return false;
    }

    public String findActiveElement() {
        WebElement element = this.driver().switchTo().activeElement();
        String focusedElement = element.getAttribute("id");
        return focusedElement;
    }

    public void clickByLinkText(String selector) {
        this.driver().findElement(By.partialLinkText(selector)).click();
    }

    public void enterDataInActiveField(String value) {
        WebElement element = this.driver().switchTo().activeElement();
        element.clear();
        element.sendKeys(value);
    }

    public void clickActiveFieldRadio() {
        WebElement element = this.driver().switchTo().activeElement();
        element.click();
    }

    public void uploadFile(WebElement element, String data) {
        element.sendKeys(data);
    }

    public boolean elementIsDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            logError();
            throw e;
        }
    }

    private List<WebElement> getErrorMessagesInBody() {
        By locator =
                By.xpath("//*[contains(@class, 'govuk-error-message') and contains(@id, '-error')]");
        return this.driver().findElements(locator);
    }

    private List<WebElement> getErrorMessagesInHeader(String message) {
        By locator = By.xpath(String.format("//*[contains(text(),\"%s\")]", message));
        return this.driver().findElements(locator);
    }

    public void assertErrorMessageIsDisplayedInHeader(String message) {
        boolean errorFound = false;

        for (WebElement errorMessage : getErrorMessagesInHeader(message)) {
            if (errorMessage.getText().equals(message)) {
                errorFound = true;
                break;
            }
        }

        assertThat(errorFound, is(true));
    }

    public void assertErrorMessageIsDisplayedInPage(String message) {
        boolean errorFound = false;

        for (WebElement errorMessage : getErrorMessagesInBody()) {
            String errorText = errorMessage.getText();
            errorText = errorText.replace("Error:\n", "");

            if (errorText.equals(message)) {
                errorFound = true;
                break;
            }
        }

        assertThat(errorFound, is(true));
    }

    public void assertElementIsDisplayed(WebElement element) {
        elementIsDisplayed(element);
    }

    public void assertElementTextEquals(WebElement element, String expectedText) {
        String actualText = getText(element);
        assertThat(actualText, is(equalTo(expectedText)));
    }

    public void DropFile(File filePath, WebElement element, int offsetX, int offsetY) {
        if (!filePath.exists()) throw new WebDriverException("File not found: " + filePath.toString());
        JavascriptExecutor jse = (JavascriptExecutor) this.driver();
        WebDriverWait wait = new WebDriverWait(this.driver(), 30);
        String JS_DROP_FILE =
                "var target = arguments[0],"
                        + "    offsetX = arguments[1],"
                        + "    offsetY = arguments[2],"
                        + "    document = target.ownerDocument || document,"
                        + "    window = document.defaultView || window;"
                        + ""
                        + "var input = document.createElement('INPUT');"
                        + "input.type = 'file';"
                        + "input.style.display = 'none';"
                        + "input.onchange = function () {"
                        + "  var rect = target.getBoundingClientRect(),"
                        + "      x = rect.left + (offsetX || (rect.width >> 1)),"
                        + "      y = rect.top + (offsetY || (rect.height >> 1)),"
                        + "      dataTransfer = { files: this.files };"
                        + ""
                        + "  ['dragenter', 'dragover', 'drop'].forEach(function (name) {"
                        + "    var evt = document.createEvent('MouseEvent');"
                        + "    evt.initMouseEvent(name, !0, !0, window, 0, 0, 0, x, y, !1, !1, !1, !1, 0, null);"
                        + "    evt.dataTransfer = dataTransfer;"
                        + "    target.dispatchEvent(evt);"
                        + "  });"
                        + ""
                        + "  setTimeout(function () { document.body.removeChild(input); }, 25);"
                        + "};"
                        + "document.body.appendChild(input);"
                        + "return input;";
        WebElement input = (WebElement) jse.executeScript(JS_DROP_FILE, element, offsetX, offsetY);
        input.sendKeys(filePath.getAbsoluteFile().toString());
        wait.until(ExpectedConditions.stalenessOf(input));
    }
}

