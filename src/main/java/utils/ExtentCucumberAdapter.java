package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ExtentCucumberAdapter implements ConcurrentEventListener {
    private static final Logger logger = LoggerFactory.getLogger(ExtentCucumberAdapter.class);
    private static final Map<String, ExtentTest> testMap = new HashMap<>();
    private static final ExtentReports extent = ExtentReportManager.getInstance();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, this::handleTestRunStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::handleTestRunFinished);
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
    }

    private void handleTestRunStarted(TestRunStarted event) {
        logger.info("Test run started");
    }

    private void handleTestRunFinished(TestRunFinished event) {
        logger.info("Test run finished");
        extent.flush();
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        String scenarioName = event.getTestCase().getName();
        String featureName = event.getTestCase().getUri().getPath();
        if (featureName.contains("/")) {
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
        }

        String key = event.getTestCase().getId().toString();
        if (!testMap.containsKey(key)) {
            ExtentTest test = extent.createTest(scenarioName)
                    .assignCategory(featureName)
                    .assignAuthor("Automation Team");
            testMap.put(key, test);
        }

        logger.info("Test case started: {}", scenarioName);
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        String scenarioName = event.getTestCase().getName();
        String featureName = event.getTestCase().getUri().getPath();
        if (featureName.contains("/")) {
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
        }
        String key = featureName + ":" + scenarioName;
        ExtentTest test = testMap.get(key);

        if (test != null) {
            if (event.getResult().getStatus() == io.cucumber.plugin.event.Status.PASSED) {
                test.log(Status.PASS, "Test passed");
            } else if (event.getResult().getStatus() == io.cucumber.plugin.event.Status.FAILED) {
                test.log(Status.FAIL, "Test failed: " + event.getResult().getError().getMessage());
            } else if (event.getResult().getStatus() == io.cucumber.plugin.event.Status.SKIPPED) {
                test.log(Status.SKIP, "Test skipped");
            }
        }

        logger.info("Test case finished: {} with status: {}", scenarioName, event.getResult().getStatus());
    }

    private void handleTestStepStarted(TestStepStarted event) {
        String scenarioName = event.getTestCase().getName();
        String featureName = event.getTestCase().getUri().getPath();
        if (featureName.contains("/")) {
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
        }
        String key = featureName + ":" + scenarioName;
        ExtentTest test = testMap.get(key);

        if (test != null) {
            TestStep testStep = event.getTestStep();
            String stepName = "";
            if (testStep instanceof PickleStepTestStep) {
                stepName = ((PickleStepTestStep) testStep).getStep().getText();
            } else {
                stepName = testStep.getCodeLocation(); // fallback for hooks/backgrounds
            }
            test.log(Status.INFO, "Executing step: " + stepName);
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        String scenarioName = event.getTestCase().getName();
        String featureName = event.getTestCase().getUri().getPath();
        if (featureName.contains("/")) {
            featureName = featureName.substring(featureName.lastIndexOf('/') + 1);
        }
        String key = featureName + ":" + scenarioName;
        ExtentTest test = testMap.get(key);

        if (test != null) {
            TestStep testStep = event.getTestStep();
            String stepName = "";
            if (testStep instanceof PickleStepTestStep) {
                stepName = ((PickleStepTestStep) testStep).getStep().getText();
            } else {
                stepName = testStep.getCodeLocation();
            }
            if (event.getResult().getStatus() == io.cucumber.plugin.event.Status.PASSED) {
                test.log(Status.PASS, "Step passed: " + stepName);
            } else if (event.getResult().getStatus() == io.cucumber.plugin.event.Status.FAILED) {
                test.log(Status.FAIL, "Step failed: " + stepName + " - " + event.getResult().getError().getMessage());
            } else if (event.getResult().getStatus() == io.cucumber.plugin.event.Status.SKIPPED) {
                test.log(Status.SKIP, "Step skipped: " + stepName);
            }
        }
    }
}