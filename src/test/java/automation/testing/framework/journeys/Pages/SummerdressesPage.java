package automation.testing.framework.journeys.Pages;

import automation.testing.framework.config.EnvironmentHandler;
import automation.testing.framework.journeys.PageObject.SummerDresses_PO;
import automation.testing.framework.selenium.WebDriverHandler;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SummerdressesPage {

    private final SummerDresses_PO summerDresses_po;
    private final EnvironmentHandler environmentHandler;

    private final WebDriverHandler webDriverHandler;

    public SummerdressesPage(SummerDresses_PO summerDresses_po, EnvironmentHandler environmentHandler, WebDriverHandler webDriverHandler) {
        this.summerDresses_po = summerDresses_po;
        this.environmentHandler = environmentHandler;
        this.webDriverHandler = webDriverHandler;
    }

    public void openAutomationPage() {
        String sdpUIBaseUrl = environmentHandler.valueOf("UIBaseUrl");
        summerDresses_po.openPage(sdpUIBaseUrl);
    }

    public void assertHomepage() {
        Assert.assertTrue(summerDresses_po.homePageLogoDisplayed());
        Assert.assertTrue(summerDresses_po.popularLinkDisplayed());
        Assert.assertTrue(summerDresses_po.bestsellerLinkDisplayed());
    }

    public void clickWomenDresses() {
        summerDresses_po.clickWomen();
    }

    public void assertsubCategories() {
        assertThat("Tops subcategory not displayed", summerDresses_po.assertTops().equalsIgnoreCase("Tops"));
        assertThat("Dresses subcategory not displayed", summerDresses_po.assertDresses().equalsIgnoreCase("Dresses"));
    }

    public void clickOnDresses() {
        summerDresses_po.clickDresses();
    }

    public void assertDressesPage() {
        assertThat("Subcategory heading not displayed", summerDresses_po.assertSubcategoryHeading(), is("Subcategories"));
        assertThat("Casual dresses subcategory not displayed", summerDresses_po.assertCasualDresses().equalsIgnoreCase("Casual Dresses"));
    }

    public void clickOnSummerDresses() {
        summerDresses_po.clickSummerDresses();
    }

    public void assertSummerDressesPage() {
        assertThat("Summer dressses page not displayed", summerDresses_po.summerDressesPage(), is("Summer Dresses"));
    }

    public void sortTheDresses() {
        Select se = new Select(summerDresses_po.selectLowPrice());
        se.selectByVisibleText("Price: Lowest first");
    }

    public void clickOndress1() {
        summerDresses_po.selectDress();
        //  Taking little bit longer to load the page so adding below method
        summerDresses_po.waitForPageToLoad();
    }

    public void selectsize() {
        Select size = new Select(summerDresses_po.selSize());
        size.selectByValue("1");
    }

    public void clickcolour() {
        summerDresses_po.selectColour();
    }

    public void clickAddToCart() {
        summerDresses_po.addToCart();
    }

    public void assertMessage(String message) {
        assertThat("Message not displayed", summerDresses_po.getMessage(), is(message));
    }

    public void clickCheckOutButton() {
        summerDresses_po.ProceedCheckOut();
    }

    public void assertDress1(String dress1) {
        assertThat("printed dress not added", summerDresses_po.printeDress(), is(dress1));
    }

    public void clearCart() {
        summerDresses_po.deletedItem();
    }

    public void assertAlert(String alert) {
        assertThat("Alert message not displayed", summerDresses_po.getAlertMessage(), is(alert));
    }

    public void clickOnDressesTab() {
        summerDresses_po.clickDres();
    }

    public void clickPrintedSummerDress() {
        summerDresses_po.clickPrintedDress();
    //  Taking little bit longer to load the page so added below method
        summerDresses_po.waitForPageToLoad();
    //  Need to add below code when we run in windows because this website opening iframe when I click on dress in chrome chrome browser,
        //  firefox browser is working fine without iframe
    //  webDriverHandler.getDriver().switchTo().frame(0);

        Assert.assertTrue(summerDresses_po.summerDressDisplayed());
    }

    public void assertPrintedSummerDress() {
        assertThat("printed dress not added", summerDresses_po.assertPrintedSummerDressInCart(), is("Printed Summer Dress"));
    }
}
