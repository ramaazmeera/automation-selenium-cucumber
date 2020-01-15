package automation.testing.framework.journeys.Stepdef;


import automation.testing.framework.journeys.Pages.DresssesPage;
import automation.testing.framework.journeys.Pages.HomePage;
import automation.testing.framework.journeys.Pages.AddDressToCartPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AddDressToCartSteps {

    private final AddDressToCartPage AddDressToCartPage;
    private final HomePage homePage;
    private final DresssesPage dresssesPage;

    public AddDressToCartSteps(AddDressToCartPage AddDressToCartPage, HomePage homePage, DresssesPage dresssesPage) {
        this.AddDressToCartPage = AddDressToCartPage;
        this.homePage=homePage;
        this.dresssesPage=dresssesPage;
    }

    @Given("I navigate to the automationpractice url")
    public void iNavigateTheAutomationpracticeUrl() {homePage.openAutomationPage();}

    @Then("I should see the home page displayed")
    public void iShouldSeeTheHomePageIsDisplayed() { homePage.assertHomepage();}

    @When("I click on women section")
    public void iClickOnWomenSection() { homePage.clickWomenDresses(); }

    @Then("I should see tops and dresses subcategories displayed in women page")
    public void iShouldSeeTopsAndDressesSubcategoriesDisplayed() { dresssesPage.assertsubCategories(); }

    @And("I click on dresses")
    public void iClickOnDresses() { dresssesPage.clickOnDresses(); }

    @Then("I should see dresses page displayed with subcategories")
    public void iShouldSeeDressesPageDisplayedWithSubcategories() { dresssesPage.assertDressesPage(); }

    @And("I click on summer dresses section")
    public void iClickOnSummerDressesSection() {
        dresssesPage.clickOnSummerDresses();
    }

    @Then("I should see summer dresses displayed")
    public void iShouldSeeSummerDressesDisplayed() { dresssesPage.assertSummerDressesPage();}

    @And("I sort the items by low to high price")
    public void iSortTheItemsByLowToHighPrice() {
        dresssesPage.sortTheDresses();
    }

    @When("I click printed chiffon dress")
    public void iAddedPrintedChiffonDressToTheCart() { dresssesPage.clickOndress1(); }

    @And("I select size")
    public void iSelectSize() { AddDressToCartPage.selectsize(); }

    @And("I select colour")
    public void iSelectColour() { AddDressToCartPage.clickcolour();}

    @And("I click add to cart")
    public void iClickAddToCart() {
        AddDressToCartPage.clickAddToCart();
    }

    @Then("the message \"([^\"]*)\" is displayed")
    public void messageIsDisplayed(String message) { AddDressToCartPage.assertMessage(message); }

    @And("I click proceed to checkout")
    public void iClickProceedToCheckout() {
        AddDressToCartPage.clickCheckOutButton();
    }

    @Then("I should see \"([^\"]*)\" is in the cart")
    public void iShouldSeeIsInTheCart(String dress1) { AddDressToCartPage.assertDress1(dress1); }

    @And("I cleared the cart")
    public void iClearedTheCart() {
        AddDressToCartPage.clearCart();
    }

    @Then("I should see \"([^\"]*)\" alert")
    public void iShouldSeeAlert(String alertMessage) {
        AddDressToCartPage.assertAlert(alertMessage);
    }

    @And("I click on dresses tab")
    public void iClickOnDressesTab() {
        AddDressToCartPage.clickOnDressesTab();
    }

    @When("I selected another summer dress")
    public void iClickedOnPrintedSummerDress() {
        AddDressToCartPage.clickPrintedSummerDress();
    }

    @Then("I should see Printed Summer Dress is in the cart")
    public void iShouldSeePrintedSummerDressIsInTheCart() {
        AddDressToCartPage.assertPrintedSummerDress();
    }
}
