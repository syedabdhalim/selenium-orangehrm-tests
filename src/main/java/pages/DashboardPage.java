package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.RetryUtil;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Typical selectors for OrangeHRM demo
    private By userDropdown = By.cssSelector(".oxd-userdropdown-name");
    private By logoutButton = By.xpath("//a[text()='Logout']");

    private static final Logger logger = LoggerFactory.getLogger(DashboardPage.class);

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickUserDropdown() {
        logger.info("Attempting to click user dropdown");
        RetryUtil.retryVoid(() -> {
            wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();
            logger.debug("User dropdown clicked successfully");
        }, 3, Duration.ofSeconds(2));
    }

    public void clickLogout() {
        logger.info("Attempting to click logout button");
        RetryUtil.retryVoid(() -> {
            wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
            logger.debug("Logout button clicked successfully");
        }, 3, Duration.ofSeconds(2));
    }

    public boolean isAtLoginPage() {
        // Check for login page element (e.g., username field)
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).isDisplayed();
    }
} 