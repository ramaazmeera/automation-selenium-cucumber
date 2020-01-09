package automation.testing.framework.journeys.Stepdef;

import automation.testing.framework.journeys.Pages.SummerdressesPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SummerDressesSteps {

    private final SummerdressesPage summerdressesPage;

    public SummerDressesSteps(SummerdressesPage summerdressesPage) {
        this.summerdressesPage = summerdressesPage;
    }

    @Given("I navigate the automationpractice url")
    public void iNavigateTheAutomationpracticeUrl() throws Throwable {
        summerdressesPage.openAutomationPage();

    }

    @Then("I should see the home page is displyed with popular and bestsellers links")
    public void iShouldSeeTheHomePageIsDisplyed() {
        summerdressesPage.assertHomepage();

    }


    @When("I click on women section")
    public void iClickOnWomenSection() {
        summerdressesPage.clickWomenDresses();
    }

    @Then("I should see tops and dresses subcategories displayed")
    public void iShouldSeeTopsAndDressesSubcategoriesDisplayed() {
        summerdressesPage.assertsubCategories();
    }

    @And("I click on dresses")
    public void iClickOnDresses() {
        summerdressesPage.clickOnDresses();

    }

    @Then("I should see dresses page displayed with subcategories")
    public void iShouldSeeDressesPageDisplayedWithSubcategories() {
        summerdressesPage.assertDressesPage();
    }

    @And("I click on summer dresses section")
    public void iClickOnSummerDressesSection() {
        summerdressesPage.clickOnSummerDresses();
    }

    @Then("I should see summer dresses dispalyed")
    public void iShouldSeeSummerDressesDispalyed() {
        summerdressesPage.assertSummerDressesPage();
    }

    @And("I sort the items by low to high price")
    public void iSortTheItemsByLowToHighPrice() {
        summerdressesPage.sortTheDresses();
    }


    @And("I clicked printed chiffon dress")
    public void iAddedPrintedChiffonDressToTheCart() {
        summerdressesPage.clickOndress1();

    }


    @And("I select size")
    public void iSelectSize() {
        summerdressesPage.selectsize();

    }

    @And("I click colour")
    public void iClickColour() {
        summerdressesPage.clickcolour();

    }

    @And("I click add to cart")
    public void iClickAddToCart() {
        summerdressesPage.clickAddToCart();
    }

    @Then("message \"([^\"]*)\" is displayed")
    public void messageIsDisplayed(String message) {
        summerdressesPage.assertMessage(message);

    }

    @And("I click proceed to checkout")
    public void iClickProceedToCheckout() {
        summerdressesPage.clickCheckOutButton();
    }

    @Then("I should see \"([^\"]*)\" is in the cart")
    public void iShouldSeeIsInTheCart(String dress1) {
        summerdressesPage.assertDress1(dress1);

    }

    @And("I cleared the cart")
    public void iClearedTheCart() {
        summerdressesPage.clearCart();
    }

    @Then("I should see \"([^\"]*)\" alert")
    public void iShouldSeeAlert(String alertMessage) {
        summerdressesPage.assertAlert(alertMessage);
    }

    @And("I click on dresses tab")
    public void iClickOnDressesTab() {
        summerdressesPage.clickOnDressesTab();
    }

    @And("I clicked printed summer dress")
    public void iClickedPrintedPrintedSummerDress() {
        summerdressesPage.clickPrintedSummerDress();
    }

    @Then("I should see Printed Summer Dress is in the cart")
    public void iShouldSeePrintedSummerDressIsInTheCart() {
        summerdressesPage.assertPrintedSummerDress();
    }
}
