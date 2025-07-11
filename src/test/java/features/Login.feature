@smoke
Feature: OrangeHRM Login

  @validLogin
  Scenario: Login with valid credentials
    Given I enter username "Admin"
    And I enter password "admin123"
    When I click the login button
    Then I should be logged in successfully

  @invalidLogin
  Scenario: Login with invalid credentials
    Given I enter username "InvalidUser"
    And I enter password "wrongpass"
    When I click the login button
    Then I should see an error message

