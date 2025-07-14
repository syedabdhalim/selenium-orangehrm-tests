package stepdefs;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.DashboardPage;
import utils.DriverFactory;
import utils.ExtentReportManager;
import utils.ScreenshotUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);
    private WebDriver driver = DriverFactory.getDriver();
    private LoginPage loginPage = new LoginPage(driver);
    private DashboardPage dashboardPage = new DashboardPage(driver);

    @Given("I enter username {string}")
    public void i_enter_username(String username) {
        logger.info("Starting login process with username: {}", username);
        
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.INFO, "Entering username: " + username);
        }
        
        loginPage.enterUsername(username);
    }

    @And("I enter password {string}")
    public void i_enter_password(String password) {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.INFO, "Entering password");
        }
        
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_login_button() {
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.INFO, "Clicking login button");
        }
        
        loginPage.clickLogin();
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        String url = driver.getCurrentUrl();
        boolean isLoggedIn = url.contains("/dashboard") || url.contains("index");
        
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            if (isLoggedIn) {
                test.log(Status.PASS, "Login successful - Current URL: " + url);
                ScreenshotUtil.logPassWithScreenshot(driver, "Login_Success", 
                    "Login successful - Dashboard page loaded");
            } else {
                test.log(Status.FAIL, "Login failed - Current URL: " + url);
                ScreenshotUtil.logFailWithScreenshot(driver, "Login_Failed", 
                    "Login failed - Not redirected to dashboard");
            }
        }
        
        assert isLoggedIn : "Login failed - Expected dashboard URL but got: " + url;
    }

    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        String error = loginPage.getErrorMessage();
        boolean hasError = error != null && !error.isEmpty();
        
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            if (hasError) {
                test.log(Status.PASS, "Error message displayed: " + error);
                ScreenshotUtil.logPassWithScreenshot(driver, "Login_Error_Displayed", 
                    "Error message displayed correctly");
            } else {
                test.log(Status.FAIL, "No error message displayed");
                ScreenshotUtil.logFailWithScreenshot(driver, "Login_Error_Missing", 
                    "Expected error message but none displayed");
            }
        }
        
        assert hasError : "Expected error message but none displayed";
    }
}
