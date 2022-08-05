package page.locators;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {

	public static WebDriver driver;

	public BasePage(WebDriver driver) {
		BasePage.driver = driver;
	}
}
