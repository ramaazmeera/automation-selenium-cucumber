package automation.testing.framework.journeys.Pages;

import automation.testing.framework.journeys.PageObject.AddDressToCart_PO;
import automation.testing.framework.selenium.WebDriverHandler;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AddDressToCartPage {

    private final AddDressToCart_PO addDressToCart_po;
    private final WebDriverHandler webDriverHandler;

    public AddDressToCartPage(AddDressToCart_PO addDressToCart_po, WebDriverHandler webDriverHandler) {
        this.addDressToCart_po = addDressToCart_po;
        this.webDriverHandler = webDriverHandler;
    }

    public void selectsize() {
        Select size = new Select(addDressToCart_po.selSize());
        size.selectByValue("1");
    }

    public void clickcolour() {
        addDressToCart_po.selectColour();
    }

    public void clickAddToCart() {
        addDressToCart_po.addToCart();
    }

    public void assertMessage(String message) {
        assertThat("Message not displayed", addDressToCart_po.getMessage(), is(message));
    }

    public void clickCheckOutButton() {
        addDressToCart_po.ProceedCheckOut();
    }

    public void assertDress1(String dress1) {
        assertThat("printed dress not added", addDressToCart_po.printeDress(), is(dress1));
    }

    public void clearCart() {
        addDressToCart_po.deletedItem();
    }

    public void assertAlert(String alert) {
        assertThat("Alert message not displayed", addDressToCart_po.getAlertMessage(), is(alert));
    }

    public void clickOnDressesTab() {
        addDressToCart_po.clickDres();
    }

    public void clickPrintedSummerDress() {
        addDressToCart_po.clickPrintedDress();
        addDressToCart_po.waitForPageToLoad();

// Please UNCOMMENT the below line of code if you want to the run this test in windows operating system using Chrorme browser as this website is using iframes when user clicks printed summer dress,

// Please COMMENT the below line of code if you want to the run this test in below environments and browsers:
// Windows with Firefox AND
// MAC with any browser

        //webDriverHandler.getDriver().switchTo().frame(0);

        Assert.assertTrue(addDressToCart_po.summerDressDisplayed());
    }

    public void assertPrintedSummerDress() {
        assertThat("printed dress not added", addDressToCart_po.assertPrintedSummerDressInCart(), is("Printed Summer Dress"));
    }


}
