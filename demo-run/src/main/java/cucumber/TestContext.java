package cucumber;

import java.net.MalformedURLException;

import managers.PageObjectManager;
import managers.WebDriverFactory;

public class TestContext {
	private final WebDriverFactory webDriverManager;
	private final PageObjectManager pageObjectManager;
	public ScenarioContext scenarioContext;

	public TestContext() throws MalformedURLException {
		webDriverManager = new WebDriverFactory();
		pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
		scenarioContext = new ScenarioContext();
	}

	public WebDriverFactory getWebDriverManager() {
		return webDriverManager;
	}

	public PageObjectManager getPageObjectManger() {
		return pageObjectManager;
	}

	public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}
}
