package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver initDriver() {
        logger.info("Initializing driver for thread: {}", Thread.currentThread().getId());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driverThread.set(driver);
        return driver;
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThread.get();
        if (driver == null) {
            logger.warn("Driver not initialized for thread: {}", Thread.currentThread().getId());
            return initDriver();
        }
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            logger.info("Quitting driver for thread: {}", Thread.currentThread().getId());
            driver.quit();
            driverThread.remove();
        }
    }
}
