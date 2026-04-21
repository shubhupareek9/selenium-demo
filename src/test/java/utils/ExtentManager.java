package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static synchronized ExtentReports getExtent() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter("target/extent-report.html");

            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Selenium Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Framework", "JUnit5 + Selenium");
        }

        return extent;
    }
}
