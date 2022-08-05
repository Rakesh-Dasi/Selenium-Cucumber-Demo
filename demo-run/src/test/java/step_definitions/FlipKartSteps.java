package step_definitions;

import cucumber.TestContext;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import page.executors.HomePage;
import page.executors.SearchPage;

import java.util.List;
import java.util.Map;

public class FlipKartSteps {
    TestContext testContext;
    HomePage homePage;
    Scenario scenario;
    SearchPage searchPage;

    public FlipKartSteps(TestContext context) {
        testContext = context;
        homePage = testContext.getPageObjectManger().getHomePage();
        searchPage = testContext.getPageObjectManger().getSearchPage();
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("^I navigate to Flipkart$")
    public void iNavigateToFlipkart() {
        homePage.navigateToWebsite();
        homePage.ClosePopUp();
    }

    @And("^I search for \"([^\"]*)\" and select \"([^\"]*)\" in Categories$")
    public void iSearchForAndSelectInCategories(String product, String category) throws Throwable {
        homePage.SearchForProduct(product);
        homePage.SelectCategory(category);
        Thread.sleep(4000);
    }

    @And("^I set the following filters and select Flipkart Assured$")
    public void iSetTheFollowingFiltersAndSelectFlipkartAssured(DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> values = dataTable.asMaps(String.class, String.class);
        for(Map<String, String> value:values ){
            homePage.SelectBrandFilter( value.get("Brand"));
        }
        searchPage.SelectFlipkartAssuredCheckbox();
        Thread.sleep(6000);
    }

    @And("^I sort the Price from High to Low$")
    public void iSortThePriceFromHighToLow() {
        searchPage.SelectPriceHighToLow();
    }

    @Then("^I capture all the results on the First Page$")
    public void iCaptureAllTheResultsOnTheFirstPage() {
        searchPage.GetProductResults();
    }
}