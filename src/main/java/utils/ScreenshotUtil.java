package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);

    public static String captureScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

            // Create screenshots directory
            String screenshotDir = "test-output/screenshots/";
            new File(screenshotDir).mkdirs();

            // Generate filename with timestamp
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String fileName = testName + "_" + now.format(formatter) + ".png";
            String filePath = screenshotDir + fileName;

            // Save screenshot as file
            java.nio.file.Files.write(java.nio.file.Paths.get(filePath), screenshot);

            // Convert to base64 for ExtentReports
            String base64Screenshot = java.util.Base64.getEncoder().encodeToString(screenshot);

            logger.info("Screenshot captured: {}", filePath);
            return base64Screenshot;

        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
            return null;
        }
    }

    public static void addScreenshotToReport(WebDriver driver, String testName, String message) {
        try {
            String base64Screenshot = captureScreenshot(driver, testName);
            if (base64Screenshot != null) {
                ExtentTest test = ExtentReportManager.getTest();
                if (test != null) {
                    test.addScreenCaptureFromBase64String(base64Screenshot, message);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to add screenshot to report", e);
        }
    }

    public static void logInfoWithScreenshot(WebDriver driver, String testName, String message) {
        try {
            String base64Screenshot = captureScreenshot(driver, testName);
            ExtentTest test = ExtentReportManager.getTest();
            if (test != null) {
                test.log(Status.INFO, message);
                if (base64Screenshot != null) {
                    test.addScreenCaptureFromBase64String(base64Screenshot, "Info Screenshot");
                }
            }
        } catch (Exception e) {
            logger.error("Failed to log info with screenshot", e);
        }
    }

    public static void logPassWithScreenshot(WebDriver driver, String testName, String message) {
        try {
            String base64Screenshot = captureScreenshot(driver, testName);
            ExtentTest test = ExtentReportManager.getTest();
            if (test != null) {
                test.log(Status.PASS, message);
                if (base64Screenshot != null) {
                    test.addScreenCaptureFromBase64String(base64Screenshot, "Pass Screenshot");
                }
            }
        } catch (Exception e) {
            logger.error("Failed to log pass with screenshot", e);
        }
    }

    public static void logFailWithScreenshot(WebDriver driver, String testName, String message) {
        try {
            String base64Screenshot = captureScreenshot(driver, testName);
            ExtentTest test = ExtentReportManager.getTest();
            if (test != null) {
                test.log(Status.FAIL, message);
                if (base64Screenshot != null) {
                    test.addScreenCaptureFromBase64String(base64Screenshot, "Failure Screenshot");
                }
            }
        } catch (Exception e) {
            logger.error("Failed to log failure with screenshot", e);
        }
    }
} 