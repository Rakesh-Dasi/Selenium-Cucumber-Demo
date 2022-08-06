package runners;

import java.io.File;

import org.junit.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import managers.FileReaderManager;

@CucumberOptions(features = "classpath:features", glue = {"step_definitions"}, plugin = {
        "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html", "pretty", "json:target/cucumber-reports/Cucumber.json"}, monochrome = true, tags = {
        "@Search"})
@Test
public class TestNgTestRunner extends AbstractTestNGCucumberTests {

    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File(FileReaderManager.getInstance().getConfigReader().getReportConfigPath()));
        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
    }
}
