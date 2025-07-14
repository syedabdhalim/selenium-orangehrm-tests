package hooks;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ExtentReportManager;

import java.util.Properties;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private WebDriver driver;
    private Properties prop;

    @Before
    public void setup(Scenario scenario) {
        logger.info("Setting up test environment for thread: {}", Thread.currentThread().getId());
        prop = ConfigReader.initProperties();
        driver = DriverFactory.initDriver();
        driver.get(prop.getProperty("baseUrl"));
        
        // Start ExtentReports test
        ExtentTest test = ExtentReportManager.getInstance().createTest(scenario.getName());
        test.assignCategory(scenario.getSourceTagNames().toArray(new String[0]));
        test.assignAuthor("Automation Team");
        
        logger.info("Test environment setup completed for thread: {}", Thread.currentThread().getId());
    }

    @After
    public void teardown(Scenario scenario) {
        logger.info("Tearing down test environment for thread: {}", Thread.currentThread().getId());
        
        // Add screenshot to ExtentReports if test fails
        if (scenario.isFailed()) {
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot");
                
                // Log failure in ExtentReports
                ExtentTest test = ExtentReportManager.getTest();
                if (test != null) {
                    test.log(Status.FAIL, "Test failed: " + scenario.getName());
                    test.addScreenCaptureFromBase64String(
                        java.util.Base64.getEncoder().encodeToString(screenshot),
                        "Failure Screenshot"
                    );
                }
            } catch (Exception e) {
                logger.error("Failed to capture screenshot", e);
            }
        } else {
            // Log success in ExtentReports
            ExtentTest test = ExtentReportManager.getTest();
            if (test != null) {
                test.log(Status.PASS, "Test passed: " + scenario.getName());
            }
        }
        
        DriverFactory.quitDriver();
        logger.info("Test environment teardown completed for thread: {}", Thread.currentThread().getId());
    }
}
