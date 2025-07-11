package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;
import utils.DriverFactory;

import java.util.Properties;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private WebDriver driver;
    private Properties prop;

    @Before
    public void setup() {
        logger.info("Setting up test environment for thread: {}", Thread.currentThread().getId());
        prop = ConfigReader.initProperties();
        driver = DriverFactory.initDriver();
        driver.get(prop.getProperty("baseUrl"));
        logger.info("Test environment setup completed for thread: {}", Thread.currentThread().getId());
    }

    @After
    public void teardown() {
        logger.info("Tearing down test environment for thread: {}", Thread.currentThread().getId());
        DriverFactory.quitDriver();
        logger.info("Test environment teardown completed for thread: {}", Thread.currentThread().getId());
    }
}
