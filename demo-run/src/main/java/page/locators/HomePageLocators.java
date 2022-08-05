package page.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePageLocators extends BasePage {

    public HomePageLocators(WebDriver driver) {
        super(driver);
    }

    public static final By CloseButton = By.xpath("(//button)[2]");

    public static final By SearchBar = By.xpath("//input[@name = 'q']");

    public static final By SearchIcon = By.xpath("//button[@type='submit']");

    public By Categories(String category){
        return By.xpath("//span[text()='CATEGORIES']/following::a[@title='"+category+"']");
    }

    public By BrandFilter(String filter){
        return By.xpath("//input[@type='checkbox']/following::div[text()='"+filter+"']");
    }

}
