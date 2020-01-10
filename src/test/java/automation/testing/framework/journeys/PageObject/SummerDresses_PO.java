package automation.testing.framework.journeys.PageObject;

import automation.testing.framework.selenium.BasePageObject;
import automation.testing.framework.selenium.WebDriverHandler;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SummerDresses_PO extends BasePageObject {

    @FindBy(xpath = "//img[@class='logo img-responsive']")
    private WebElement homePageLogo;

    @FindBy(xpath = "//*[contains(text(),'Popular')]")
    private WebElement popular;

    @FindBy(xpath = "//*[contains(text(),'Best Sellers')]")
    private WebElement bestSeller;

    @FindBy(linkText = "Women")
    private WebElement womenLink;

    @FindBy(xpath = "//a[@class='subcategory-name'][contains(text(),'Tops')]")
    private WebElement tops;

    @FindBy(xpath = "//a[@class='subcategory-name'][contains(text(),'Dresses')]")
    private WebElement dresses;

    @FindBy(xpath = "//a[@class='subcategory-name'][contains(text(),'Casual Dresses')]")
    private WebElement casualDresses;

    @FindBy(xpath = "//a[@class='subcategory-name'][contains(text(),'Dresses')]")
    private WebElement eveningDresses;

    @FindBy(xpath = "//a[@class='subcategory-name'][contains(text(),'Dresses')]")
    private WebElement summerDresses;

    @FindBy(xpath = "//*[@class='subcategory-heading']")
    private WebElement subcategoryHeading;

    @FindBy(css = "#subcategories > ul > li:nth-child(3) > div.subcategory-image > a > img")
    private WebElement summerDressesSection;

    @FindBy(xpath = "//*[@class='category-name']")
    private WebElement summerDressesPage;

    @FindBy(id = "selectProductSort")
    private WebElement dropdown;

    @FindBy(css = "img[title*='Chiffon Dress']")
    private WebElement printedDress;

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

    @FindBy(css = ".last-line.last-item-of-tablet-line .product_img_link > img.replace-2x.img-responsive")

    private WebElement printedSummerDress;

    @FindBy(id = "bigpic")
    private WebElement summerDressDisplay;

    @FindBy(xpath = "//td[@class='cart_description']//a[contains(text(),'Printed Summer Dress')]")
    private WebElement PrintedDressInCart;

    public SummerDresses_PO(WebDriverHandler driverHandler) {
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

    public String assertTops() { return getText(tops); }

    public String assertDresses() {
        return getText(dresses);
    }

    public void clickDresses() {
        clickAndWait(dresses, 15);
    }

    public String assertCasualDresses() {
        return getText(casualDresses);
    }

    public String assertSubcategoryHeading() { return getText(subcategoryHeading); }

    public void clickSummerDresses() {
        clickAndWait(summerDressesSection, 15);
    }

    public String summerDressesPage() {
        return getText(summerDressesPage);
    }

    public WebElement selectLowPrice() {
        return dropdown;
    }

    public void selectDress() {
        clickAndWait(printedDress, 30);
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

    public String getAlertMessage() {
        return waitAndgetText(alertWarning, 15);
    }

    public void clickDres() {
        clickAndWait(dressTab, 10);
    }

    public void clickPrintedDress() { clickAndWait(printedSummerDress, 30); }

    public boolean summerDressDisplayed() {
        return summerDressDisplay.isDisplayed();
    }

    public String assertPrintedSummerDressInCart() {
        return waitAndgetText(PrintedDressInCart,5);
    }
}
