package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/features",
    glue = {"stepdefs", "hooks"},
    tags = "@smoke",
    plugin = {
        "pretty",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
        "json:target/cucumber.json",
        "html:target/cucumber-report.html"
    },
    monochrome = true
)

public class TestRunner extends AbstractTestNGCucumberTests {}
