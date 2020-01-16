package automation.testing.framework.journeys.PageObject;

import automation.testing.framework.selenium.BasePageObject;
import automation.testing.framework.selenium.WebDriverHandler;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Dresses_PO extends BasePageObject {

    @FindBy(xpath = "//a[@class='subcategory-name'][contains(text(),'Tops')]")
    private WebElement tops;

    @FindBy(xpath = "//a[@class='subcategory-name'][contains(text(),'Dresses')]")
    private WebElement dresses;

    @FindBy(xpath = "//*[@class='subcategory-heading']")
    private WebElement subcategoryHeading;

    @FindBy(xpath = "//a[@class='subcategory-name'][contains(text(),'Casual Dresses')]")
    private WebElement casualDresses;

    @FindBy(css = "#subcategories > ul > li:nth-child(3) > div.subcategory-image > a > img")
    private WebElement summerDressesSection;

    @FindBy(xpath = "//*[@class='category-name']")
    private WebElement summerDressesPage;

    @FindBy(id = "selectProductSort")
    private WebElement dropdown;

    @FindBy(xpath = "//*[@id=\"center_column\"]/ul/li[3]/div/div[2]/h5/a")
    private WebElement printedDress;

    @FindBy(css = "#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-line.last-item-of-tablet-line.last-mobile-line > div > div.right-block > h5 > a")
    private WebElement printedSummerDress;

    @FindBy(id = "bigpic")
    private WebElement summerDressDisplay;


    public Dresses_PO(WebDriverHandler driverHandler) {
        super(driverHandler);
        PageFactory.initElements(driverHandler.getDriver(), this);
    }

    public String assertTops() { return getText(tops); }

    public String assertDresses() {
        return getText(dresses);
    }

    public void clickDresses() {
        clickAndWait(dresses, 15);
    }

    public String assertSubcategoryHeading() { return getText(subcategoryHeading); }

    public String assertCasualDresses() {
        return getText(casualDresses);
    }

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

    public void clickPrintedDress() { clickAndWait(printedSummerDress, 30); }

    public boolean summerDressDisplayed() {
        return summerDressDisplay.isDisplayed();
    }
}
