package automation.testing.framework.journeys.Pages;

import automation.testing.framework.config.EnvironmentHandler;
import automation.testing.framework.journeys.PageObject.Home_PO;
import org.junit.Assert;

public class HomePage {

    private final Home_PO home_po;
    private final EnvironmentHandler environmentHandler;

    public HomePage(Home_PO home_po,EnvironmentHandler environmentHandler){
        this.home_po=home_po;
        this.environmentHandler=environmentHandler;
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
