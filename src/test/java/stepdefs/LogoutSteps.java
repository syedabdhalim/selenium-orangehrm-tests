package stepdefs;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import utils.DriverFactory;

public class LogoutSteps {
    private WebDriver driver = DriverFactory.getDriver();
    private DashboardPage dashboardPage = new DashboardPage(driver);

    @When("I click the user dropdown")
    public void i_click_user_dropdown() {
        dashboardPage.clickUserDropdown();
    }

    @And("I click the logout button")
    public void i_click_logout_button() {
        dashboardPage.clickLogout();
    }

    @Then("I should be logged out successfully")
    public void i_should_be_logged_out_successfully() {
        assert dashboardPage.isAtLoginPage();
    }
} 