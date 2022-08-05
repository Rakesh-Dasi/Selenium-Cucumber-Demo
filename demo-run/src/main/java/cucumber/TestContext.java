package cucumber;

import java.net.MalformedURLException;

import managers.PageObjectManager;
import managers.WebDriverMangerUtils;

public class TestContext {
	private final WebDriverMangerUtils webDriverManager;
	private final PageObjectManager pageObjectManager;
	public ScenarioContext scenarioContext;

	public TestContext() throws MalformedURLException {
		webDriverManager = new WebDriverMangerUtils();
		pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
		scenarioContext = new ScenarioContext();
	}

	public WebDriverMangerUtils getWebDriverManager() {
		return webDriverManager;
	}

	public PageObjectManager getPageObjectManger() {
		return pageObjectManager;
	}

	public ScenarioContext getScenarioContext() {
		return scenarioContext;
	}
}
