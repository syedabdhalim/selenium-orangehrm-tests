# Selenium OrangeHRM Tests


![CI](https://github.com/syedabdhalim/selenium-orangehrm-tests/actions/workflows/ci.yml/badge.svg)
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
![Selenium](https://img.shields.io/badge/Selenium-Automation-green?logo=selenium)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-23d96c?logo=cucumber)

UI test automation project built with **Selenium**, **Cucumber**, and **Java** to validate core functionalities of [OrangeHRM Demo](https://opensource-demo.orangehrmlive.com/). It follows the **Page Object Model** (POM) design pattern, integrates with **TestNG** as the runner, and generates detailed visual reports using **ExtentReports**.

CI/CD is handled via **GitHub Actions**.

[![View ExtentReports](https://img.shields.io/badge/ExtentReports-View_Live_Report-blue)](https://syedabdhalim.github.io/selenium-orangehrm-tests/)

---

## Project Structure
```bash
selenium-orangehrm-tests/
├── src/
│   ├── main/java/
│   │   ├── pages/         # Page Object Model classes
│   │   └── utils/         # Utility classes
│   └── test/java/
│       ├── features/      # Gherkin .feature files for test scenarios
│       ├── stepdefs/      # Step Definitions binding Gherkin steps to code
│       ├── hooks/         # Cucumber Hooks
│       └── runners/       # Test runners
├── test-output/extent-reports/ # Generated ExtentReports HTML files
├── .github/workflows/ci.yml # GitHub Actions CI workflow
```

## How to Run Locally

### 1. Install dependencies
- Java 17+
- Maven
- Chrome
- ChromeDriver

### 2. Run tests
```bash
mvn clean test
```
### 3. View ExtentReports
- Open the latest HTML file in `test-output/extent-reports/` with your browser.
