package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/java/features",
    glue = {"stepdefs", "hooks"},
    tags = "@smoke",
    plugin = {
        "pretty",
        "json:target/cucumber.json",
        "html:target/cucumber-report.html",
        "utils.ExtentCucumberAdapter"
    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}