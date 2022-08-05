package managers;

import org.openqa.selenium.WebDriver;

import page.executors.*;

public class PageObjectManager {
    private final WebDriver driver;

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    private HomePage homePage;

    private SearchPage searchPage;

    public HomePage getHomePage() {
        return (homePage == null) ? homePage = new HomePage(driver) : homePage;
    }

    public SearchPage getSearchPage() {
        return (searchPage == null) ? searchPage = new SearchPage(driver) : searchPage;
    }
}

