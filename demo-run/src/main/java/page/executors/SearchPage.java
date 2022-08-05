package page.executors;

import com.aventstack.extentreports.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import extensions.Wait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;
import page.locators.SearchPageLocators;

import java.util.List;


public class SearchPage extends SearchPageLocators {

    public SearchPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void SelectCategory(String category) {
        Assert.assertTrue(Wait.IsElementDisplayed(Categories(category)));
        Wait.waitForElementVisibleAndClick(Categories(category));
    }

    public void SelectBrandFilter(String filter) {
        Assert.assertTrue(Wait.IsElementDisplayed(BrandFilter(filter)));
        Wait.waitForElementVisibleAndClick(BrandFilter(filter));
    }

    public void SelectFlipkartAssuredCheckbox() {
        Wait.shortSleep();
        Assert.assertTrue(Wait.IsElementDisplayed(FilpKartAssuredCheckbox));
        Wait.waitForElementVisibleAndClick(FilpKartAssuredCheckbox);
    }

    public void SelectPriceHighToLow() {
        Wait.shortSleep();
        Assert.assertTrue(Wait.IsElementDisplayed(PriceHighToLow));
        Wait.waitForElementVisibleAndClick(PriceHighToLow);
    }

    public void GetProductResults() {
        Wait.shortSleep();
        List<WebElement> products = Wait.getElements(ProductResults);
        List<WebElement> productPrices = Wait.getElements(ProductPrices);
        for ( int i =0; i<products.size(); i++){
            for( int j =0; j<productPrices.size(); j++) {
                System.out.println("Product Name  --> " + products.get(i).getText() + " Product Price --> " + productPrices.get(j).getText());

            }
        }
    }

}
