package automation.testing.framework.journeys.PageObject;

import automation.testing.framework.selenium.BasePageObject;
import automation.testing.framework.selenium.WebDriverHandler;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home_PO extends BasePageObject {

    @FindBy(xpath = "//img[@class='logo img-responsive']")
    private WebElement homePageLogo;

    @FindBy(xpath = "//*[contains(text(),'Popular')]")
    private WebElement popular;

    @FindBy(xpath = "//*[contains(text(),'Best Sellers')]")
    private WebElement bestSeller;

    @FindBy(linkText = "Women")
    private WebElement womenLink;

    @FindBy(css = "#block_top_menu > ul > li:nth-child(1) > a")
    private WebElement womenTab;

    @FindBy(css = "#block_top_menu > ul > li:nth-child(1) > ul > li:nth-child(2) > ul > li:nth-child(3) > a")
    private  WebElement summerDresses;



    public Home_PO(WebDriverHandler driverHandler) {
        super(driverHandler);
        PageFactory.initElements(driverHandler.getDriver(), this);
    }

    public boolean homePageLogoDisplayed() {
        return homePageLogo.isDisplayed();
    }

    public boolean popularLinkDisplayed() {
        return popular.isDisplayed();
    }

    public boolean bestsellerLinkDisplayed() {
        return bestSeller.isDisplayed();
    }

    public void clickWomen() {
        clickAndWait(womenLink, 5);
    }

    public WebElement hooverToWomen() {
        return womenTab;

    }

    public WebElement hooverToSummerDresss() {
        return summerDresses;
    }

    public void clickSDress() {
        clickAndWait(summerDresses,15);
    }
}
