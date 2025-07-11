@smoke
Feature: OrangeHRM Logout

  @logout
  Scenario: Logout after successful login
    Given I enter username "Admin"
    And I enter password "admin123"
    When I click the login button
    Then I should be logged in successfully
    When I click the user dropdown
    And I click the logout button
    Then I should be logged out successfully 