package managers;

import enums.DriverType;
import enums.EnvironmentType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

    private WebDriver driver;

    private static DriverType driverType;
    private static EnvironmentType environmentType;
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";
    private static final String IE_DRIVER_PROPERTY = "webdriver.ie.driver";
    private static final String USERNAME = FileReaderManager.getInstance().getConfigReader().getUsername();
    private static final String ACCESS_KEY = FileReaderManager.getInstance().getConfigReader().getAccessKey();
    private static final String BROWSERSTACK_URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public WebDriverFactory() {
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
    }

    public WebDriver getDriver() throws MalformedURLException {
        if (driver == null)
            driver = createDriver();
        return driver;
    }

    private WebDriver createDriver() throws MalformedURLException {
        switch (environmentType) {
            case LOCAL:
                driver = createLocalDriver();
                break;
            case REMOTE:
                driver = createRemoteDriver();
                break;
        }
        return driver;
    }

    private WebDriver createRemoteDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("os", "Windows");
        capabilities.setCapability("os_version", "10");
        capabilities.setCapability("browser", "Chrome");
        capabilities.setCapability("browser_version", "80");
        capabilities.setCapability("name", "Rakesh Dasi's First Test");
        driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), capabilities);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        return driver;
    }

    private WebDriver createLocalDriver() throws MalformedURLException {
        switch (driverType) {
            case FIREFOX:
                System.setProperty(FIREFOX_DRIVER_PROPERTY, "Drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.manage().deleteAllCookies();
                break;

            case REMOTEDESKTOP:
                System.setProperty(CHROME_DRIVER_PROPERTY,
                        FileReaderManager.getInstance().getConfigReader().getDriverPath());
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();

                capabilities.setPlatform(Platform.WINDOWS);
                URL url = new URL("http://localhost:4444/wd/hub");
                driver = new RemoteWebDriver(url, capabilities);
                break;
            case INTERNETEXPLORER:
                System.setProperty(IE_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getIEDriverPath());
                DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
                cap.setCapability("nativeEvents", false);
                cap.setCapability("unexpectedAlertBehaviour", "accept");
                cap.setCapability("ignoreProtectedModeSettings", true);
                cap.setCapability("disable-popup-blocking", true);
                cap.setCapability("enablePersistentHover", true);
                cap.setCapability("ignoreZoomSetting", true);
                cap.setCapability("javascriptEnabled", true);
                cap.setJavascriptEnabled(true);
                cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                InternetExplorerOptions optionsIE = new InternetExplorerOptions();
                optionsIE.merge(cap);
                driver = new InternetExplorerDriver(optionsIE);
                break;
        }

        if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize())
            driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(),
                TimeUnit.SECONDS);
        return driver;
    }

    public void closeDriver() {
        driver.close();
        driver.quit();
    }

    public void analyzeLog() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        for (LogEntry entry : logEntries) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
    }

    public void quiteDriver() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
