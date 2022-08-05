package step_definitions;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.cucumber.listener.Reporter;
import com.google.common.io.Files;
import cucumber.TestContext;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {

    TestContext testContext;


    public Hooks(TestContext context) {
        testContext = context;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        DOMConfigurator.configure("log4j.xml");
        PropertyConfigurator.configure("configs//log4j.properties");
        disableWarning();
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            try {
                // This takes a screenshot from the driver at save it to the specified location
                File sourcePath = ((TakesScreenshot) testContext.getWebDriverManager().getDriver())
                        .getScreenshotAs(OutputType.FILE);

                // Building up the destination path for the screenshot to save
                // Also make sure to create a folder 'screenshots' with in the cucumber-report
                // folder
                File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/"
                        + screenshotName + ".png");

                // Copy taken screenshot from source location to destination location
                Files.copy(sourcePath, destinationPath);

                // This attach the specified screenshot to the test
                Reporter.addScreenCaptureFromPath(destinationPath.toString());
            } catch (IOException e) {
            }
        }
    }

    @After(order = 0)
    public void AfterSteps() throws InterruptedException {
        testContext.getWebDriverManager().quiteDriver();
    }

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }
}
