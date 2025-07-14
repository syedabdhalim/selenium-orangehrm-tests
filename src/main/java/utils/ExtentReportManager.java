package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    public static ExtentReports createInstance() {
        String fileName = getReportName();
        String directory = "test-output/extent-reports/";
        new File(directory).mkdirs();
        String path = directory + fileName;

        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(path);
        
        // Load configuration from XML file
        try {
            htmlReporter.loadXMLConfig("src/test/resources/extent-config.xml");
        } catch (Exception e) {
            // Fallback to default configuration
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setDocumentTitle("OrangeHRM Test Report");
            htmlReporter.config().setEncoding("utf-8");
            htmlReporter.config().setReportName("OrangeHRM Automation Test Results");
        }

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Organization", "OrangeHRM");
        extent.setSystemInfo("Application", "OrangeHRM Demo");
        extent.setSystemInfo("Environment", "Demo");
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extent;
    }

    private static String getReportName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return "OrangeHRM_Test_Report_" + now.format(formatter) + ".html";
    }

    public static void startTest(String testName) {
        ExtentTest extentTest = getInstance().createTest(testName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void endTest() {
        if (test.get() != null) {
            getInstance().flush();
        }
    }

    public static void addSystemInfo(String key, String value) {
        getInstance().setSystemInfo(key, value);
    }
} 