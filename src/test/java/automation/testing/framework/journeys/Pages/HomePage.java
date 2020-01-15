package automation.testing.framework.journeys.Pages;

import automation.testing.framework.config.EnvironmentHandler;
import automation.testing.framework.journeys.PageObject.Home_PO;
import automation.testing.framework.selenium.WebDriverHandler;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomePage {

    private final Home_PO home_po;
    private final EnvironmentHandler environmentHandler;
    private final WebDriverHandler webDriverHandler;

    public HomePage(Home_PO home_po,EnvironmentHandler environmentHandler,WebDriverHandler webDriverHandler){
        this.home_po=home_po;
        this.environmentHandler=environmentHandler;
        this.webDriverHandler=webDriverHandler;
    }

    public void openAutomationPage() {
        String sdpUIBaseUrl = environmentHandler.valueOf("UIBaseUrl");
        home_po.openPage(sdpUIBaseUrl);
    }

    public void assertHomepage() {
        Assert.assertTrue(home_po.homePageLogoDisplayed());
        Assert.assertTrue(home_po.popularLinkDisplayed());
        Assert.assertTrue(home_po.bestsellerLinkDisplayed());
    }

    public void clickWomenDresses() { home_po.clickWomen();}

}
