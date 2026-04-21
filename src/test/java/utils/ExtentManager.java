package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter("target/extent-report.html");

            spark.config().setReportName("Selenium Automation Report");
            spark.config().setDocumentTitle("Test Execution Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Project", "Selenium Extent Demo");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Framework", "JUnit 5 + Selenium");
        }

        return extent;
    }
}
