package stepdefs;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import utils.DriverFactory;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutSteps {
    private static final Logger logger = LoggerFactory.getLogger(LogoutSteps.class);
    private WebDriver driver = DriverFactory.getDriver();
    private DashboardPage dashboardPage = new DashboardPage(driver);

    @When("I click the user dropdown")
    public void i_click_user_dropdown() {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.INFO, "Clicking user dropdown menu");
        }
        
        dashboardPage.clickUserDropdown();
    }

    @And("I click the logout button")
    public void i_click_logout_button() {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.INFO, "Clicking logout button");
        }
        
        dashboardPage.clickLogout();
    }

    @Then("I should be logged out successfully")
    public void i_should_be_logged_out_successfully() {
        boolean isLoggedOut = dashboardPage.isAtLoginPage();
        
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            if (isLoggedOut) {
                test.log(Status.PASS, "Logout successful - Redirected to login page");
                ScreenshotUtil.logPassWithScreenshot(driver, "Logout_Success", 
                    "Logout successful - Login page displayed");
            } else {
                test.log(Status.FAIL, "Logout failed - Not redirected to login page");
                ScreenshotUtil.logFailWithScreenshot(driver, "Logout_Failed", 
                    "Logout failed - Still on dashboard page");
            }
        }
        
        assert isLoggedOut : "Logout failed - Expected to be on login page";
    }
} 