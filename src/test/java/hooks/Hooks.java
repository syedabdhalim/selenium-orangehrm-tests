package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.DriverFactory;

import java.util.Properties;

public class Hooks {
    private WebDriver driver;
    private Properties prop;

    @Before
    public void setup() {
        prop = ConfigReader.initProperties();
        driver = DriverFactory.initDriver();
        driver.get(prop.getProperty("baseUrl"));
    }

    @After
    public void teardown() {
        DriverFactory.quitDriver();
    }
}
