package stepdefs;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.DashboardPage;
import utils.DriverFactory;
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
        loginPage.enterUsername(username);
    }

    @And("I enter password {string}")
    public void i_enter_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_login_button() {
        loginPage.clickLogin();
    }

    @Then("I should be logged in successfully")
    public void i_should_be_logged_in_successfully() {
        String url = driver.getCurrentUrl();
        assert url.contains("/dashboard") || url.contains("index");
    }

    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        String error = loginPage.getErrorMessage();
        assert error != null && !error.isEmpty();
    }
}
