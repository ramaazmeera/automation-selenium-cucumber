package automation.testing.framework.journeys.Pages;

import automation.testing.framework.journeys.PageObject.Dresses_PO;
import org.openqa.selenium.support.ui.Select;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DresssesPage {
    private final Dresses_PO dresses_po;

    public DresssesPage(Dresses_PO dresses_po){
        this.dresses_po=dresses_po;
    }

    public void assertsubCategories() {
        assertThat("Tops subcategory not displayed", dresses_po.assertTops().equalsIgnoreCase("Tops"));
        assertThat("Dresses subcategory not displayed", dresses_po.assertDresses().equalsIgnoreCase("Dresses"));
    }

    public void clickOnDresses() {
        dresses_po.clickDresses();
    }

    public void assertDressesPage() {
        assertThat("Subcategory heading not displayed", dresses_po.assertSubcategoryHeading(), is("Subcategories"));
        assertThat("Casual dresses subcategory not displayed", dresses_po.assertCasualDresses().equalsIgnoreCase("Casual Dresses"));
    }

    public void clickOnSummerDresses() {
        dresses_po.clickSummerDresses();
    }

    public void assertSummerDressesPage() {
        assertThat("Summer dressses page not displayed", dresses_po.summerDressesPage(), is("Summer Dresses"));
    }

    public void sortTheDresses() {
        Select se = new Select(dresses_po.selectLowPrice());
        se.selectByVisibleText("Price: Lowest first");
    }

    public void clickOndress1() {
        dresses_po.selectDress();
        dresses_po.waitForPageToLoad();
    }

}
