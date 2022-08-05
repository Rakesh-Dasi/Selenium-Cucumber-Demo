package page.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPageLocators extends BasePage {

    public SearchPageLocators(WebDriver driver) {
        super(driver);
    }

    public static final By FilpKartAssuredCheckbox = By.xpath("(//input[@type='checkbox']/following::div/img[contains(@src,'flixcart')])[1]");

    public static final By PriceHighToLow = By.xpath("//div[text()='Price -- High to Low']");

    public static final By ProductResults = By.xpath("//div[@class='_4rR01T']");

    public static final By ProductPrices = By.xpath("//div[@class='_30jeq3 _1_WHN1']");

    public By Categories(String category){
        return By.xpath("//span[text()='CATEGORIES']/following::a[@title='"+category+"']");
    }

    public By BrandFilter(String filter){
        return By.xpath("//input[@type='checkbox']/following::div[text()='"+filter+"']");
    }

}
