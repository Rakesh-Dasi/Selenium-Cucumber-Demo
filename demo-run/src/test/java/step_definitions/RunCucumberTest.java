package step_definitions;

import cucumber.api.CucumberOptions;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(features = "classpath:features",plugin = {
        "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html", "pretty", "json:target/cucumber-reports/Cucumber.json"}, monochrome = true, tags = {
        "@Search"})
public class RunCucumberTest extends AbstractTestNGCucumberTests{

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}