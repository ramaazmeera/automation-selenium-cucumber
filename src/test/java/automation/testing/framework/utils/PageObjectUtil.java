//package automation.testing.framework.utils;
//
//
//import automation.testing.framework.selenium.WebDriverHandler;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.StaleElementReferenceException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedCondition;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeoutException;
//
//import static com.google.code.tempusfugit.temporal.Duration.seconds;
//import static com.google.code.tempusfugit.temporal.Timeout.timeout;
//import static com.google.code.tempusfugit.temporal.WaitFor.waitOrTimeout;
//import static junit.framework.TestCase.fail;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.endsWith;
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.not;
//import static org.hamcrest.Matchers.startsWith;
//
//public class PageObjectUtil {
//
//    protected static final Logger LOG = LoggerFactory.getLogger(PageObjectUtil.class);
//    protected String pageHeader;
//    protected String pageTitle;
//    protected String urlPath = "";
////    @Value("${ui.url}")
//    private String baseUrl;
//    private final WebDriverHandler webDriverHandler;
//    private By alertMessage = By.id("alert-0-message");
//    private By alertLink = By.id("alert0-link");
//
//    public PageObjectUtil(WebDriverHandler webDriverHandler) {
//        this.webDriverHandler = webDriverHandler;
//    }
//
//    public void assertAlertBannerIsNotDisplayed() {
//        assertElementIsNotDisplayed(alertMessage);
//    }
//
//    public void assertAlertBannerTextDoesNotEqual(String text) {
//        if (getElement(alertMessage).isDisplayed()) {
//            assertElementTextDoesNotEqual(alertMessage, text);
//        } else {
//            fail("Alert banner is not displayed");
//        }
//    }
//
//    public void assertAlertBannerTextEquals(String expectedText) {
//        assertElementTextEquals(alertMessage, expectedText);
//    }
//
//    public void assertAlertLinkTextEquals(String linkText) {
//        assertElementTextEquals(alertLink, linkText);
//    }
//
//    protected void assertElementAttributeContains(By locator, String attribute, String value) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    assertThat(webDriverHandler.getDriver().findElement(locator).getAttribute(attribute), is(containsString(value)));
//                    return true;
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                } catch (AssertionError e) {
//                    LOG.error("Element attribute value is incorrect: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//    }
//
//
//    protected void assertElementAttributeEquals(By locator, String attribute, String value) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    assertThat(webDriverHandler.getDriver().findElement(locator).getAttribute(attribute), containsString(value));
//                    return true;
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                } catch (AssertionError e) {
//                    LOG.error("Element attribute value is incorrect: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//    }
//
//    protected void assertElementBackgroundColourEquals(By locator, String expectedColour) {
//        String actualColour = webDriverHandler.getDriver().findElement(locator).getCssValue("background-color");
//
//        if (expectedColour.contains("#")) {
//            //Convert from RGB into HEX
//            String[] rgbArray = actualColour.replace("rgb(", "").replace(")", "").split(",");
//            int r = Integer.parseInt(rgbArray[0].trim());
//            int g = Integer.parseInt(rgbArray[1].trim());
//            int b = Integer.parseInt(rgbArray[2].trim());
//
//            actualColour = "#" + Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
//        }
//
//        assertThat("Assert that the background colour equals", actualColour, is(equalTo(expectedColour)));
//    }
//
//    public void assertElementIsDisplayed(By locator) {
//        assertElementIsDisplayed(locator, true);
//    }
//
//    public void assertElementIsDisplayed(By locator, boolean refresh) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    WebElement element = getElement(locator, refresh);
//
//                    if (element != null &&
//                            !element.isDisplayed()) {
//
//                        LOG.warn("Element found but not displayed");
//                        if (refresh) {
//                            refreshPage();
//                        }
//
//                        return false;
//                    }
//
//                    return true;
//                } catch (StaleElementReferenceException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//    }
//
//    protected void assertElementIsEnabled(By locator) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    refreshPage();
//                    return getElement(locator).isEnabled();
//                } catch (StaleElementReferenceException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not enabled");
//        }
//    }
//
//    protected void assertElementIsSelected(By locator) {
//        assertThat(getElement(locator).isSelected(), is(true));
//    }
//
//    protected void assertElementFontWeightEquals(By locator, String expectedFontWeight) {
//        String actualFontWeight = getElement(locator).getCssValue("font-weight");
//        assertThat("Assert that the font weight is displayed", actualFontWeight, is(equalTo(expectedFontWeight)));
//    }
//
//    public void assertElementIsNotDisplayed(By locator) {
//        boolean isDisplayed = getElements(locator).size() > 0;
//        assertThat(isDisplayed, is(false));
//    }
//
//    protected void assertElementIsNotNull(By locator) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    return getElement(locator) != null;
//                } catch (StaleElementReferenceException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//    }
//
//    protected void assertElementIsChecked(By locator, boolean selected) {
//        WebElement element = getElement(locator);
//        assertThat(element.isSelected(), is(selected));
//    }
//
//    public void assertElementTextContains(By locator, String expectedContainsText) {
//        String fullText = getElementText(locator);
//        assertThat(fullText, is(containsString(expectedContainsText)));
//    }
//
//    public void assertElementTextDoesNotContain(By locator, String expectedNotContainsText) {
//        String fullText = getElementText(locator);
//        assertThat(fullText, not(containsString(expectedNotContainsText)));
//    }
//
//    private void assertElementTextDoesNotEqual(By locator, String expectedNotEqualsText) {
//        String fullText = getElementText(locator);
//        assertThat(fullText, is(not(equalTo(expectedNotEqualsText))));
//    }
//
//    public void assertElementTextEquals(By locator, String expectedText) {
//        String actualText = getElementText(locator);
//        assertThat(actualText, is(equalTo(expectedText)));
//    }
//
//    protected void assertElementTextEqualsIgnoreCase(By locator, String expectedText) {
//        String actualText = getElementText(locator).toUpperCase();
//        assertThat(actualText, is(equalTo(expectedText.toUpperCase())));
//    }
//
//    protected void assertElementTextStartsWith(By locator, String expectedText) {
//        String fullText = getElementText(locator);
//        assertThat(fullText, startsWith(expectedText));
//    }
//
//    protected void assertElementTextEndsWith(By locator, String expectedText) {
//        String fullText = getElementText(locator);
//        assertThat(fullText, endsWith(expectedText));
//    }
//
//    protected void assertElementValueEquals(By locator, String expectedValue) {
//        String actualText = getElementValue(locator);
//        assertThat(actualText, is(equalTo(expectedValue)));
//    }
//
//    public void assertErrorMessageIsDisplayedInHeader(String message) {
//        boolean errorFound = false;
//        final List<WebElement> errorMessages = new ArrayList<WebElement>();
//
//        try {
//            // Wait for errors to appear before executing assertion
//            Thread.sleep(1000);
//
//            waitOrTimeout(() -> {
//                try {
//                    errorMessages.addAll(getErrorMessagesInHeader());
//                    return errorMessages.size() > 0;
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//
//        for (WebElement errorMessage : errorMessages) {
//            if (errorMessage.getText().equals(message)) {
//                errorFound = true;
//                break;
//            }
//        }
//
//        assertThat(errorFound, is(true));
//    }
//
//    public void assertErrorMessageIsDisplayedInPage(String message) {
//        boolean errorFound = false;
//
//        for (WebElement errorMessage : getErrorMessagesInBody()) {
//            String errorText = errorMessage.getText();
//            errorText = errorText.replace("Error:\n", "");
//
//            if (errorText.equals(message)) {
//                errorFound = true;
//                break;
//            }
//        }
//
//        assertThat(errorFound, is(true));
//    }
//
//    public void assertPageTitleIsCorrect() {
//        String actualTitle = webDriverHandler.getDriver().getTitle();
//        assertThat(actualTitle, is(equalTo(pageTitle)));
//    }
//
//    protected void clearAndPopulate(By locator, String text) {
//        clearText(locator);
//        inputText(locator, text);
//    }
//
//    protected void clearText(By locator) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    getElement(locator).clear();
//                    return true;
//                } catch (StaleElementReferenceException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//    }
//
//    public void click(By locator) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    getElement(locator).click();
//                    return true;
//                } catch (StaleElementReferenceException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//    }
//
//    public void clickAlertLink() {
//        click(alertLink);
//    }
//
//    public void waitUntilUrlContains(String expectedUrl) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    String actualUrl = webDriverHandler.getDriver().getCurrentUrl();
//                    return actualUrl.contains(expectedUrl);
//                } catch (StaleElementReferenceException e) {
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Expected url to contain text:" + expectedUrl);
//        }
//    }
//
//    protected boolean elementIsDisplayed(By locator) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    return getElement(locator).isDisplayed();
//                } catch (StaleElementReferenceException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//
//        return true;
//    }
//
//    protected WebDriver getDriver() {
//        return webDriverHandler.getDriver();
//    }
//
//    protected WebElement getElement(By locator) {
//        return getElement(locator, false);
//    }
//
//    protected WebElement getElement(By locator, boolean refresh) {
//        final List<WebElement> elements = new ArrayList<WebElement>();
//
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    elements.add(webDriverHandler.getDriver().findElement(locator));
//                    return true;
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//
//                    if (refresh) {
//                        refreshPage();
//                    }
//
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//
//        return elements.get(0);
//    }
//
//    protected WebElement getElementFromElement(By parentLocator, By childLocator) {
//        WebElement parent = getElement(parentLocator);
//        return getElementFromElement(parent, childLocator);
//    }
//
//    protected WebElement getElementFromElement(WebElement element, By locator) {
//        return element.findElement(locator);
//    }
//
//    protected List<WebElement> getElements(By locator) {
//        final List<WebElement> elements = new ArrayList<WebElement>();
//
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    elements.addAll(webDriverHandler.getDriver().findElements(locator));
//                    return true;
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("No cases found in case list");
//        }
//
//        return elements;
//    }
//
//    protected List<WebElement> getElementsFromElement(By locatorParent, By locatorChild) {
//        return getElement(locatorParent).findElements(locatorChild);
//    }
//
//    protected List<WebElement> getElementsFromElement(WebElement element, By locator) {
//        return element.findElements(locator);
//    }
//
//    protected String getElementText(By locator) {
//        return webDriverHandler.getDriver().findElement(locator).getText();
//    }
//
//    private int getElementTextAsNumber(WebElement element) {
//        String text = element.getText().replaceAll("[^\\d]", "");
//        return Integer.parseInt(text);
//    }
//
//    protected int getElementTextAsNumber(By locator) {
//        WebElement element = webDriverHandler.getDriver().findElement(locator);
//        return getElementTextAsNumber(element);
//    }
//
//    private String getElementValue(By locator) {
//        WebElement element = getElement(locator);
//        return element.getAttribute("value");
//    }
//
//    private int getElementValueAsNumber(WebElement element) {
//        String text = element.getAttribute("value").replaceAll("[^\\d]", "");
//        return Integer.parseInt(text);
//    }
//
//    protected int getElementValueAsNumber(By locator) {
//        WebElement element = getElement(locator);
//        return getElementValueAsNumber(element);
//    }
//
//    private List<WebElement> getErrorMessagesInBody() {
//        By locator = By.xpath("//*[contains(@class, 'govuk-error-message') and contains(@id, '-error')]");
//        return webDriverHandler.getDriver().findElements(locator);
//    }
//
//    private List<WebElement> getErrorMessagesInHeader() {
//        WebElement errorSummary = webDriverHandler.getDriver().findElement(By.id("error-summary"));
//        return errorSummary.findElements(By.xpath("//a[contains(@id, 'error')]"));
//    }
//
//    protected Select getSelectElement(By locator) {
//        return new Select(webDriverHandler.getDriver().findElement(locator));
//    }
//
//    protected void inputText(By locator, String text) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    getElement(locator).sendKeys(text);
//                    return true;
//                } catch (StaleElementReferenceException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(10)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail("Element is not displayed");
//        }
//    }
//
//    public void open() {
//        webDriverHandler.getDriver().navigate().to(baseUrl + urlPath);
//    }
//
//    public void waitMilliSeconds(int milliSeconds) {
//        try {
//            Thread.sleep(milliSeconds);
//        } catch (InterruptedException e) {
//            //do nothing
//        }
//    }
//
//    public void waitUntilDocumentIsReady() {
//        new WebDriverWait(webDriverHandler.getDriver(), 10)
//                .until(
//                        (ExpectedCondition<Boolean>) wd -> (
//                                (JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
//                );
//    }
//
//    public void refreshPage() {
//        LOG.info("Refreshing page");
//        webDriverHandler.getDriver().navigate().refresh();
//    }
//
//    protected void scrollElementIntoView(WebElement element) {
//        ((JavascriptExecutor) webDriverHandler.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
//    }
//
//    protected void scrollElementIntoView(By locator) {
//        scrollElementIntoView(getElement(locator));
//    }
//
//    protected void waitUntilWebElementTextIsGreaterThanZero(By locator) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    refreshPage();
//                    return (getElementTextAsNumber(locator) > 0);
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(15)));
//        } catch (InterruptedException | TimeoutException e) {
//            // Do nothing - assertions will cater for inability to find expected value
//        }
//    }
//
//    protected void waitUntilElementTextNumberEquals(By locator, String expectedNumber) {
//        expectedNumber = expectedNumber.replaceAll("[^\\d]", ""); // remove non numeric chars
//        int _expectedNumber = Integer.parseInt(expectedNumber);
//        waitUntilElementTextNumberEquals(locator, _expectedNumber);
//    }
//
//    private void waitUntilElementTextNumberEquals(By locator, int expectedNumber) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    refreshPage();
//
//                    int actualNumber = getElementTextAsNumber(locator);
//                    return actualNumber == expectedNumber;
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(15)));
//        } catch (InterruptedException | TimeoutException e) {
//            // Do nothing - assertions will cater for inability to find expected value
//        }
//    }
//
//    protected void waitUntilElementTextContains(By locator, String expectedText) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    refreshPage();
//
//                    String actualText = getElementText(locator);
//                    return actualText.contains(expectedText);
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(15)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail(String.format("Element text for '%1$s' does not contain: '%2$s' after 15 seconds", locator, expectedText));
//        }
//    }
//
//    protected void waitUntilElementTextEquals(By locator, String expectedText) {
//        try {
//            waitOrTimeout(() -> {
//                try {
//                    refreshPage();
//
//                    String actualText = getElementText(locator);
//                    return actualText.equals(expectedText);
//                } catch (NoSuchElementException e) {
//                    LOG.error("Cannot locate intended element: {}", e.getMessage());
//                    return false;
//                }
//            }, timeout(seconds(15)));
//        } catch (InterruptedException | TimeoutException e) {
//            fail(String.format("Element text for '%1$s' does not equal: '%2$s' after 15 seconds", locator, expectedText));
//        }
//    }
//}
