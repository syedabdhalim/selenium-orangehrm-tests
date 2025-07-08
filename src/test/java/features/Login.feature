@smoke
Feature: OrangeHRM Login

  @validLogin
  Scenario: Login with valid credentials
    Given I enter username "Admin"
    And I enter password "admin123"
    When I click the login button
    Then I should be logged in successfully
