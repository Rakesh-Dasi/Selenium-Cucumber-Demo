package page.executors;

import dataProviders.ConfigFileReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.cucumber.listener.Reporter;

import extensions.Wait;
import org.testng.Assert;
import page.locators.HomePageLocators;


public class HomePage extends HomePageLocators {

    ConfigFileReader configFileReader;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToWebsite() {
        configFileReader = new ConfigFileReader();
        driver.get(configFileReader.getApplicationUrl());
        Reporter.addStepLog("URL is " + configFileReader.getApplicationUrl());
    }

    public void ClosePopUp() {
        Wait.shortSleep();
        Assert.assertTrue(Wait.IsElementDisplayed(CloseButton));
        Wait.waitForElementVisibleAndClick(CloseButton);
    }

    public void SearchForProduct(String searchItem){
        Assert.assertTrue(Wait.IsElementDisplayed(SearchBar));
        Wait.waitForElementLocatorAndSetValue(SearchBar, searchItem);
        Wait.waitForElementVisibleAndClick(SearchIcon);
    }

    public void SelectCategory(String category){
        Wait.shortSleep();
        Assert.assertTrue(Wait.IsElementDisplayed(Categories(category)));
        Wait.waitForElementVisibleAndClick(Categories(category));
    }

    public void SelectBrandFilter(String filter){
        Assert.assertTrue(Wait.IsElementDisplayed(BrandFilter(filter)));
        Wait.waitForElementVisibleAndClick(BrandFilter(filter));
    }


}
