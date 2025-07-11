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
        // Basic stability options
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--window-size=1920,1080");

        // Parallel execution fixes
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-plugins");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");

        // Disable DevTools to prevent crash
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-renderer-backgrounding");

        // Memory optimization
        options.addArguments("--memory-pressure-off");
        options.addArguments("--max_old_space_size=4096");

        WebDriver driver = new ChromeDriver(options);
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
