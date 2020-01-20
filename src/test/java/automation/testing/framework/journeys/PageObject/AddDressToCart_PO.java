package automation.testing.framework.journeys.PageObject;

import automation.testing.framework.selenium.BasePageObject;
import automation.testing.framework.selenium.WebDriverHandler;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddDressToCart_PO extends BasePageObject {


    @FindBy(id = "group_1")
    private WebElement size;

    @FindBy(id = "color_16")
    private WebElement yellowColour;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement addDresses;

    @FindBy(xpath = "//div[@class='layer_cart_product col-xs-12 col-md-6']//h2[1]")
    private WebElement successMessage;

    @FindBy(xpath = "//a[@class='btn btn-default button button-medium']")
    private WebElement checkOutButton;

    @FindBy(xpath = "//td[@class='cart_description']//a[contains(text(),'Printed Chiffon Dress')]")
    private WebElement dress1;

    @FindBy(xpath = "//i[@class='icon-trash']")
    private WebElement delete;

    @FindBy(xpath = "//p[@class='alert alert-warning']")
    private WebElement alertWarning;

    @FindBy(css = "#block_top_menu > ul > li.sfHoverForce > a")
    private WebElement dressTab;


    @FindBy(xpath = "//td[@class='cart_description']//a[contains(text(),'Printed Summer Dress')]")
    private WebElement PrintedDressInCart;

    public AddDressToCart_PO(WebDriverHandler driverHandler) {
        super(driverHandler);
        PageFactory.initElements(driverHandler.getDriver(), this);
    }

    public WebElement selSize() {
        return size;
    }

    public void selectColour() {
        click(yellowColour);
    }

    public void addToCart() { waitAndClick(addDresses); }

    public String getMessage() {
        return waitAndgetText(successMessage, 5);
    }

    public void ProceedCheckOut() {
        waitAndClick(checkOutButton);
    }

    public String printeDress() {
        return waitAndgetText(dress1, 5);
    }

    public void deletedItem() { click(delete); }

    public String getAlertMessage() { return waitAndgetText(alertWarning, 15);}

    public void clickDres() { clickAndWait(dressTab, 10);}

    public String assertPrintedSummerDressInCart() {
        return waitAndgetText(PrintedDressInCart,5);
    }

}
